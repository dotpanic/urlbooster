/*
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.u8t.urlbooster.components;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.u8t.urlbooster.Main;
import com.u8t.urlbooster.bean.URLBoosterProxy;
import com.u8t.urlbooster.urlfactory.IURLFactory;

public class SiteHitMaker extends Thread {
    
    private URLBoosterProxy proxy;
    private int threadId;
    
    private ExecutionUnit executionUnit;
    
    private boolean goodProxy = true;
    
    public SiteHitMaker(URLBoosterProxy proxy, int threadId, ExecutionUnit executionUnit) {
        this.proxy = proxy;
        this.threadId = threadId;
        this.executionUnit = executionUnit;
    }
    
    public final void run() {
        try {
            
            executionUnit.recordHitTry();
            
            for (int i = 0; i < executionUnit.getConfig().getUrlFactories().size() && goodProxy; i++) {
                
                goodProxy = false;
                
                IURLFactory urlFactory = executionUnit.getConfig().getUrlFactories().get(i);
                
                executionUnit.logOutputResult("thread (" + threadId + ") with proxy "
                        + proxy.getProxyName() + ":" 
                        + proxy.getProxyPort() + " created (" + urlFactory.getURL() + ")");
    
                executionUnit.saveHitStatistic(urlFactory.getURL());
                
                // En fonction du type de proxy, on teste les genres de proxies
                // dans un ordre spécifique
                switch (executionUnit.getConfig().getProxyType()) {
                case Http:
                    hit(urlFactory, Proxy.Type.HTTP);
                    break;
                case HttpThenSocks:
                    if (!hit(urlFactory, Proxy.Type.HTTP))
                        hit(urlFactory, Proxy.Type.SOCKS);
                    break;
                case SocksThenHttp:
                    if (!hit(urlFactory, Proxy.Type.SOCKS))
                        hit(urlFactory, Proxy.Type.HTTP);
                    break;
                case Socks:
                    hit(urlFactory, Proxy.Type.HTTP);
                    break;
                }
                
                // Si le proxy est OK, on le sauvegarde
                if (goodProxy) {
                    executionUnit.saveGoodStatistic(urlFactory.getURL());
                    if (executionUnit.getConfig().isSaveGoodProxies())
                        Main.getApp().saveProxy(proxy, executionUnit.getConfig().getGoodProxiesFile());
                    
                    if (executionUnit.getConfig().isEmitBeep())
                        Toolkit.getDefaultToolkit().beep();
                } else {
                    executionUnit.saveBadStatistic(urlFactory.getURL());
                    if (executionUnit.getConfig().isSaveBadProxies())
                        Main.getApp().saveProxy(proxy, executionUnit.getConfig().getBadProxiesFile());
                }
            }
            
        } catch (Exception e) {
             e.printStackTrace();
        } finally {
            executionUnit.activityAlert();
        }
    }
    
    private boolean hit(IURLFactory urlFactory, Proxy.Type proxyType) {
        try {
            
            // Création d'une URL à visiter
            URL u = new URL(urlFactory.getURL());
            
            // Proxy setup
            InetSocketAddress addr = new InetSocketAddress(proxy.getProxyName(), Integer.parseInt(proxy.getProxyPort()));
            Proxy HTTPProxy = new Proxy(proxyType, addr);
            
            // Etablissement d'une connexion HTTP
            HttpURLConnection con = (HttpURLConnection) u.openConnection(HTTPProxy);
    
            // Alimentation des différents champs de la requête pour simuler une
            // authentique browser request
            for (Map.Entry<String, String> entry : urlFactory.getHeaders().entrySet())
                con.setRequestProperty(entry.getKey(), entry.getValue());
            
            LogManager.logFine("connection...");
            
            // Connexion !
            con.connect();
            
            LogManager.logFine("connection ok / proxy response: " + con.getResponseCode());
            
            /*
             * Si le code retour est 200 ou 302, alors le proxy fonctionne et
             * il faut le sauvegarder. On pourrait peut-être se limiter au
             * code retour 302, qui est celui normalement retourné par le site
             * lorsque le hit est accepté. A tester...
             */
            if (con.usingProxy() && (con.getResponseCode() == 200 || con.getResponseCode() == 302)) {
                
                // Lecture de la réponse HTTP
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                try {
                    String validator = urlFactory.getURLValidator();
                    boolean validatorRegex = urlFactory.isURLValidatorRegex();
                    boolean useValidator = urlFactory.useValidator();

                    if (useValidator && !validator.equals("")) {
                        String line;
                        while ((line = responseReader.readLine()) != null) {
                            if (validatorRegex) {
                                
                                Pattern pattern = Pattern.compile(validator);
                                Matcher matcher = pattern.matcher(line);

                                if (matcher.find()) {
                                    goodProxy = true;
                                    break;
                                }
                            } else if (line.indexOf(validator) > 0) {
                                goodProxy = true;
                                break;
                            }
                            
                        }                
                    } else
                        goodProxy = true;
                } finally {
                    responseReader.close();
                }
                
                if (goodProxy && executionUnit.getConfig().isResolveNameServers())
                    proxy.setProxyName(addr.getAddress().getHostAddress());

                if (goodProxy)
                    executionUnit.logOutputResult("URL " + urlFactory.getURL() + " hit by thread " + threadId + "!");
                else
                    executionUnit.logOutputResult("Thread " + threadId + " failed to hit " + urlFactory.getURL() + "!");
            } else {
                executionUnit.logOutputResult("Bad HTTP response on thread " + threadId + "! Response code: " + con.getResponseCode());
            }
            
            // Déconnexion
            con.disconnect();
        
        } catch (SocketTimeoutException e) {
            executionUnit.logOutputResult("Connect timeout on thread " + threadId + "!");
            LogManager.traceException(e);
        } catch (UnknownHostException e) {
            executionUnit.logOutputResult("Unknown host on thread " + threadId + "!");
            LogManager.traceException(e);
        } catch (NoRouteToHostException e) {
            executionUnit.logOutputResult("No route to host on thread " + threadId + "!");
            LogManager.traceException(e);
        } catch (ProtocolException e) {
            executionUnit.logOutputResult("Protocol exception on thread " + threadId + "!");
            LogManager.traceException(e);
        } catch (ConnectException e) {
            executionUnit.logOutputResult("Connect exception (" + e.getMessage() + ") on thread " + threadId + "!");
            LogManager.traceException(e);
        } catch (SocketException e) {
            executionUnit.logOutputResult("Socket exception (" + e.getMessage() + ") on thread " + threadId + "!");
            LogManager.traceException(e);
        } catch (IOException e) {
            LogManager.traceException(e);
        }
        
        return goodProxy;
    }

    public final URLBoosterProxy getProxy() {
        return proxy;
    }
}