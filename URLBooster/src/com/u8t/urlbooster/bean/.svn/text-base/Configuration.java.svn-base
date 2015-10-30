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
package com.u8t.urlbooster.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import com.u8t.urlbooster.Main;
import com.u8t.urlbooster.components.LogManager;
import com.u8t.urlbooster.urlfactory.IURLFactory;

public class Configuration implements Cloneable {

    public static final String version = "2.3.0";

    public enum ConfigType {
        Generic, Miniville, Antiville, MyMinicity;
    }
    
    public enum HitMode {
        Random, PseudoRandom, Intelligent;
    }
    
    public enum HitOrder {
        File, Alpha, FileReverse, AlphaReverse, Random;
    }
    
    public enum DebugLevel {
        Off, Severe, Warning, Info, Fine, Finer, Finest, All;
    }
    
    public enum ProxyType {
        Http, HttpThenSocks, SocksThenHttp, Socks;
    }
    
    // fichier de paramètres par défaut
    private String propertiesFile = "";

    private ConfigType configType = ConfigType.Generic;
    
    // timeout de connexion par défaut en millisecondes
    public int HttpConnectionTimeout = 30000;

    // non-activité maximale des processus en cours d'exécution en millisecondes
    private int maxThreadsInactivity = 15000;

    // chemin des fichiers de proxies
    private boolean saveGoodProxies = false;
    private String goodProxiesFile = "";
    private boolean saveBadProxies = false;
    private String badProxiesFile = "";
    private String proxiesFile = "";
    private String proxiesSeparator = ":";
    
    // indique si la liste de proxies doit être nettoyée
    private boolean cleanProxiesFile = true;
    
    // Log level
    private DebugLevel debugLevel = DebugLevel.Off;

    // liste interne des proxies
    private AbstractMap<String, URLBoosterProxy> proxiesList;

    // statistiques globales
    private LinkedHashMap<String, URLStatistic> generalStatistics = new LinkedHashMap<String, URLStatistic>();

    // parallélisme maximal autorisé
    private int maxThreadCount = 15;

    // Nombre d'itérations à effectuer
    private int iterations = 1;

    // Indique si un beep doit être émis en cas de bon proxy trouvé
    private boolean emitBeep = false;
    
    // Doit-on résoudre les noms de serveurs ?
    private boolean resolveNameServers = false;
    
    // Doit-on exclure les bons proxies de la liste à tester ?
    private boolean exludeGoodProxies = false;

    // Provider des URLs à visiter
    private List<IURLFactory> urlFactories = new ArrayList<IURLFactory>();
    
    // URL générique à visiter
    private String genericURL = "http://";
    
    // Valideur de l'URL. Doit contenir une chaine de caractère spécifique
    // attendue dans le retour HTTP.
    private String genericURLValidator = "";
    
    // Indique si le validator est une expression régulière.
    private boolean genericURLValidatorRegex = false;
    
    // Type de proxy présent dans le fichier des proxies à tester
    private ProxyType proxyType = ProxyType.Http;
    
    // Configuration spécifique à Miniville
    MinivilleConfiguration minivilleConfiguration = new MinivilleConfiguration();
    
    public class MinivilleConfiguration {
        
        /*
         * Mode de tirage des URLs à visiter.
         * HitMode.RANDOM : tirage complètement au hasard
         * HitMode.PSEUDORANDOM : tirage en fonction d'un poids paramétré
         * HitMode.INTELLIGENT : dans ce mode, les informations de la miniville sont
         * récupérées, et les URLs sont visitées en fonction des ses besoins. Lorsque
         * la ville n'a aucun besoin particulier, une bascule vers le mode PSEUDORANDOM
         * est effectuée.
         */
        private HitMode hitMode = HitMode.Intelligent;
        
        private String minivilleName = "";
        
        // Poids des URLS pour le mode PSEUDORANDOM
        private int weightCitizen = 50;
        private int weightIndustry = 17;
        private int weightTransport = 10;
        private int weightSecurity = 12;
        private int weightEnvironment = 11;
        private int weightBusiness = 0;
        
        // Objectifs optimaux par défaut
        private int objectiveCitizen = 10000000; // 10000000 d'habitants, on a le temps de voir venir...
        private int objectiveIndustry = 0; // 0% chômage
        private int objectiveTransport = 100; // 100% transport
        private int objectiveSecurity = 0; // 0% délinquance
        private int objectiveEnvironment = 0; // 0% pollution
        private int objectiveBusiness = 0; // pas de statistique sur cet objectif
        
