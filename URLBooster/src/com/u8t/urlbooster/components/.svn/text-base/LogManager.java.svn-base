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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.u8t.urlbooster.Main;
import com.u8t.urlbooster.bean.Configuration.DebugLevel;

public class LogManager {
    
    // Logger
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final Level LOGGER_LEVEL = Level.ALL;
    
    private LogManager() { } ;
    
    public static void traceException(final Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter stackTrace = new PrintWriter(sw);
        LOGGER.finer(e.toString());
        e.printStackTrace(stackTrace);
        LOGGER.finer(sw.toString());
    }
    
    public static void initLogManager() {
        Handler[] handlers = Logger.getLogger("").getHandlers();
        for (Handler handler : handlers) {
            handler.setLevel(Level.ALL);
        }

        LOGGER.setLevel(LOGGER_LEVEL);
    }
    
    public static void setLoggerLevel(final DebugLevel level) {
        switch (level) {
            case All:
                LOGGER.setLevel(Level.ALL);
                break;
            case Severe:
                LOGGER.setLevel(Level.SEVERE);
                break;
            case Warning:
                LOGGER.setLevel(Level.WARNING);
                break;
            case Info:
                LOGGER.setLevel(Level.INFO);
                break;
            case Fine:
                LOGGER.setLevel(Level.FINE);
                break;
            case Finer:
                LOGGER.setLevel(Level.FINER);
                break;
            case Finest:
                LOGGER.setLevel(Level.FINEST);
                break;
            case Off:
                LOGGER.setLevel(Level.OFF);
                break;
            default:
                LOGGER.setLevel(Level.OFF);
                break;
        }
    }

    public static void logSevere(final String msg) {
        LOGGER.log(Level.SEVERE, msg);
    }
    
    public static void logWarwing(final String msg) {
        LOGGER.log(Level.WARNING, msg);
    }
    
    public static void logInfo(final String msg) {
        LOGGER.log(Level.INFO, msg);
    }
    
    public static void logFine(final String msg) {
        LOGGER.log(Level.FINE, msg);
    }
    
    public static void logFiner(final String msg) {
        LOGGER.log(Level.FINER, msg);
    }
    
    public static void logFinest(final String msg) {
        LOGGER.log(Level.FINEST, msg);
    }
}
