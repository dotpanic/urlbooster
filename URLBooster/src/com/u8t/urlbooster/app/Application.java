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
package com.u8t.urlbooster.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

import com.u8t.urlbooster.bean.Configuration;
import com.u8t.urlbooster.bean.URLBoosterProxy;
import com.u8t.urlbooster.utils.StringTags;
import com.u8t.urlbooster.utils.StringUtils;

abstract public class Application extends Thread {
    
    private static Configuration config = new Configuration();
    
    // Paramètres pour le locking du fichier des bons proxies
    private static final int LOCK_TRIES = 10;
    private static final int DELAY_BETWEEN_LOCK_TRY = 100;
    
    // Indicateur de fin d'exécution
    private boolean terminate = false;

    public void init() {
        logInfo(StringUtils.getCenteredString("URLBooster by dotpanic", '-'));
        logInfo(StringUtils.getCenteredString("version " + Configuration.version));
        logInfo(StringUtils.getCenteredString("Copywrong ultim8team 2007-2009"));
        logInfo(StringUtils.getCenteredString("http://dotpanic.ultim8team.com/urlbooster/"));
        logInfo(StringUtils.getCenteredString("", '-'));
        
        if (this instanceof Console)
            loadConfig();
    }
    
    // méthode à implémenter dans les classes terminales
    abstract public void exitProgram(String reason);
    
    public void logInfo(String msg) {
        if (msg.equals("."))
            System.out.print(msg);
        else
            System.out.println(msg);
    }

    private void loadConfig() {
        // Si le fichier de paramètres n'existe pas, on sort du programme
        if (!new File(config.getPropertiesFile()).exists()) {
            exitProgram("Properties file " + config.getPropertiesFile() + " not found!");
        }

        config.loadConfigFromFile(config.getPropertiesFile());
    }

    /*
     * Cette méthode va nous servir à enregistrer les bons proxies dans un
     * fichier indépendant.
     */
    public synchronized void saveProxy(URLBoosterProxy proxy, String proxyFile) {

        boolean proxyExists = false;
        
        // substitution des tags spéciaux
        proxyFile = StringTags.replaceDateTimeTags(proxyFile);
        
        try {

            if (new File(proxyFile).exists()) {
                // on regarde si notre proxy n'existe pas déjà
                BufferedReader proxiesReader = new BufferedReader(new FileReader(proxyFile));

                try {
                    String proxyLine;
                    // Lecture de toutes les lignes du fichier
                    while ((proxyLine = proxiesReader.readLine()) != null && !proxyExists) {
                        // Si le proxy existe, on l'indique
                        if (proxyLine.compareTo(proxy.getProxyName() + config.getProxiesSeparator() + proxy.getProxyPort()) == 0)
                            proxyExists = true;
                    }

                } finally {
                    proxiesReader.close();
                }
            }

            // Si le proxy n'existe pas, on le rajoute
            if (!proxyExists) {

                // Création d'un handle sur le fichier de sortie
                RandomAccessFile rf = new RandomAccessFile(proxyFile, "rw");

                try {

                    // On essaye de locker le ficher
                    FileLock fl = rf.getChannel().tryLock();
                    int lockCount = 0;

                    // Si le fichier n'a pas pu être locké, on va retenter plusieurs fois
                    while (fl == null && lockCount < LOCK_TRIES) {
                        try {
                            Thread.sleep(DELAY_BETWEEN_LOCK_TRY);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        fl = rf.getChannel().tryLock();
                        lockCount++;
                    }

                    try {
                        // Si le fichier est locké, on sauvegarde le proxy
                        if(fl != null) {
                            rf.seek(new File(proxyFile).length());
                            rf.writeBytes(proxy.getProxyName() + config.getProxiesSeparator() + proxy.getProxyPort() + "\r\n");
                        } else
                            logInfo("proxy " + proxy.getProxyName() + config.getProxiesSeparator() + proxy.getProxyPort() +
                                    " has not been saved because the " + proxyFile + " file could not be locked for exclusive access!");
                    } finally {
                        if(fl != null) {
                            fl.release();
                        }
                    }
                } finally {
                    rf.close();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final Configuration getConfig() {
        return config;
    }

    public boolean isTerminate() {
        return terminate;
    }

    public void setTerminate(boolean terminate) {
        this.terminate = terminate;
    }

}
