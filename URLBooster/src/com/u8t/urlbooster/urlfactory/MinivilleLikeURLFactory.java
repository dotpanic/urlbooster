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
package com.u8t.urlbooster.urlfactory;

import java.util.LinkedHashMap;

import com.u8t.urlbooster.bean.Configuration;
import com.u8t.urlbooster.bean.URLList;
import com.u8t.urlbooster.bean.WeightedURL;
import com.u8t.urlbooster.bean.Configuration.HitMode;
import com.u8t.urlbooster.components.ExecutionUnit;
import com.u8t.urlbooster.components.StatisticsGrabber;
import com.u8t.urlbooster.utils.HTTPHeaderUtils;

abstract public class MinivilleLikeURLFactory implements IURLFactory {
    
    // liste pondérée des URLs
    private URLList urlList = new URLList();
    
    // Valideur de l'URL. Doit contenir une chaine de caractère spécifique
    // attendue dans le retour HTTP.
    private static String URLValidator = "trax.motion-twin.com";
    
    // Récupérateur des statistiques de la miniville
    private StatisticsGrabber sg;
    
    // indique si l'on doit utiliser le validator
    private boolean useValidator = true;
    
    private ExecutionUnit executionUnit;
    private Configuration config;
    
    // hostname et URL principale
    private String minivilleHost;
    private String minivilleURL;
    private String minivilleURLXML;

    public MinivilleLikeURLFactory(ExecutionUnit executionUnit, String miniville) {
        
        this.executionUnit = executionUnit;
        this.config = executionUnit.getConfig();
        
        int weightCitizen = config.getMinivilleConfiguration().getWeightCitizen();
        int weightIndustry = config.getMinivilleConfiguration().getWeightIndustry();
        int weightTransport = config.getMinivilleConfiguration().getWeightTransport();
        int weightSecurity = config.getMinivilleConfiguration().getWeightSecurity();
        int weightEnvironment = config.getMinivilleConfiguration().getWeightEnvironment();
        int weightBusiness = config.getMinivilleConfiguration().getWeightBusiness();
        
        int objectiveCitizen = config.getMinivilleConfiguration().getObjectiveCitizen();
        int objectiveIndustry = config.getMinivilleConfiguration().getObjectiveIndustry();
        int objectiveTransport = config.getMinivilleConfiguration().getObjectiveTransport();
        int objectiveSecurity = config.getMinivilleConfiguration().getObjectiveSecurity();
        int objectiveEnvironment = config.getMinivilleConfiguration().getObjectiveEnvironment();
        int objectiveBusiness = config.getMinivilleConfiguration().getObjectiveBusiness();
        
        minivilleHost = miniville + "." + getMinivilleDomain();
        minivilleURL = "http://" + minivilleHost + "/";
        minivilleURLXML = minivilleURL + "xml";
        
        this.useValidator = config.getMinivilleConfiguration().isUseMinivilleValidator();
        
        /*
         * Construction de la liste des URLs à visiter. A chaque URL nous associons :
         * - un id qui va identifier de manière unique chaque URL
         * - l'URL en question
         * - un poids, qui va déterminer sa probabilité de tirage. Le poids total
         * des URLs peut être égal à 100 (ce n'est pas une obligation). Dans ce cas,
         * le poids représente simplement un poucentage.
         * - l'objectif à atteindre
         * - la statistique actuelle (= à l'objectif dans un premier temps)
         * - le tag xml contenant la statistique relative à l'URL
         */
        urlList.add(new WeightedURL(URLList.URL_ID.CITIZEN,     minivilleURL,         weightCitizen,     objectiveCitizen,     0,   "<population>"));   // Habitants
        urlList.add(new WeightedURL(URLList.URL_ID.INDUSTRY,    minivilleURL + "ind", weightIndustry,    objectiveIndustry,    0,   "<unemployment>")); // Industrie
        urlList.add(new WeightedURL(URLList.URL_ID.TRANSPORT,   minivilleURL + "tra", weightTransport,   objectiveTransport,   100, "<transport>"));    // Transport
        urlList.add(new WeightedURL(URLList.URL_ID.SECURITY,    minivilleURL + "sec", weightSecurity,    objectiveSecurity,    0,   "<criminality>"));  // Securité
        urlList.add(new WeightedURL(URLList.URL_ID.ENVIRONMENT, minivilleURL + "env", weightEnvironment, objectiveEnvironment, 0,   "<pollution>"));    // Environnement
        urlList.add(new WeightedURL(URLList.URL_ID.BUSINESS,    minivilleURL + "com", weightBusiness,    objectiveBusiness,    0,   "<incomes>"));      // Commerce
    }
    
    public void initStatisticsGrabber() {
        // Lancement du processus de récupération des statistiques si nous sommes en mode intelligent
        if (config.getMinivilleConfiguration().getHitMode() == HitMode.Intelligent) {
            sg = new StatisticsGrabber(config.getMinivilleConfiguration().getRefreshStatisticsInterval(), executionUnit, this);
            sg.setPriority(Thread.MIN_PRIORITY);
            sg.start();
        }
    }
    
    public String getURL() {
        
        // visite d'une URL au hasard
        switch(config.getMinivilleConfiguration().getHitMode()) {
        case PseudoRandom:
            return(getPseudoRandomURL());
        case Random:
            return(getRandomURL());
        case Intelligent:
            return(getIntelligentURL());
        default:
            return(getRandomURL());
        }
        
    }
    
    private String getRandomURL() {
        // tirage d'un poids au hasard
        int random = (int) (Math.random() * urlList.size());
        
        // récupération de l'URL correspondant au poids tiré
        return(urlList.get(random).getURL());
    }
    
