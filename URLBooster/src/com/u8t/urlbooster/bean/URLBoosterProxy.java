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

public final class URLBoosterProxy {
    
    private String proxyName = "";
    private String proxyPort = "";
    
    public URLBoosterProxy() { }
    
    public URLBoosterProxy(final String proxyName, final String proxyPort) {
        this.proxyName = proxyName;
        this.proxyPort = proxyPort;
    }
    
    public final String getProxyName() {
        return proxyName;
    }
    
    public final void setProxyName(String proxyName) {
        this.proxyName = proxyName;
    }
    
    public final String getProxyPort() {
        return proxyPort;
    }
    
    public final void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }
}
