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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.u8t.urlbooster.app.Console;
import com.u8t.urlbooster.bean.Configuration;
import com.u8t.urlbooster.bean.URLBoosterProxy;
import com.u8t.urlbooster.bean.URLStatistic;
import com.u8t.urlbooster.bean.Configuration.HitMode;
import com.u8t.urlbooster.gui.ExecutionBoard;
import com.u8t.urlbooster.urlfactory.AntivilleURLFactory;
import com.u8t.urlbooster.urlfactory.GenericURLFactory;
import com.u8t.urlbooster.urlfactory.MinivilleURLFactory;
import com.u8t.urlbooster.urlfactory.MyMinicityURLFactory;
import com.u8t.urlbooster.utils.StringTags;
import com.u8t.urlbooster.utils.StringUtils;

public class ExecutionUnit extends Thread {

    private Configuration config;
    
    // Indicateur de fin d'exécution
    private boolean terminate = false;
    
    // Pool d'éxécution des processus;
    private ThreadPoolExecutor executor;
    private boolean executorPaused = false;
    private boolean pauseRequired = false;
    private boolean resumeRequired = false;
    
    // Indicateur de dernière activité
    private long lastActivity = System.currentTimeMillis();
    
    // Objet parent
    private Object parent;
    
    // Liste des tâches en attente lors d'une pause
    List<Runnable> pausedTaskListFromFiller = new ArrayList<Runnable>();
    List<Runnable> pausedTaskListFromExecutor = new ArrayList<Runnable>();
    
    // Compteur d'itérations
    private int currentIteration = 0;
    
    public ExecutionUnit(Configuration config, Object parent) {
        this.config = (Configuration) config.clone();
        this.parent = parent;
    }
    
    public void run() {
        
        logOutputResult(StringUtils.getCenteredString("Execution unit started!", '*'));
        
        printConfiguration();
        
        logOutputResult("Loading URL factories...");
        getConfig().resetUrlFactories();
        StringTokenizer st;
        switch(getConfig().getConfigType()) {
        case Miniville:
            st = new StringTokenizer(getConfig().getMinivilleConfiguration().getMinivilleName(), ";");
            while (st.hasMoreTokens()) {
                String miniville = st.nextToken();
                logOutputResult("Create Miniville URL factory for " + miniville + "...");
                MinivilleURLFactory muf = new MinivilleURLFactory(this, miniville);
                getConfig().addUrlFactory(muf);
                muf.initStatisticsGrabber();
            }
            break;
        case Antiville:
            st = new StringTokenizer(getConfig().getMinivilleConfiguration().getMinivilleName(), ";");
            while (st.hasMoreTokens()) {
                String miniville = st.nextToken();
                logOutputResult("Create Antiville URL factory for " + miniville + "...");
                getConfig().addUrlFactory(new AntivilleURLFactory(miniville, getConfig().getMinivilleConfiguration().isUseMinivilleValidator()));
            }
            break;
        case MyMinicity:
            st = new StringTokenizer(getConfig().getMinivilleConfiguration().getMinivilleName(), ";");
            while (st.hasMoreTokens()) {
                String miniville = st.nextToken();
                logOutputResult("Create MyMinicity URL factory for " + miniville + "...");
                MyMinicityURLFactory mmuf = new MyMinicityURLFactory(this, miniville);
                getConfig().addUrlFactory(mmuf);
                mmuf.initStatisticsGrabber();
            }
            break;
        case Generic:
            st = new StringTokenizer(getConfig().getGenericURL(), ";");
            while (st.hasMoreTokens()) {
                String url = st.nextToken();
                logOutputResult("Create Generic URL factory for " + url + "...");
                getConfig().addUrlFactory(new GenericURLFactory(url, getConfig().getGenericURLValidator(), getConfig().isGenericURLValidatorRegex()));
            }
            break;
        }
        
        // Setup des paramètres globaux
        System.getProperties().put("sun.net.client.defaultConnectTimeout", "" + getConfig().getHttpConnectionTimeout());
        
        getConfig().resetGeneralStatistics();
        
        while ((getConfig().getIterations() > 0 || getConfig().getIterations() == -1) && !terminate) {
        
            if (getConfig().getIterations() > 0)
                getConfig().setIterations(getConfig().getIterations() - 1);
            
            iterate();
        }

        terminate = true;
        
        printStatistics();
        logOutputResult(StringUtils.getCenteredString("Execution unit stopped!", '*'));
        
        if (parent instanceof ExecutionBoard) {
            ((ExecutionBoard) parent).finalizeCloseButton();
        }
    }

