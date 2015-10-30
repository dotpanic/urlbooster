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

import com.u8t.urlbooster.Main;
import com.u8t.urlbooster.utils.HTTPHeaderUtils;
import com.u8t.urlbooster.utils.StringTags;

public class GenericURLFactory implements IURLFactory {

    // URL principale
    private String genericURL;
    
    // Valideur de l'URL. Doit contenir une chaine de caractère spécifique
    // attendue dans le retour HTTP.
    private String URLValidator = "";
    
    // Indique si le valideur est une expression régulière
    private boolean URLValidatorRegex = false;
    
    private int counter = 0;
    
    public GenericURLFactory(String URL, String URLValidator, boolean URLValidatorRegex) {

        if (URL != null && !URL.equals("")) {
            genericURL = URL;
            this.URLValidator = URLValidator;
            this.URLValidatorRegex = URLValidatorRegex;

        } else {
            Main.getApp().exitProgram("GenericURL properties not found!");
        }
    }
    
    public String getURL() {
        
        String builtURL = genericURL;
        
        // Substitution des tags spéciaux
        while (builtURL.contains("<c>"))
            builtURL = builtURL.replaceAll("<c>", "" + ++counter);
        while (builtURL.contains("<count>"))
            builtURL = builtURL.replaceAll("<count>", "" + ++counter);
        
        builtURL = StringTags.substituteTags(builtURL);
        
        return (builtURL);
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
        
        return lhm;
    }
    
    public final String getURLValidator() {
        return URLValidator;
    }
    
    public final boolean isURLValidatorRegex() {
        return URLValidatorRegex;
    }
    
    public final boolean useValidator() {
        return true;
    }
}
