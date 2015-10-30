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

import com.u8t.urlbooster.components.ExecutionUnit;


public class MyMinicityURLFactory extends MinivilleLikeURLFactory {
    
    private static final String minivilleDomain = "myminicity.com";

    public MyMinicityURLFactory(ExecutionUnit executionUnit, String miniville) {
        super(executionUnit, miniville);
    }
    
    public final String getMinivilleDomain() {
        return minivilleDomain;
    }

    public LinkedHashMap<String, String> getHeaders() {
        
        LinkedHashMap<String, String> lhm = super.getHeaders();
        lhm.put("Cookie", "X-MV-Referer=; X-Mc-Ref=1");
        
        return lhm;
    }
}