    private void printConfiguration() {
        
        logOutputResult(StringUtils.getCenteredString("Config Type: " + getConfig().getConfigType(), '-'));
        
        switch(getConfig().getConfigType()) {
        case Miniville:
        case MyMinicity:
            logOutputResult("City: " + getConfig().getMinivilleConfiguration().getMinivilleName());
            logOutputResult("Mode: " + getConfig().getMinivilleConfiguration().getHitMode());
            logOutputResult("Objectives: Pop " + getConfig().getMinivilleConfiguration().getObjectiveCitizen()
                                     + " Ind " + getConfig().getMinivilleConfiguration().getObjectiveIndustry()
                                     + " Tra " + getConfig().getMinivilleConfiguration().getObjectiveTransport()
                                     + " Sec " + getConfig().getMinivilleConfiguration().getObjectiveSecurity()
                                     + " Env " + getConfig().getMinivilleConfiguration().getObjectiveEnvironment()
                                     + " Com " + getConfig().getMinivilleConfiguration().getObjectiveBusiness());
            logOutputResult("Weights: Pop " + getConfig().getMinivilleConfiguration().getWeightCitizen()
                                  + " Ind " + getConfig().getMinivilleConfiguration().getWeightIndustry()
                                  + " Tra " + getConfig().getMinivilleConfiguration().getWeightTransport()
                                  + " Sec " + getConfig().getMinivilleConfiguration().getWeightSecurity()
                                  + " Env " + getConfig().getMinivilleConfiguration().getWeightEnvironment()
                                  + " Com " + getConfig().getMinivilleConfiguration().getWeightBusiness());
            if (getConfig().getMinivilleConfiguration().getHitMode() == HitMode.Intelligent)
                logOutputResult("Refresh stats every " + getConfig().getMinivilleConfiguration().getRefreshStatisticsInterval() + " seconds");
            logOutputResult("Use validator: " + getConfig().getMinivilleConfiguration().isUseMinivilleValidator());
            break;
        case Antiville:
            logOutputResult("City: " + getConfig().getMinivilleConfiguration().getMinivilleName());
            break;
        case Generic:
            logOutputResult("URL: " + getConfig().getGenericURL());
            if (!getConfig().getGenericURLValidator().equals("")) {
                logOutputResult("Validator: " + getConfig().getGenericURLValidator());
                logOutputResult("Validator is regex: " + getConfig().isGenericURLValidatorRegex());
            }
            break;
        }
        
        logOutputResult("Save good proxies: " + getConfig().isSaveGoodProxies());
        logOutputResult("Save bad proxies: " + getConfig().isSaveBadProxies());
        logOutputResult("Exclude good proxies: " + getConfig().isExludeGoodProxies());
        logOutputResult("Emit beep: " + getConfig().isEmitBeep());
        logOutputResult("Clean proxies file: " + getConfig().isCleanProxiesFile());
        logOutputResult("Proxies types: " + getConfig().getProxyType());
        
        logOutputResult("Debug Level: " + getConfig().getDebugLevel());
        logOutputResult("HTTP Timeout: " + getConfig().getHttpConnectionTimeout() / 1000 + " seconds");
        logOutputResult("Max Threads: " + getConfig().getMaxThreadCount() + " / Max inactivity: " + getConfig().getMaxThreadsInactivity() / 1000 + " seconds");
        logOutputResult("Resolve name servers: " + getConfig().isResolveNameServers());
        
        logOutputResult(StringUtils.getCenteredString("", '-'));
    }
    
    private void printStatistics() {

        int totalHits = 0;
        int totalGoodHits = 0;

        logOutputResult(StringUtils.getCenteredString("Proxies responses statistics", '='));

        for (Map.Entry<String, URLStatistic> entry : getConfig().getGeneralStatistics().entrySet()) {
            logOutputResult(entry.getKey() + ": " + entry.getValue().getGoodHits() + "/" + entry.getValue().getTotalHits());
            totalGoodHits += entry.getValue().getGoodHits();
            totalHits += entry.getValue().getTotalHits();
        }

        logOutputResult("Total: " + totalGoodHits + "/" + totalHits);

        logOutputResult(StringUtils.getCenteredString("", '='));
    }
    
