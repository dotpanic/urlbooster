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

public class AntivilleURLFactory implements IURLFactory {

    // hostname et URL principale
    public String antivilleHost;
    private String antivilleURL;
    
    // indique si l'on doit utiliser le validator
    private boolean useValidator = true;
    
    // Valideur de l'URL. Doit contenir une chaine de caractère spécifique
    // attendue dans le retour HTTP.
    private static String URLValidator = "trax.motion-twin.com";
    
    public AntivilleURLFactory(String antiville, boolean useValidator) {

        if (antiville != null && !antiville.equals("")) {
            antivilleHost = antiville + ".antiville.fr";
            antivilleURL = "http://" + antivilleHost + "/";
            this.useValidator = useValidator;
        } else {
            Main.getApp().exitProgram("Miniville properties not found!");
        }
    }
    
    public String getURL() {
        return (antivilleURL);
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
        lhm.put("Host", antivilleHost);
        lhm.put("Cookie", "X-MV-Referer=; X-Miv-Ref=1");
        
        return lhm;
        
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
}