        // intervalle de refresh des statistiques
        private int refreshStatisticsInterval = 300;
        
        // indique si l'on doit utiliser un validator
        private boolean useMinivilleValidator = true;
        
        public void loadConfigWithParamaters(Properties properties) {
            
            if (properties.getProperty("Miniville") != null && !properties.getProperty("Miniville").equals("")) {
                minivilleName = properties.getProperty("Miniville");
            } else {
                Main.getApp().exitProgram("Miniville properties not found!");
            }
            
            if (properties.getProperty("HitMode") != null && !properties.getProperty("HitMode").equals("")) {
                if (properties.getProperty("HitMode").toLowerCase().equals("random"))
                    hitMode = HitMode.Random;
                else if (properties.getProperty("HitMode").toLowerCase().equals("pseudorandom"))
                    hitMode = HitMode.PseudoRandom;
                else if (properties.getProperty("HitMode").toLowerCase().equals("intelligent"))
                    hitMode = HitMode.Intelligent;
            } else {
                Main.getApp().exitProgram("HitMode properties not found!");
            }
            
            refreshStatisticsInterval = new Integer(properties.getProperty("StatisticsRefreshInterval", Integer.toString(refreshStatisticsInterval))).intValue();
            
            weightCitizen = new Integer(properties.getProperty("WeightCitizen", Integer.toString(weightCitizen))).intValue();
            weightIndustry = new Integer(properties.getProperty("WeightIndustry", Integer.toString(weightIndustry))).intValue();
            weightTransport = new Integer(properties.getProperty("WeightTransport", Integer.toString(weightTransport))).intValue();
            weightSecurity = new Integer(properties.getProperty("WeightSecurity", Integer.toString(weightSecurity))).intValue();
            weightEnvironment = new Integer(properties.getProperty("WeightEnvironment", Integer.toString(weightEnvironment))).intValue();
            weightBusiness = new Integer(properties.getProperty("WeightBusiness", Integer.toString(weightBusiness))).intValue();

            objectiveCitizen = new Integer(properties.getProperty("ObjectiveCitizen", Integer.toString(objectiveCitizen))).intValue();
            objectiveIndustry = new Integer(properties.getProperty("ObjectiveIndustry", Integer.toString(objectiveIndustry))).intValue();
            objectiveTransport = new Integer(properties.getProperty("ObjectiveTransport", Integer.toString(objectiveTransport))).intValue();
            objectiveSecurity = new Integer(properties.getProperty("ObjectiveSecurity", Integer.toString(objectiveTransport))).intValue();
            objectiveEnvironment = new Integer(properties.getProperty("ObjectiveEnvironment", Integer.toString(objectiveEnvironment))).intValue();
            objectiveBusiness = new Integer(properties.getProperty("ObjectiveBusiness", Integer.toString(objectiveBusiness))).intValue();
            
            if (properties.getProperty("UseMinivilleValidator") != null && properties.getProperty("UseMinivilleValidator").toLowerCase().equals("true"))
                setUseMinivilleValidator(true);
            else if (properties.getProperty("UseMinivilleValidator") != null && properties.getProperty("UseMinivilleValidator").toLowerCase().equals("false"))
                setUseMinivilleValidator(false);
            else if (properties.getProperty("UseMinivilleValidator") != null)
                Main.getApp().exitProgram("UseMinivilleValidator property incorrect!");
        }
        