    private void iterate() {
        
        if (getConfig().getIterations() == -1)
            logOutputResult(StringUtils.getCenteredString("Running iteration " + ++currentIteration + " (infinite mode)", '-'));
        else
            logOutputResult(StringUtils.getCenteredString("Running iteration " + ++currentIteration + " (" + getConfig().getIterations() + " to go)", '-'));
        
        // Lecture du fichier de proxies
        readProxiesFile();
        
        if (getConfig().getProxiesList() != null && getConfig().getProxiesList().size() > 0) {
            
            if (parent instanceof ExecutionBoard) {
                ((ExecutionBoard) parent).setTotalProxies(getConfig().getProxiesList().size());
            }

            // Queue des processus en attente d'exécution
            BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(getConfig().getProxiesList().size());
    
            // Executeur des processus
            executor = new ThreadPoolExecutor(getConfig().getMaxThreadCount(),
                                              getConfig().getMaxThreadCount(),
                                              getConfig().getHttpConnectionTimeout(),
                                              TimeUnit.MILLISECONDS,
                                              blockingQueue);
            // Liste de travail des tâches
            List<Runnable> taskList = new ArrayList<Runnable>();
    
            // Identifiant des tâches
            int threadId = 1;
    
            // Construction de la liste des tâches
            for (URLBoosterProxy proxy: getConfig().getProxiesList().values()) {
                // Construction d'un nouveau processus
                SiteHitMaker shm = new SiteHitMaker(proxy, threadId, this);
                shm.setPriority(Thread.MIN_PRIORITY);
                taskList.add(shm);
                threadId++;
            }
    
            fillExecutor(executor, taskList);
            
            // boucle d'attente de terminaison des processus
            while ((executor.getActiveCount() > 0 && !terminate && taskList.size() > 0) || executorPaused) {

                // On attend une seconde
                try {
                    Thread.sleep(1000);
                    if (parent instanceof ExecutionBoard) {
                        ((ExecutionBoard) parent).updateTime();
                    }
                    
                    if (!(executorPaused || pauseRequired)) {
                    
                        logOutputResult(".");
        
                        if (System.currentTimeMillis() - lastActivity > getConfig().getMaxThreadsInactivity()) {
        
                            // L'executor semble bloqué -> réinitialisation des process
                            logOutputResult("Executor seems to be stucked -> shutdown/restart!");
                            taskList = executor.shutdownNow();
        
                            if (taskList != null && taskList.size() > 0) {
                                // Construction d'une nouvelle queue et d'un nouvel executor tout frais
                                blockingQueue = new ArrayBlockingQueue<Runnable>(taskList.size());
                                executor = new ThreadPoolExecutor(getConfig().getMaxThreadCount(),
                                                                  getConfig().getMaxThreadCount(),
                                                                  getConfig().HttpConnectionTimeout,
                                                                  TimeUnit.MILLISECONDS,
                                                                  blockingQueue);
            
                                logOutputResult("Reloading tasks...");
                                fillExecutor(executor, taskList);
            
                                lastActivity = System.currentTimeMillis();
                            }
                        }
                    }
                        
                    if (pauseRequired) {
                        pauseExecutor();
                        pauseRequired = false;
                        if (!(parent == null)) {
                            if (parent instanceof ExecutionBoard) {
                                ((ExecutionBoard) parent).enablePauseButton();
                            }
                        }
                    }
                    
                    if (resumeRequired) {
                        resumeExecutor();
                        resumeRequired = false;
                        if (!(parent == null)) {
                            if (parent instanceof ExecutionBoard) {
                                ((ExecutionBoard) parent).enablePauseButton();
                            }
                        }
                    }
                    
                    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    setTerminate(true);
                    executor.shutdownNow();
                    break;
                }
            }
            executor.shutdownNow();
            
        } else {
            logOutputResult("Empty proxy file or proxy file not found!");
        }
        
        logOutputResult(StringUtils.getCenteredString("End iteration " + currentIteration, '-'));
    }

