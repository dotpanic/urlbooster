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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.NoRouteToHostException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

import com.u8t.urlbooster.bean.WeightedURL;
import com.u8t.urlbooster.urlfactory.IURLFactory;
import com.u8t.urlbooster.urlfactory.MinivilleLikeURLFactory;
import com.u8t.urlbooster.utils.HTTPHeaderUtils;

public class StatisticsGrabber extends Thread {
    
    // 1 minute entre chaque tentative de récupération de statistique
    private static int EXECUTION_FREQUENCY = 300;
    
    private ExecutionUnit executionUnit;
    
    private MinivilleLikeURLFactory minivilleFactory;
    
    public StatisticsGrabber(int executionFrequency, ExecutionUnit executionUnit, IURLFactory urlFactory) {
        super();
        
        this.executionUnit = executionUnit;
        
        EXECUTION_FREQUENCY = executionFrequency;
        LogManager.logFinest("Statistics process: constructor");

        minivilleFactory = (MinivilleLikeURLFactory) urlFactory;
        LogManager.logFinest("Statistics process: minivilleFactory for host " + minivilleFactory.getURL());
    }
    
    public final void run() {
        try {
            
            executionUnit.logOutputResult("Statistics process: started");
            
            int timeWaited = EXECUTION_FREQUENCY;
            
            while(executionUnit != null && !executionUnit.isTerminate()) {
                
                if (EXECUTION_FREQUENCY == timeWaited) {
                    getCityStatistics();
                    timeWaited = 0;
                }
                 
                sleep(1000);
                timeWaited++;
            }
            
            executionUnit.logOutputResult("Statistics process: stopped");
            
        } catch (InterruptedException e) {
             e.printStackTrace();
        }
    }
    
    private void getCityStatistics() {
        try {
            
            // Création d'une URL à visiter
            URL u = new URL(minivilleFactory.getMinivilleURLXML());
            LogManager.logFinest("Statistics process: statistics URL to visit: " + minivilleFactory.getMinivilleURLXML());
            
            // Etablissement d'une connexion HTTP
            HttpURLConnection con = (HttpURLConnection) u.openConnection();

            // Alimentation des différents champs de la requête
            con.setRequestProperty("Accept-Language", HTTPHeaderUtils.getRandomLanguage());
            con.setRequestProperty("Accept-Encoding", "gzip, deflate");
            con.setRequestProperty("User-Agent", HTTPHeaderUtils.getRandomUserAgent());
            con.setRequestProperty("Host", minivilleFactory.getMinivilleHost());
            
            LogManager.logFine("Statistics process: connection...");
            
            // Connexion !
            con.connect();
            
            LogManager.logFine("Statistics process: connection ok / proxy response: " + con.getResponseCode());
            
            // Si le code retour est 200, on lit la réponse
            if (con.getResponseCode() == 200) {
                
                /*
                 * Lecture et affichage de la réponse HTTP. Bon ici, deux solutions.
                 * Soit on part sur une solution à base de SAX ou DOM (Xerces rulez!), mais
                 * vu la complexité du fichier XML de miniville, c'est un peu comme sortir
                 * un mirage 2000 pour atomiser un serpent en plein désert (private joke),
                 * soit on utilise la bonne vieille méthode du parsing manuel, qui
                 * a fait ses preuves depuis la nuit des temps (1/1/70). 
                 */
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                while ((line = responseReader.readLine()) != null) {
                    for (WeightedURL weightedURL : minivilleFactory.getUrlList()) {
                        if (!weightedURL.getStatTag().equals("") && line.contains(weightedURL.getStatTag()))
                            fillStatistic(line, weightedURL);                            
                    }
                }
            }
            
            // Déconnexion
            con.disconnect();
        
        } catch (SocketTimeoutException e) {
            executionUnit.logOutputResult("Statistics process: connect timeout!");
            LogManager.traceException(e);
        } catch (UnknownHostException e) {
            executionUnit.logOutputResult("Statistics process: unknown host!");
            LogManager.traceException(e);
        } catch (NoRouteToHostException e) {
            executionUnit.logOutputResult("Statistics process: no route to host!");
            LogManager.traceException(e);
        } catch (ConnectException e) {
            executionUnit.logOutputResult("Statistics process: connect exception (" + e.getMessage() + ")!");
            LogManager.traceException(e);
        } catch (SocketException e) {
            executionUnit.logOutputResult("Statistics process: socket exception (" + e.getMessage() + ")!");
            LogManager.traceException(e);
        } catch (IOException e) {
            e.printStackTrace();
            LogManager.traceException(e);
        }
    }
    
    void fillStatistic(String line, WeightedURL weightedURL) {
        try {
            minivilleFactory.getUrlList().getURLById(weightedURL.getId()).setStatistic(
                    new Integer(line.substring(line.indexOf(weightedURL.getStatTag()) + weightedURL.getStatTag().length(),
                                               line.indexOf("<", line.indexOf(weightedURL.getStatTag()) + 1))).intValue());
        } catch (NumberFormatException nfe) {
            LogManager.traceException(nfe);
            minivilleFactory.getUrlList().getURLById(weightedURL.getId()).setStatistic(weightedURL.getObjective());
        }
    }
}