    private String getPseudoRandomURL() {
        
        // tirage d'un poids au hasard
        double randomWeight = Math.random() * urlList.getTotalWeight();
        
        // Si nous nous trouvons en mode intelligent, on regarde si l'URL tirée est
        // compatible avec l'état d'évolution actuel de la miniville
        if (config.getMinivilleConfiguration().getHitMode() == HitMode.Intelligent) {
            boolean appropriateURL = false;
            while (!appropriateURL) {
                appropriateURL = true;
                int citizenCount = urlList.getURLById(URLList.URL_ID.CITIZEN).getStatistic();
                if ((urlList.getURLByWeight(randomWeight).getId() == URLList.URL_ID.INDUSTRY) &&
                    (citizenCount < 50)) {
                    appropriateURL = false;
                }
                if ((urlList.getURLByWeight(randomWeight).getId() == URLList.URL_ID.TRANSPORT) &&
                    (citizenCount < 100)) {
                    appropriateURL = false;
                }
                if ((urlList.getURLByWeight(randomWeight).getId() == URLList.URL_ID.SECURITY) &&
                    (citizenCount < 300)) {
                    appropriateURL = false;
                }
                if ((urlList.getURLByWeight(randomWeight).getId() == URLList.URL_ID.ENVIRONMENT) &&
                    (citizenCount < 500)) {
                    appropriateURL = false;
                }
                if ((urlList.getURLByWeight(randomWeight).getId() == URLList.URL_ID.BUSINESS) &&
                    (citizenCount < 1000)) {
                    appropriateURL = false;
                }
                if (!appropriateURL)
                    randomWeight = Math.random() * urlList.getTotalWeight();
            }
        }
        
        // récupération de l'URL correspondant au poids tiré
        return(urlList.getURLByWeight(randomWeight).getURL());
    }
    
    private String getIntelligentURL() {
        int deltaToObjective = 0;
        WeightedURL urlToHit = null;
        
        for (WeightedURL weightedURL : urlList) {
            switch (weightedURL.getId()) {
            case INDUSTRY:
            case SECURITY:
            case ENVIRONMENT:
                // On calcule le delta par rapport à l'objectif, et on conserve l'URL
                // du delta le plus important
                if (urlList.getURLById(weightedURL.getId()).getStatistic() > weightedURL.getObjective() &&
                    Math.abs(weightedURL.getObjective() - urlList.getURLById(weightedURL.getId()).getStatistic()) > deltaToObjective) {
                    deltaToObjective = Math.abs(weightedURL.getObjective() - urlList.getURLById(weightedURL.getId()).getStatistic());
                    urlToHit = weightedURL;
                }
                break;
            case TRANSPORT:
                // On calcule le delta par rapport à l'objectif, et on conserve l'URL
                // du delta le plus important
                if (urlList.getURLById(weightedURL.getId()).getStatistic() < weightedURL.getObjective() &&
                        Math.abs(weightedURL.getObjective() - urlList.getURLById(weightedURL.getId()).getStatistic()) > deltaToObjective) {
                        deltaToObjective = Math.abs(weightedURL.getObjective() - urlList.getURLById(weightedURL.getId()).getStatistic());
                        urlToHit = weightedURL;
                    }
                break;
            case CITIZEN:
            case BUSINESS:
                // Pas de calcul de delta pour les habitants ou les commerces
                break;
            }
        }
        
        // Si nous avons un delta positif, cela signifie que notre ville manque
        // de quelque chose. On envoie les renforts...
        if (deltaToObjective > 0 && urlToHit != null)
            return(urlToHit.getURL());

        // Si notre ville ne manque de rien, on suit le plan d'approvisionnement général
        return(getPseudoRandomURL());
    }
    
    public LinkedHashMap<String, String> getHeaders() {
        
        LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();
        
        lhm.put("Accept", "image/gif, " +
                "image/x-xbitmap, " +
                "image/jpeg, " +
                "image/pjpeg, " + 
                "application/vnd.ms-excel, " +
                "application/vnd.ms-powerpoint, " +
                "application/msword, " + 
                "application/x-shockwave-flash, " +
                "*/*");

        lhm.put("Accept-Language", HTTPHeaderUtils.getRandomLanguage());
        lhm.put("Accept-Encoding", "gzip, deflate");
        lhm.put("User-Agent", HTTPHeaderUtils.getRandomUserAgent());
        lhm.put("Host", minivilleHost);
        lhm.put("Cookie", "X-MV-Referer=; X-Ref-Ok=1");
        
        return lhm;
        
    }

    public final URLList getUrlList() {
        return urlList;
    }

    public final void setUrlList(URLList urlList) {
        this.urlList = urlList;
    }
    
    public final String getURLValidator() {
        return URLValidator;
    }

    public final boolean isURLValidatorRegex() {
        return false;
    }
    
    public final boolean useValidator() {
        return useValidator;
    }
    
    abstract public String getMinivilleDomain();

    public String getMinivilleHost() {
        return minivilleHost;
    }

    public void setMinivilleHost(String minivilleHost) {
        this.minivilleHost = minivilleHost;
    }

    public String getMinivilleURL() {
        return minivilleURL;
    }

    public void setMinivilleURL(String minivilleURL) {
        this.minivilleURL = minivilleURL;
    }

    public String getMinivilleURLXML() {
        return minivilleURLXML;
    }

    public void setMinivilleURLXML(String minivilleURLXML) {
        this.minivilleURLXML = minivilleURLXML;
    }
    
}
