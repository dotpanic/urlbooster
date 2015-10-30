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
import java.io.IOException;
import java.io.InputStreamReader;

import com.u8t.urlbooster.components.ExecutionUnit;
import com.u8t.urlbooster.components.LogManager;
import com.u8t.urlbooster.utils.StringUtils;

public class Console extends Application {
    
    public void run() {

        ExecutionUnit executionUnit = new ExecutionUnit(getConfig(), this);
        executionUnit.setPriority(Thread.MIN_PRIORITY);
        executionUnit.start();
        
        while (!executionUnit.isTerminate()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LogManager.traceException(e);
            }
        }

        logInfo("Main loop terminated.");

        waitForUserInput();
    }

    public void exitProgram(String reason) {
        logInfo("program has exited");
        if (!reason.equals(""))
            logInfo("reason: " + reason);
        else
            logInfo("reason: unknown reason");

        waitForUserInput();

        System.exit(-1);
    }
    
    public void waitForUserInput() {
        
        logInfo(StringUtils.getCenteredString("PROGRAM STOPPED - PRESS ENTER TO CONTINUE", '#'));

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader input = new BufferedReader(isr);

        try {
            input.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