        public final int getWeightCitizen() {
            return weightCitizen;
        }
        public final void setWeightCitizen(int weightCitizen) {
            this.weightCitizen = weightCitizen;
        }
        public final int getWeightIndustry() {
            return weightIndustry;
        }
        public final void setWeightIndustry(int weightIndustry) {
            this.weightIndustry = weightIndustry;
        }
        public final int getWeightTransport() {
            return weightTransport;
        }
        public final void setWeightTransport(int weightTransport) {
            this.weightTransport = weightTransport;
        }
        public final int getWeightSecurity() {
            return weightSecurity;
        }
        public final void setWeightSecurity(int weightSecurity) {
            this.weightSecurity = weightSecurity;
        }
        public final int getWeightEnvironment() {
            return weightEnvironment;
        }
        public final void setWeightEnvironment(int weightEnvironment) {
            this.weightEnvironment = weightEnvironment;
        }
        public final int getWeightBusiness() {
            return weightBusiness;
        }
        public final void setWeightBusiness(int weightBusiness) {
            this.weightBusiness = weightBusiness;
        }
        public final int getObjectiveCitizen() {
            return objectiveCitizen;
        }
        public final void setObjectiveCitizen(int objectiveCitizen) {
            this.objectiveCitizen = objectiveCitizen;
        }
        public final int getObjectiveIndustry() {
            return objectiveIndustry;
        }
        public final void setObjectiveIndustry(int objectiveIndustry) {
            this.objectiveIndustry = objectiveIndustry;
        }
        public final int getObjectiveTransport() {
            return objectiveTransport;
        }
        public final void setObjectiveTransport(int objectiveTransport) {
            this.objectiveTransport = objectiveTransport;
        }
        public final int getObjectiveSecurity() {
            return objectiveSecurity;
        }
        public final void setObjectiveSecurity(int objectiveSecurity) {
            this.objectiveSecurity = objectiveSecurity;
        }
        public final int getObjectiveEnvironment() {
            return objectiveEnvironment;
        }
        public final void setObjectiveEnvironment(int objectiveEnvironment) {
            this.objectiveEnvironment = objectiveEnvironment;
        }
        
        public final int getObjectiveBusiness() {
            return objectiveBusiness;
        }
        
        public final void setObjectiveBusiness(int objectiveBusiness) {
            this.objectiveBusiness = objectiveBusiness;
        }
        
        public final HitMode getHitMode() {
            return hitMode;
        }
        
        public final void setHitMode(HitMode hitMode) {
            this.hitMode = hitMode;
        }
        
        public final int getRefreshStatisticsInterval() {
            return refreshStatisticsInterval;
        }
        
        public final void setRefreshStatisticsInterval(int refreshStatisticsInterval) {
            this.refreshStatisticsInterval = refreshStatisticsInterval;
        }

        public final String getMinivilleName() {
            return minivilleName;
        }

        public final void setMinivilleName(String minivilleName) {
            this.minivilleName = minivilleName;
        }

        public boolean isUseMinivilleValidator() {
            return useMinivilleValidator;
        }

        public void setUseMinivilleValidator(boolean useMinivilleValidator) {
            this.useMinivilleValidator = useMinivilleValidator;
        }
    }
    
    private HitOrder proxyHitOrder = HitOrder.File;
    
