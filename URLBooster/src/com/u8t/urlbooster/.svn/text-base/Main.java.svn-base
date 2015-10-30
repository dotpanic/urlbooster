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
package com.u8t.urlbooster;

import com.u8t.urlbooster.app.Application;
import com.u8t.urlbooster.app.Console;
import com.u8t.urlbooster.app.Gui;
import com.u8t.urlbooster.components.LogManager;

public class Main {
    
    private enum execMode {
        CONSOLE, GUI;
    }
    
    private static Application app;

    public static void logInfo(String msg) {
        app.logInfo(msg);
    }
    
    public static void main(String[] args) {

        LogManager.initLogManager();
        
        // Paramètres par défaut du programme
        execMode mode = execMode.GUI;
        String configFile = "";
        
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-console"))
                    mode = execMode.CONSOLE;
                if (args[i].equals("-configFile"))
                    configFile = args[++i];
            }
        }

        if (mode == execMode.CONSOLE) {
            app = new Console();
            if (!configFile.equals(""))
                configFile = "urlbooster.ini";
        } else {
            app = new Gui();
            if (!configFile.equals(""))
                configFile = "urlbooster.xml";
        }

        app.getConfig().setPropertiesFile(configFile);
        app.init();
        app.setPriority(Thread.MIN_PRIORITY);
        app.start();
        
        while (!app.isTerminate()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LogManager.traceException(e);
            }
        }
    }
    
    public static final Application getApp() {
        return app;
    }
}
