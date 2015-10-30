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
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.NoRouteToHostException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

import com.u8t.urlbooster.Main;

public class URLGrabber extends Thread {
    
    private String urlToGet;
    private ExecutionUnit executionUnit;
    
    public URLGrabber(String urlToGet, ExecutionUnit executionUnit) {
        this.urlToGet = urlToGet;
        this.executionUnit = executionUnit;
    }
    
    public void saveURLToFile(File savedFile) {
        try {
            
            // Création d'une URL à visiter
            URL u = new URL(urlToGet);
            
            // Timeout setup
            System.getProperties().put("sun.net.client.defaultConnectTimeout", "" + Main.getApp().getConfig().getHttpConnectionTimeout());
            
            // Etablissement d'une connexion HTTP
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            
            // Connexion !
            con.connect();
            
            /*
             * Si le code retour est 200, on peut lire le contenu
             */
            executionUnit.logOutputResult("" + con.getResponseCode());
            if (con.getResponseCode() == 200) {
                executionUnit.logOutputResult("URL " + urlToGet + " read!");
                
                // Lecture de la réponse HTTP
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                
                RandomAccessFile rf = new RandomAccessFile(savedFile, "rw");

                try {
                    rf.seek(savedFile.length());
                
                    String line;
                    while ((line = responseReader.readLine()) != null) {
                        executionUnit.logOutputResult(line);
                        rf.writeBytes(line + "\r\n");
                    }                
                } finally {
                    rf.close();
                    responseReader.close();
                }
            }
            
            // Déconnexion
            con.disconnect();
        
        } catch (SocketTimeoutException e) {
            Main.logInfo("Connect timeout!");
            LogManager.traceException(e);
        } catch (UnknownHostException e) {
            Main.logInfo("Unknown host!");
            LogManager.traceException(e);
        } catch (NoRouteToHostException e) {
            Main.logInfo("No route to host!");
            LogManager.traceException(e);
        } catch (ConnectException e) {
            Main.logInfo("Connect exception!");
            LogManager.traceException(e);
        } catch (SocketException e) {
            Main.logInfo("Socket exception!");
            LogManager.traceException(e);
        } catch (IOException e) {
            LogManager.traceException(e);
        }
    }
}