    public final void loadConfigFromFile(String file) {
        
        setPropertiesFile(file);
        
        try {
            Properties properties = new Properties();
            
            if (file.indexOf(".xml") == (file.length() - 4)) 
                properties.loadFromXML(new FileInputStream(file));
            else
                properties.load(new FileInputStream(file));
        
            if (properties.getProperty("ConfigType") != null && !properties.getProperty("ConfigType").equals("")) {
                if(properties.getProperty("ConfigType").toLowerCase().equals("miniville")) {
                    setConfigType(ConfigType.Miniville);
                    getMinivilleConfiguration().loadConfigWithParamaters(properties);
                } else if(properties.getProperty("ConfigType").toLowerCase().equals("antiville")) {
                    setConfigType(ConfigType.Antiville);
                    if (properties.getProperty("Miniville") != null && !properties.getProperty("Miniville").equals("")) {
                        getMinivilleConfiguration().setMinivilleName(properties.getProperty("Miniville"));
                    } else {
                        Main.getApp().exitProgram("Miniville not found!");
                    }
                } else if(properties.getProperty("ConfigType").toLowerCase().equals("myminicity")) {
                    setConfigType(ConfigType.MyMinicity);
                    getMinivilleConfiguration().loadConfigWithParamaters(properties);
                } else if(properties.getProperty("ConfigType").toLowerCase().equals("generic")) {
                    setConfigType(ConfigType.Generic);
                    if (properties.getProperty("GenericURL") != null && !properties.getProperty("GenericURL").equals("")) {
                        setGenericURL(properties.getProperty("GenericURL"));
                    } else {
                        Main.getApp().exitProgram("GenericURL not found!");
                    }
                    
                    if (properties.getProperty("GenericValidator") != null && !properties.getProperty("GenericValidator").equals("")) {
                        setGenericURLValidator(properties.getProperty("GenericValidator"));
                    }
                    
                    if (properties.getProperty("GenericValidatorIsRegex") != null && properties.getProperty("GenericValidatorIsRegex").toLowerCase().equals("true"))
                        setGenericURLValidatorRegex(true);
                    else if (properties.getProperty("GenericValidatorIsRegex") != null && properties.getProperty("GenericValidatorIsRegex").toLowerCase().equals("false"))
                        setGenericURLValidatorRegex(false);
                } else {
                    Main.getApp().exitProgram("ConfigType not supported!");
                }
            } else {
                Main.getApp().exitProgram("ConfigType property not found!");
            }
    
            if (properties.getProperty("ProxiesPath") != null && !properties.getProperty("ProxiesPath").equals("")) {
                setProxiesFile(properties.getProperty("ProxiesPath"));
    
                if (!getProxiesFile().contains("http://") && !new File(getProxiesFile()).exists()) {
                    Main.getApp().exitProgram("ProxiesPath file not found!");
                }
            } else {
                Main.getApp().exitProgram("ProxiesPath property not found!");
            }
    
            if (properties.getProperty("SaveGoodProxies") != null && properties.getProperty("SaveGoodProxies").toLowerCase().equals("true"))
                setSaveGoodProxies(true);
            else if (properties.getProperty("SaveGoodProxies") != null && properties.getProperty("SaveGoodProxies").toLowerCase().equals("false"))
                setSaveGoodProxies(false);
            
            if (properties.getProperty("GoodProxiesPath") != null && !properties.getProperty("GoodProxiesPath").equals("")) {
                setGoodProxiesFile(properties.getProperty("GoodProxiesPath"));
            }
            
            if (properties.getProperty("SaveBadProxies") != null && properties.getProperty("SaveBadProxies").toLowerCase().equals("true"))
                setSaveBadProxies(true);
            else if (properties.getProperty("SaveBadProxies") != null && properties.getProperty("SaveBadProxies").toLowerCase().equals("false"))
                setSaveBadProxies(false);
            
            if (properties.getProperty("BadProxiesPath") != null && !properties.getProperty("BadProxiesPath").toLowerCase().equals("")) {
                setBadProxiesFile(properties.getProperty("BadProxiesPath"));
            }
    
            if (properties.getProperty("ProxiesOrder") != null && !properties.getProperty("ProxiesOrder").toLowerCase().equals("")) {
                if(properties.getProperty("ProxiesOrder").toLowerCase().equals("file"))
                    setProxyHitOrder(HitOrder.File);
                else if(properties.getProperty("ProxiesOrder").toLowerCase().equals("alpha"))
                    setProxyHitOrder(HitOrder.Alpha);
                else if(properties.getProperty("ProxiesOrder").toLowerCase().equals("filereverse"))
                    setProxyHitOrder(HitOrder.FileReverse);
                else if(properties.getProperty("ProxiesOrder").toLowerCase().equals("alphareverse"))
                        setProxyHitOrder(HitOrder.AlphaReverse);
                else if(properties.getProperty("ProxiesOrder").toLowerCase().equals("random"))
                    setProxyHitOrder(HitOrder.Random);
                else {
                    Main.getApp().exitProgram("ProxiesOrder not supported!");
                }
            }
            
            if (properties.getProperty("cleanProxiesFile") != null && properties.getProperty("cleanProxiesFile").toLowerCase().equals("true"))
                setCleanProxiesFile(true);
            else if (properties.getProperty("cleanProxiesFile") != null && properties.getProperty("cleanProxiesFile").toLowerCase().equals("false"))
                setCleanProxiesFile(false); 
            
            setIterations(new Integer(properties.getProperty("Iterations", Integer.toString(getIterations()))).intValue());
    
            if (properties.getProperty("EmitBeep") != null && properties.getProperty("EmitBeep").toLowerCase().equals("true"))
                setEmitBeep(true);
            else if (properties.getProperty("EmitBeep") != null && properties.getProperty("EmitBeep").toLowerCase().equals("false"))
                setEmitBeep(false);
            else if (properties.getProperty("EmitBeep") != null)
                Main.getApp().exitProgram("EmitBeep property incorrect!");
            
            if (properties.getProperty("ResolveNameServers") != null && properties.getProperty("ResolveNameServers").toLowerCase().equals("true"))
                setResolveNameServers(true);
            else if (properties.getProperty("ResolveNameServers") != null && properties.getProperty("ResolveNameServers").toLowerCase().equals("false"))
                setResolveNameServers(false);
            else if (properties.getProperty("ResolveNameServers") != null)
                Main.getApp().exitProgram("ResolveNameServers property incorrect!");
            
            if (properties.getProperty("ExcludeGoodProxies") != null && properties.getProperty("ExcludeGoodProxies").toLowerCase().equals("true"))
                setExludeGoodProxies(true);
            else if (properties.getProperty("ExcludeGoodProxies") != null && properties.getProperty("ExcludeGoodProxies").toLowerCase().equals("false"))
                setExludeGoodProxies(false);
            else if (properties.getProperty("ExcludeGoodProxies") != null)
                Main.getApp().exitProgram("ExcludeGoodProxies property incorrect!");
    
            if (properties.getProperty("DebugLevel") != null && !properties.getProperty("DebugLevel").equals("")) {
                if(properties.getProperty("DebugLevel").toLowerCase().equals("off"))
                    setDebugLevel(DebugLevel.Off);
                else if(properties.getProperty("DebugLevel").toLowerCase().equals("severe"))
                    setDebugLevel(DebugLevel.Severe);
                else if(properties.getProperty("DebugLevel").toLowerCase().equals("warning"))
                    setDebugLevel(DebugLevel.Warning);
                else if(properties.getProperty("DebugLevel").toLowerCase().equals("info"))
                    setDebugLevel(DebugLevel.Info);
                else if(properties.getProperty("DebugLevel").toLowerCase().equals("fine"))
                    setDebugLevel(DebugLevel.Fine);
                else if(properties.getProperty("DebugLevel").toLowerCase().equals("finer"))
                    setDebugLevel(DebugLevel.Finer);
                else if(properties.getProperty("DebugLevel").toLowerCase().equals("finest"))
                    setDebugLevel(DebugLevel.Finest);
                else if(properties.getProperty("DebugLevel").toLowerCase().equals("all"))
                    setDebugLevel(DebugLevel.All);
                else {
                    Main.getApp().exitProgram("DebugLevel not supported!");
                }
                LogManager.setLoggerLevel(getDebugLevel());
            }
            
            if (properties.getProperty("ProxyType") != null && !properties.getProperty("ProxyType").equals("")) {
                if(properties.getProperty("ProxyType").toLowerCase().equals("http"))
                    setProxyType(ProxyType.Http);
                else if(properties.getProperty("ProxyType").toLowerCase().equals("httpthensocks"))
                    setProxyType(ProxyType.HttpThenSocks);
                else if(properties.getProperty("ProxyType").toLowerCase().equals("socksthenhttp"))
                    setProxyType(ProxyType.SocksThenHttp);
                else if(properties.getProperty("ProxyType").toLowerCase().equals("socks"))
                    setProxyType(ProxyType.Socks);
                else {
                    Main.getApp().exitProgram("ProxyType not supported!");
                }
            }

            setProxiesSeparator(properties.getProperty("ProxiesSeparator", getProxiesSeparator()));
            setMaxThreadCount(new Integer(properties.getProperty("MaxThreadCount", Integer.toString(getMaxThreadCount()))).intValue());
            setHttpConnectionTimeout(new Integer(properties.getProperty("HTTPConnectionTimeout", Integer.toString(getHttpConnectionTimeout() / 1000))).intValue() * 1000);
            setMaxThreadsInactivity(new Integer(properties.getProperty("MaxThreadsInactivity", Integer.toString(getMaxThreadsInactivity() / 1000))).intValue() * 1000);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public final void saveConfigToFile(String file) {
        
        setPropertiesFile(file);
        
        try {
            Properties properties = new Properties();
        
            properties.setProperty("ConfigType", "" + getConfigType());
            
            switch (getConfigType()) {
            case Generic:
                properties.setProperty("GenericURL", getGenericURL());
                properties.setProperty("GenericValidator", getGenericURLValidator());
                properties.setProperty("GenericValidatorIsRegex", "" + isGenericURLValidatorRegex());
                break;
            case Miniville:
            case Antiville:
            case MyMinicity:
                properties.setProperty("Miniville", getMinivilleConfiguration().getMinivilleName());
                switch (getConfigType()) {
                case Miniville:
                case MyMinicity:
                    
                    properties.setProperty("HitMode", "" + getMinivilleConfiguration().getHitMode());
                    properties.setProperty("StatisticsRefreshInterval", "" + getMinivilleConfiguration().getRefreshStatisticsInterval());
                    
                    properties.setProperty("WeightCitizen", "" + getMinivilleConfiguration().getWeightCitizen());
                    properties.setProperty("WeightIndustry", "" + getMinivilleConfiguration().getWeightIndustry());
                    properties.setProperty("WeightTransport", "" + getMinivilleConfiguration().getWeightTransport());
                    properties.setProperty("WeightSecurity", "" + getMinivilleConfiguration().getWeightSecurity());
                    properties.setProperty("WeightEnvironment", "" + getMinivilleConfiguration().getWeightEnvironment());
                    properties.setProperty("WeightBusiness", "" + getMinivilleConfiguration().getWeightBusiness());
                    
                    properties.setProperty("ObjectiveCitizen", "" + getMinivilleConfiguration().getObjectiveCitizen());
                    properties.setProperty("ObjectiveIndustry", "" + getMinivilleConfiguration().getObjectiveIndustry());
                    properties.setProperty("ObjectiveTransport", "" + getMinivilleConfiguration().getObjectiveTransport());
                    properties.setProperty("ObjectiveSecurity", "" + getMinivilleConfiguration().getObjectiveSecurity());
                    properties.setProperty("ObjectiveEnvironment", "" + getMinivilleConfiguration().getObjectiveEnvironment());
                    properties.setProperty("ObjectiveBusiness", "" + getMinivilleConfiguration().getObjectiveBusiness());
                   
                    break;
                default:
                    break;
                }
                
                break;
            }
            
            properties.setProperty("ProxiesPath", getProxiesFile());
            properties.setProperty("GoodProxiesPath", getGoodProxiesFile());
            properties.setProperty("SaveGoodProxies", "" + isSaveGoodProxies());
            properties.setProperty("BadProxiesPath", getBadProxiesFile());
            properties.setProperty("SaveBadProxies", "" + isSaveBadProxies());
            properties.setProperty("ProxiesOrder", "" + getProxyHitOrder());
            properties.setProperty("Iterations", "" + getIterations());
            properties.setProperty("EmitBeep", "" + isEmitBeep());
            properties.setProperty("ResolveNameServers", "" + isResolveNameServers());
            properties.setProperty("ExcludeGoodProxies", "" + isExludeGoodProxies());
            properties.setProperty("DebugLevel", "" + getDebugLevel());
            properties.setProperty("ProxyType", "" + getProxyType());
            properties.setProperty("ProxiesSeparator", getProxiesSeparator());
            properties.setProperty("MaxThreadCount", "" + getMaxThreadCount());
            properties.setProperty("HTTPConnectionTimeout", "" + getHttpConnectionTimeout() / 1000);
            properties.setProperty("MaxThreadsInactivity", "" + getMaxThreadsInactivity() / 1000);

            FileOutputStream fos = new FileOutputStream(file);
            properties.storeToXML(fos, "");
            fos.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError(e.toString());
        }
    }
    
    public final String getPropertiesFile() {
        return propertiesFile;
    }

    public final void setPropertiesFile(String propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    public final int getHttpConnectionTimeout() {
        return HttpConnectionTimeout;
    }

    public final void setHttpConnectionTimeout(int httpConnectionTimeout) {
        HttpConnectionTimeout = httpConnectionTimeout;
    }

    public final int getMaxThreadsInactivity() {
        return maxThreadsInactivity;
    }

    public final void setMaxThreadsInactivity(int maxThreadsInactivity) {
        this.maxThreadsInactivity = maxThreadsInactivity;
    }

    public final boolean isSaveGoodProxies() {
        return saveGoodProxies;
    }

    public final void setSaveGoodProxies(boolean saveGoodProxies) {
        this.saveGoodProxies = saveGoodProxies;
    }

    public final String getGoodProxiesFile() {
        return goodProxiesFile;
    }

    public final void setGoodProxiesFile(String goodProxiesFile) {
        this.goodProxiesFile = goodProxiesFile;
    }

    public final boolean isSaveBadProxies() {
        return saveBadProxies;
    }

    public final void setSaveBadProxies(boolean saveBadProxies) {
        this.saveBadProxies = saveBadProxies;
    }

    public final String getBadProxiesFile() {
        return badProxiesFile;
    }

    public final void setBadProxiesFile(String badProxiesFile) {
        this.badProxiesFile = badProxiesFile;
    }

    public final String getProxiesFile() {
        return proxiesFile;
    }

    public final void setProxiesFile(String proxiesFile) {
        this.proxiesFile = proxiesFile;
    }

    public final String getProxiesSeparator() {
        return proxiesSeparator;
    }

    public final void setProxiesSeparator(String proxiesSeparator) {
        this.proxiesSeparator = proxiesSeparator;
    }

    public final AbstractMap<String, URLBoosterProxy> getProxiesList() {
        return proxiesList;
    }

    public final void setProxiesList(AbstractMap<String, URLBoosterProxy> proxiesList) {
        this.proxiesList = proxiesList;
    }

    public final LinkedHashMap<String, URLStatistic> getGeneralStatistics() {
        return generalStatistics;
    }

    public final void resetGeneralStatistics() {
        generalStatistics = new LinkedHashMap<String, URLStatistic>();
    }

    public final int getMaxThreadCount() {
        return maxThreadCount;
    }

    public final void setMaxThreadCount(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
    }

    public final int getIterations() {
        return iterations;
    }

    public final void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public final boolean isEmitBeep() {
        return emitBeep;
    }

    public final void setEmitBeep(boolean emitBeep) {
        this.emitBeep = emitBeep;
    }

    public final List<IURLFactory> getUrlFactories() {
        return urlFactories;
    }

    public final void setUrlFactories(List<IURLFactory> urlFactory) {
        this.urlFactories = urlFactory;
    }
    
    public final void addUrlFactory(IURLFactory urlFactory) {
        this.urlFactories.add(urlFactory);
    }

    public final void resetUrlFactories() {
        this.urlFactories.clear();
    }
    
    public final HitOrder getProxyHitOrder() {
        return proxyHitOrder;
    }

    public final void setProxyHitOrder(HitOrder proxyHitOrder) {
        this.proxyHitOrder = proxyHitOrder;
    }

    public final ConfigType getConfigType() {
        return configType;
    }

    public final void setConfigType(ConfigType configType) {
        this.configType = configType;
    }

    public final MinivilleConfiguration getMinivilleConfiguration() {
        return minivilleConfiguration;
    }

    public final void setMinivilleConfiguration(
            MinivilleConfiguration minivilleConfiguration) {
        this.minivilleConfiguration = minivilleConfiguration;
    }

    public final DebugLevel getDebugLevel() {
        return debugLevel;
    }

    public final void setDebugLevel(DebugLevel debugLevel) {
        LogManager.setLoggerLevel(debugLevel);
        this.debugLevel = debugLevel;
    }

    public String getGenericURL() {
        return genericURL;
    }

    public void setGenericURL(String genericURL) {
        this.genericURL = genericURL;
    }

    public boolean isCleanProxiesFile() {
        return cleanProxiesFile;
    }

    public void setCleanProxiesFile(boolean cleanProxiesFile) {
        this.cleanProxiesFile = cleanProxiesFile;
    }

    public String getGenericURLValidator() {
        return genericURLValidator;
    }

    public void setGenericURLValidator(String genericURLValidator) {
        this.genericURLValidator = genericURLValidator;
    }

    public boolean isGenericURLValidatorRegex() {
        return genericURLValidatorRegex;
    }

    public void setGenericURLValidatorRegex(boolean genericURLValidatorRegex) {
        this.genericURLValidatorRegex = genericURLValidatorRegex;
    }

    public boolean isResolveNameServers() {
        return resolveNameServers;
    }

    public void setResolveNameServers(boolean resolveNameServers) {
        this.resolveNameServers = resolveNameServers;
    }

    public boolean isExludeGoodProxies() {
        return exludeGoodProxies;
    }

    public void setExludeGoodProxies(boolean exludeGoodProxies) {
        this.exludeGoodProxies = exludeGoodProxies;
    }

    public final ProxyType getProxyType() {
        return proxyType;
    }
    
    public final String getProxyTypeLiteral(ProxyType proxyType) {
        switch(proxyType) {
        case Http:
            return "Http";
        case HttpThenSocks:
            return "Http then Socks";
        case SocksThenHttp:
            return "Socks then Http";
        case Socks:
            return "Socks";
        }
        return "Http";
    }

    public final void setProxyType(ProxyType proxyType) {
        this.proxyType = proxyType;
    }
    
}