    private void readProxiesFile() {

        LinkedHashMap<String, URLBoosterProxy> loadedProxiesMap = new LinkedHashMap<String, URLBoosterProxy>();
        LinkedHashMap<String, URLBoosterProxy> goodProxyMap = new LinkedHashMap<String, URLBoosterProxy>();
        String proxiesFile = getConfig().getProxiesFile();
        String goodProxiesFile = getConfig().getGoodProxiesFile();
        
        // substitution des tags spéciaux
        proxiesFile = StringTags.replaceDateTimeTags(proxiesFile);
        goodProxiesFile = StringTags.replaceDateTimeTags(goodProxiesFile);
        
        if (proxiesFile.contains("http://")) {
            URLGrabber ug = new URLGrabber(proxiesFile, this);
            File httpProxiesFile = new File("httpproxies.txt");
            ug.saveURLToFile(httpProxiesFile);
            proxiesFile = "httpproxies.txt";
        } else if (getConfig().isCleanProxiesFile()) {
            cleanProxiesFile(proxiesFile);
        }
        
        try {
            // Si l'on doit exclure les bons proxies, on en récupère la liste
            if (getConfig().isExludeGoodProxies() && new File(goodProxiesFile).exists()) {
                
                logOutputResult("exclude good proxies from: " + goodProxiesFile);
                BufferedReader goodProxiesReader = new BufferedReader(new FileReader(goodProxiesFile));

                // Lecture du fichier des proxies et décomposition des lignes
                String proxyLine;
                while ((proxyLine = goodProxiesReader.readLine()) != null) {
                    if (proxyLine.indexOf(getConfig().getProxiesSeparator()) > 1) {
                        URLBoosterProxy proxy = new URLBoosterProxy();
                        proxy.setProxyName(proxyLine.substring(0, proxyLine.indexOf(getConfig().getProxiesSeparator())));
                        proxy.setProxyPort(proxyLine.substring(proxyLine.indexOf(getConfig().getProxiesSeparator()) + 1));

                        // Ajout du proxy courant à la liste s'il ne s'y trouve pas déjà
                        if (goodProxyMap.get(proxyLine) == null)
                            goodProxyMap.put(proxyLine, proxy);
                    }
                }
                
                logOutputResult("Good proxies file read: " + goodProxyMap.size() + " unique good proxies to exclude");
            }
            
            // On lit ensuite les proxies à tester
            logOutputResult("reading proxies from: " + proxiesFile);
            BufferedReader proxiesReader = new BufferedReader(new FileReader(proxiesFile));

            // Lecture du fichier des proxies et décomposition des lignes
            String proxyLine;
            while ((proxyLine = proxiesReader.readLine()) != null) {
                if (proxyLine.indexOf(getConfig().getProxiesSeparator()) > 1) {
                    URLBoosterProxy proxy = new URLBoosterProxy();
                    proxy.setProxyName(proxyLine.substring(0, proxyLine.indexOf(getConfig().getProxiesSeparator())));
                    proxy.setProxyPort(proxyLine.substring(proxyLine.indexOf(getConfig().getProxiesSeparator()) + 1));

                    // Ajout du proxy courant à la liste s'il ne s'y trouve pas déjà
                    if (loadedProxiesMap.get(proxyLine) == null && goodProxyMap.get(proxyLine) == null)
                        loadedProxiesMap.put(proxyLine, proxy);
                }
            }
            
            logOutputResult("Proxies file read: " + loadedProxiesMap.size() + " unique proxies");

            // Tri des proxies
            Comparator<String> reverseComparator = Collections.reverseOrder();
            AbstractMap<String, URLBoosterProxy> proxiesMap = new LinkedHashMap<String, URLBoosterProxy>();
            
            switch (getConfig().getProxyHitOrder()) {
            case File:
                proxiesMap = loadedProxiesMap;
                break;
                
            case Alpha:
                proxiesMap = new TreeMap<String, URLBoosterProxy>(loadedProxiesMap);
                break;
                
            case FileReverse:
                Object[] loadedProxiesKeysArray = loadedProxiesMap.keySet().toArray();
                int length = loadedProxiesKeysArray == null ? 0 : loadedProxiesKeysArray .length;
                for(int i = length - 1; i >= 0; i--) {
                    proxiesMap.put((String) loadedProxiesKeysArray[i], loadedProxiesMap.get(loadedProxiesKeysArray[i])); 
                } 
                break;
                
            case AlphaReverse:
                proxiesMap = new TreeMap<String, URLBoosterProxy>(reverseComparator);
                proxiesMap.putAll(loadedProxiesMap);
                break;
                
            case Random:
                Random randomizer = new Random();
                while (loadedProxiesMap.size() > 0) {
                    int randomKeyIndex = randomizer.nextInt(loadedProxiesMap.size());
                    String proxyKey = (String) loadedProxiesMap.keySet().toArray()[randomKeyIndex];
                    proxiesMap.put(proxyKey,
                                   (URLBoosterProxy)loadedProxiesMap.values().toArray()[randomKeyIndex]);
                    loadedProxiesMap.remove(proxyKey);
                }
                break;
            }
            
            getConfig().setProxiesList(proxiesMap);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void cleanProxiesFile(String proxiesFile) {
        
        LinkedHashMap<String, URLBoosterProxy> proxyMap = new LinkedHashMap<String, URLBoosterProxy>();
        
        try {
            
            BufferedReader proxiesReader = new BufferedReader(new FileReader(proxiesFile));

            try {
                
                // Lecture du fichier des proxies et décomposition des lignes
                String proxyLine;
                while ((proxyLine = proxiesReader.readLine()) != null) {
                    if (proxyLine.indexOf(getConfig().getProxiesSeparator()) > 1) {
                        URLBoosterProxy proxy = new URLBoosterProxy();
                        proxy.setProxyName(proxyLine.substring(0, proxyLine.indexOf(getConfig().getProxiesSeparator())));
                        proxy.setProxyPort(proxyLine.substring(proxyLine.indexOf(getConfig().getProxiesSeparator()) + 1));
        
                        // nettoyage des proxies
                        proxy.setProxyName(proxy.getProxyName().trim());
                        proxy.setProxyPort(proxy.getProxyPort().trim());
                        if (proxy.getProxyPort().indexOf(" ") > 0)
                            proxy.setProxyPort(proxy.getProxyPort().substring(0, proxy.getProxyPort().indexOf(" ")));
                        
                        // Ajout du proxy courant à la liste s'il ne s'y trouve pas déjà
                        if (proxyMap.get(proxyLine) == null)
                            proxyMap.put(proxyLine, proxy);
                    }
                }
                
            } finally {
                proxiesReader.close();
            }
                
            File tempFile = new File(proxiesFile + ".tmp");
            if (tempFile.exists()) {
                tempFile.delete();
            }
            
            // Création d'un handle sur le fichier de sortie
            RandomAccessFile rf = new RandomAccessFile(tempFile, "rw");

            try {
                for (URLBoosterProxy proxy : proxyMap.values()) {
                    rf.writeBytes(proxy.getProxyName() + config.getProxiesSeparator() + proxy.getProxyPort() + "\r\n");
                }
            } finally {
                rf.close();
            }
            
            File newProxiesFile = new File(proxiesFile);
            newProxiesFile.delete();
            tempFile.renameTo(newProxiesFile);
        
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void fillExecutor(ThreadPoolExecutor executor, List<Runnable> tasks) {
        
        boolean addTasksToPausedTasks = false;
        int addedTasks = 0;
        pausedTaskListFromFiller = new ArrayList<Runnable>();
        
        for (Runnable task : tasks) {
            try {
                if (!addTasksToPausedTasks) {
                    executor.execute(task);
                } else {
                    pausedTaskListFromFiller.add(task);
                }
                
                addedTasks ++;
    
                // On pieute juste un peu entre chaque process, histoire de ne
                // pas surcharger la connection réseau. Ceci seuleument pour
                // les n premiers threads (n étant égal à deux fois le parallélisme maximal autorisé).
                try {
                    if (addedTasks < getConfig().getMaxThreadCount() * 2) {
                        Thread.sleep(100);
                    }
                    
                    if (parent instanceof ExecutionBoard) {
                        ((ExecutionBoard) parent).updateTime();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    terminate = true;
                    break;
                }
            } catch (RejectedExecutionException e) {
                // Cette exception nous indique que l'exécuteur a été arrêté.
                // Nous sauvons les tâches restantes pour une prochaine exécution.
                addTasksToPausedTasks = true;
                addedTasks = getConfig().getMaxThreadCount() * 2;
            }
        }
        LogManager.logFinest("Paused pausedTaskListFromFiller: " + pausedTaskListFromFiller.size());
    }
    
    public void stopExecutor() {
        executor.shutdownNow();
    }

    public synchronized void saveHitStatistic(String url) {
        // Alimentation des statistiques
        if (getConfig().getGeneralStatistics().get(url) == null)
            getConfig().getGeneralStatistics().put(url, new URLStatistic());

        getConfig().getGeneralStatistics().get(url).countTotalHits();
        
        if (parent instanceof ExecutionBoard) {
            ((ExecutionBoard) parent).countTotalHit();
        }
    }

    public synchronized void saveGoodStatistic(String url) {
        // Alimentation des statistiques
        if (getConfig().getGeneralStatistics().get(url) == null)
            getConfig().getGeneralStatistics().put(url, new URLStatistic());

        getConfig().getGeneralStatistics().get(url).countGoodHits();
        
        if (parent instanceof ExecutionBoard) {
            ((ExecutionBoard) parent).countGoodHit();
        }
    }
    
    public synchronized void saveBadStatistic(@SuppressWarnings("unused") String url) {
        if (parent instanceof ExecutionBoard) {
            ((ExecutionBoard) parent).countBadHit();
        }
    }
    
    public synchronized void recordHitTry() {
        if (parent instanceof ExecutionBoard) {
            ((ExecutionBoard) parent).uncountProxy();
        }
    }
    
    public void logOutputResult(String msg) {
        if (!(parent == null)) {
            if (parent instanceof Console) {
                ((Console) parent).logInfo(msg);
            } else if (parent instanceof ExecutionBoard) {
                ((ExecutionBoard) parent).logOutputResult(msg);
            }
        }
    }

    public synchronized void activityAlert() {
        lastActivity = System.currentTimeMillis();
    }

    public final Configuration getConfig() {
        return config;
    }

    public final boolean isTerminate() {
        return terminate;
    }

    public final void setTerminate(boolean terminate) {
        this.terminate = terminate;
    }

    public boolean isExecutorPaused() {
        return executorPaused;
    }
    
    private void pauseExecutor() {
        
        logOutputResult("Pause Executor...");
        
        // L'executor semble bloqué -> réinitialisation des process
        List <Runnable> taskList = executor.shutdownNow();
        pausedTaskListFromExecutor = new ArrayList<Runnable>();
        
        for(Runnable task : taskList)
            pausedTaskListFromExecutor.add(task);
        
        LogManager.logFinest("Paused pausedTaskListFromExecutor: " + pausedTaskListFromExecutor.size());
        lastActivity = System.currentTimeMillis();
        executorPaused = true;
    }
    
    private void resumeExecutor() {
        
        logOutputResult("Resume Executor...");
        
        if (pausedTaskListFromExecutor.size() > 0 || pausedTaskListFromFiller.size() > 0) {

            LogManager.logFinest("Resume pausedTaskListFromExecutor: " + pausedTaskListFromExecutor.size());
            LogManager.logFinest("Resume pausedTaskListFromFiller: " + pausedTaskListFromFiller.size());
            
            for (Runnable task : pausedTaskListFromFiller)
                pausedTaskListFromExecutor.add(task);
            
            // Construction d'une nouvelle queue et d'un nouvel executor tout frais
            BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(pausedTaskListFromExecutor.size());
            executor = new ThreadPoolExecutor(getConfig().getMaxThreadCount(),
                                              getConfig().getMaxThreadCount(),
                                              getConfig().HttpConnectionTimeout,
                                              TimeUnit.MILLISECONDS,
                                              blockingQueue);
    
            logOutputResult("Reloading tasks...");
            fillExecutor(executor, pausedTaskListFromExecutor);
    
            lastActivity = System.currentTimeMillis();
            executorPaused = false;
        }
    }
    
    public void requirePause() {
        pauseRequired = true;
    }
    
    public void requireResume() {
        resumeRequired = true;
    }

}
