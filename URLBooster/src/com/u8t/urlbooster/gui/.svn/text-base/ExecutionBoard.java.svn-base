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
package com.u8t.urlbooster.gui;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;
import com.u8t.urlbooster.bean.Configuration;
import com.u8t.urlbooster.bean.Configuration.ConfigType;
import com.u8t.urlbooster.components.ExecutionUnit;
import com.u8t.urlbooster.components.LogManager;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class ExecutionBoard {


    private ExecutionUnit executionUnit;
    
    protected boolean terminate = false;
    
    private Shell dialogShell;
    private Button terminateButton;
    private Group statisticsGroup;
    private Button pauseButton;
    private Label elapsedTimeCount;
    private Label elapsedTimeLabel;
    private Label remainingHitsCount;
    private Label remainingHitsLabel;
    private Label outputLabel;
    private ProgressBar progressBar;
    private Label badHitsCount;
    private Label badHitsLabel;
    private Label goodHitsCount;
    private Label goodHitsLabel;
    private Label totalHitsCount;
    private Label totalHitsLabel;
    protected Text outputText;
    
    protected String textToAddToBoard;
    protected int totalHits = 0;
    protected int goodHits = 0;
    protected int badHits = 0;
    protected long startDate = System.currentTimeMillis();
    protected int diffDateSeconds = 0;
    protected int diffDateMinutes = 0;
    protected int totalProxies = 0;
    protected int remainingProxies = 0;
    
    private Terminator terminator;

    public ExecutionBoard(Configuration config) {
        executionUnit = new ExecutionUnit(config, this);
        executionUnit.setPriority(Thread.MIN_PRIORITY);
        executionUnit.start();
    }
    
    public void open() {

        try {
            dialogShell = new Shell(SWT.MIN | SWT.MAX | SWT.RESIZE);

            {
                //Register as a resource user - SWTResourceManager will
                //handle the obtaining and disposing of resources
                SWTResourceManager.registerResourceUser(dialogShell);
            }
            
            FormLayout dialogShellLayout = new FormLayout();

            dialogShell.setLayout(dialogShellLayout);
            dialogShell.layout();
            dialogShell.pack();
            dialogShell.setSize(449, 438);
            dialogShell.setMinimumSize(449, 438);
            dialogShell.setImage(SWTResourceManager.getImage("/images/CreateEClass_eOperations_EOperation_on.gif"));
            dialogShell.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
            dialogShell.addListener(SWT.Close, new Listener() {
                public void handleEvent(final Event event) {
                    LogManager.logFiner(event.toString());
                    if (boardClose()) {
                        event.doit = true;
                    } else
                        event.doit = false;
                }
            });
            
            if (executionUnit.getConfig().getConfigType() == ConfigType.Generic)
                dialogShell.setText("Execution board - Generic - " + executionUnit.getConfig().getGenericURL());
            else if (executionUnit.getConfig().getConfigType() == ConfigType.Miniville ||
                  executionUnit.getConfig().getConfigType() == ConfigType.Antiville ||
                  executionUnit.getConfig().getConfigType() == ConfigType.MyMinicity)
                  dialogShell.setText("Execution board - " + executionUnit.getConfig().getConfigType() + " - " + executionUnit.getConfig().getMinivilleConfiguration().getMinivilleName());
                {
                    statisticsGroup = new Group(dialogShell, SWT.NONE);
                    statisticsGroup.setLayout(null);
                    statisticsGroup.setText("Statistics");
                    FormData statisticsGroupLData = new FormData();
                    statisticsGroupLData.width = 421;
                    statisticsGroupLData.height = 40;
                    statisticsGroupLData.right =  new FormAttachment(1000, 1000, -7);
                    statisticsGroupLData.top =  new FormAttachment(0, 1000, 5);
                    statisticsGroupLData.left =  new FormAttachment(0, 1000, 7);
                    statisticsGroup.setLayoutData(statisticsGroupLData);
                    statisticsGroup.setBackground(Display.getCurrent()
                        .getSystemColor(SWT.COLOR_WHITE));
                    {
                        totalHitsLabel = new Label(statisticsGroup, SWT.NONE);
                        totalHitsLabel.setText("Total hits");
                        totalHitsLabel.setBounds(14, 21, 49, 14);
                        totalHitsLabel.setBackground(Display.getCurrent()
                            .getSystemColor(SWT.COLOR_WHITE));
                    }
                    {
                        totalHitsCount = new Label(statisticsGroup, SWT.NONE);
                        totalHitsCount.setText("0");
                        totalHitsCount.setBounds(112, 21, 49, 14);
                        totalHitsCount.setFont(SWTResourceManager.getFont(
                            "Tahoma",
                            8,
                            1,
                            false,
                            false));
                        totalHitsCount.setBackground(Display.getCurrent()
                            .getSystemColor(SWT.COLOR_WHITE));
                    }
                    {
                        goodHitsLabel = new Label(statisticsGroup, SWT.NONE);
                        goodHitsLabel.setText("Good hits");
                        goodHitsLabel.setBounds(182, 21, 49, 14);
                        goodHitsLabel.setBackground(Display.getCurrent()
                            .getSystemColor(SWT.COLOR_WHITE));
                    }
                    {
                        goodHitsCount = new Label(statisticsGroup, SWT.NONE);
                        goodHitsCount.setText("0");
                        goodHitsCount.setBounds(238, 21, 49, 14);
                        goodHitsCount.setFont(SWTResourceManager.getFont(
                            "Tahoma",
                            8,
                            1,
                            false,
                            false));
                        goodHitsCount.setBackground(Display.getCurrent()
                            .getSystemColor(SWT.COLOR_WHITE));
                        goodHitsCount.setForeground(SWTResourceManager
                            .getColor(0, 128, 0));
                    }
                    {
                        elapsedTimeLabel = new Label(statisticsGroup, SWT.NONE);
                        elapsedTimeLabel.setText("Elapsed time");
                        elapsedTimeLabel.setBounds(308, 21, 63, 14);
                        elapsedTimeLabel.setBackground(SWTResourceManager
                            .getColor(255, 255, 255));
                    }
                    {
                        elapsedTimeCount = new Label(statisticsGroup, SWT.NONE);
                        elapsedTimeCount.setText("00:00");
                        elapsedTimeCount.setBounds(378, 21, 42, 14);
                        elapsedTimeCount.setBackground(SWTResourceManager
                            .getColor(255, 255, 255));
                        elapsedTimeCount.setFont(SWTResourceManager.getFont(
                            "Tahoma",
                            8,
                            1,
                            false,
                            false));
                    }
                    {
                        remainingHitsLabel = new Label(
                            statisticsGroup,
                            SWT.NONE);
                        remainingHitsLabel.setText("Remaining proxies");
                        remainingHitsLabel.setBounds(14, 35, 91, 14);
                        remainingHitsLabel.setBackground(SWTResourceManager
                            .getColor(255, 255, 255));
                    }
                    {
                        remainingHitsCount = new Label(
                            statisticsGroup,
                            SWT.NONE);
                        remainingHitsCount.setText("0");
                        remainingHitsCount.setBounds(112, 35, 49, 14);
                        remainingHitsCount.setBackground(SWTResourceManager
                            .getColor(255, 255, 255));
                        remainingHitsCount.setFont(SWTResourceManager.getFont(
                            "Tahoma",
                            8,
                            1,
                            false,
                            false));
                    }
                    {
                        badHitsLabel = new Label(statisticsGroup, SWT.NONE);
                        badHitsLabel.setText("Bad hits");
                        badHitsLabel.setBounds(182, 35, 42, 14);
                        badHitsLabel.setBackground(Display.getCurrent()
                            .getSystemColor(SWT.COLOR_WHITE));
                    }
                    {
                        badHitsCount = new Label(statisticsGroup, SWT.NONE);
                        badHitsCount.setText("0");
                        badHitsCount.setBounds(238, 35, 49, 14);
                        badHitsCount.setFont(SWTResourceManager.getFont(
                            "Tahoma",
                            8,
                            1,
                            false,
                            false));
                        badHitsCount.setBackground(Display.getCurrent()
                            .getSystemColor(SWT.COLOR_WHITE));
                        badHitsCount.setForeground(SWTResourceManager.getColor(
                            217,
                            0,
                            0));
                    }
                }
                {
                    outputLabel = new Label(dialogShell, SWT.NONE);
                    outputLabel.setText("Output");
                    FormData outputLabelLData = new FormData();
                    outputLabelLData.width = 63;
                    outputLabelLData.height = 14;
                    outputLabelLData.top =  new FormAttachment(0, 1000, 91);
                    outputLabelLData.left =  new FormAttachment(0, 1000, 7);
                    outputLabel.setLayoutData(outputLabelLData);
                    outputLabel.setBackground(Display.getCurrent()
                        .getSystemColor(SWT.COLOR_WHITE));
                }
                {
                    outputText = new Text(dialogShell, SWT.MULTI
                        | SWT.WRAP
                        | SWT.V_SCROLL
                        | SWT.BORDER);
                    FormData outputTextLData = new FormData();
                    outputTextLData.width = 398;
                    outputTextLData.height = 250;
                    outputTextLData.right =  new FormAttachment(1000, 1000, -7);
                    outputTextLData.bottom =  new FormAttachment(1000, 1000, -39);
                    outputTextLData.top =  new FormAttachment(0, 1000, 109);
                    outputTextLData.left =  new FormAttachment(0, 1000, 7);
                    outputText.setLayoutData(outputTextLData);
                    outputText.setFont(SWTResourceManager.getFont("Comic Sans MS", 7, 0, false, false));
                }
                {
                    pauseButton = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
                    pauseButton.setText("Pause");
                    FormData pauseButtonLData = new FormData();
                    pauseButtonLData.width = 63;
                    pauseButtonLData.height = 28;
                    pauseButtonLData.left =  new FormAttachment(0, 1000, 7);
                    pauseButtonLData.bottom =  new FormAttachment(1000, 1000, -5);
                    pauseButton.setLayoutData(pauseButtonLData);
                    pauseButton.addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            pauseButtonWidgetSelected(evt);
                        }
                    });
                }
                
            {
                terminateButton = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
                terminateButton.setText("Terminate");
                FormData terminateButtonLData = new FormData();
                terminateButtonLData.width = 63;
                terminateButtonLData.height = 28;
                terminateButtonLData.bottom =  new FormAttachment(1000, 1000, -5);
                terminateButtonLData.right =  new FormAttachment(1000, 1000, -7);
                terminateButton.setLayoutData(terminateButtonLData);
                terminateButton.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent evt) {
                        terminateButtonWidgetSelected(evt);
                    }
                });
            }
            {
                progressBar = new ProgressBar(dialogShell, SWT.NONE);
                FormData progressBarLData = new FormData();
                progressBarLData.width = 427;
                progressBarLData.height = 14;
                progressBarLData.top =  new FormAttachment(0, 1000, 71);
                progressBarLData.left =  new FormAttachment(0, 1000, 7);
                progressBarLData.right =  new FormAttachment(1000, 1000, -7);
                progressBar.setLayoutData(progressBarLData);
            }

            dialogShell.open();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected final boolean boardClose() {
        
        if (terminate == true) {
            return true;
        }
        
        MessageBox messageBox = new MessageBox(dialogShell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
        messageBox.setText("Confirmation");
        messageBox.setMessage("Do you really want to end this execution?");

        if (getTerminateButton().getText().equals("Close") || (messageBox.open() == SWT.YES)) {
            terminate = true;
            executionUnit.stopExecutor();
            executionUnit.interrupt();
            dialogShell.setVisible(false);
            
            terminator = new Terminator(this);
            terminator.start();
        }
        
        return false;
    }
    
    class Terminator extends Thread {
        public ExecutionBoard executionBoard;
        
        public Terminator (ExecutionBoard executionBoard) {
            this.executionBoard = executionBoard;
        }
        
        public void run() {

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                LogManager.traceException(e);
            }
            Display.getDefault().syncExec(new Runnable() {
                public void run() {
                    executionBoard.getDialogShell().close();
                }
            });
        }
    }
    
    public synchronized void logOutputResult(String info) {

        if (!terminate) {
        
            textToAddToBoard = info;
            
            Display.getDefault().syncExec(new Runnable() {
                public void run() {
                    outputText.append(textToAddToBoard);
                    if (!textToAddToBoard.equals("."))
                        outputText.append("\n");
                }
            });
        }
    }
    
    public synchronized void finalizeCloseButton() {
        
        if (!terminate) {

            Display.getDefault().syncExec(new Runnable() {
                public void run() {
                    getTerminateButton().setText("Close");
                }
            });
        }
    }
    
    public synchronized void countTotalHit() {
        
        totalHits++;
        
        if (!terminate) {

            Display.getDefault().syncExec(new Runnable() {
                public void run() {
                   getTotalHitsCount().setText("" + getTotalHits());
                }
            });
        }
    }
    
    public synchronized void countGoodHit() {
        
        goodHits++;
        
        if (!terminate) {

            Display.getDefault().syncExec(new Runnable() {
                public void run() {
                    getGoodHitsCount().setText("" + getGoodHits());
                }
            });
        }
    }
    
    public synchronized void countBadHit() {
        badHits++;
        
        if (!terminate) {

            Display.getDefault().syncExec(new Runnable() {
                public void run() {
                    getBadHitsCount().setText("" + getBadHits());
                }
            });
        }
    }
    
    protected void terminateButtonWidgetSelected(SelectionEvent evt) {
        LogManager.logFiner("terminateButton.widgetSelected, event=" + evt);
        dialogShell.close();
    }

    public final int getGoodHits() {
        return goodHits;
    }

    public final void setGoodHits(int goodHits) {
        this.goodHits = goodHits;
    }

    public final int getBadHits() {
        return badHits;
    }

    public final void setBadHits(int badHits) {
        this.badHits = badHits;
    }

    public final int getTotalHits() {
        return totalHits;
    }

    public final void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public final Shell getDialogShell() {
        return dialogShell;
    }

    public final void setDialogShell(Shell dialogShell) {
        this.dialogShell = dialogShell;
    }

    public final Label getBadHitsCount() {
        return badHitsCount;
    }

    public final Label getGoodHitsCount() {
        return goodHitsCount;
    }

    public final Label getTotalHitsCount() {
        return totalHitsCount;
    }

    public final Button getTerminateButton() {
        return terminateButton;
    }
    
    protected void pauseButtonWidgetSelected(SelectionEvent evt) {
        LogManager.logFiner("pauseButton.widgetSelected, event=" + evt);
        if (executionUnit.isExecutorPaused()) {
            executionUnit.requireResume();
            pauseButton.setText("Pause");
        } else {
            executionUnit.requirePause();
            pauseButton.setText("Resume");
        }
        pauseButton.setEnabled(false);
    }
    
    public void enablePauseButton() {
        if (!terminate) {

            Display.getDefault().syncExec(new Runnable() {
                public void run() {
                    getPauseButton().setEnabled(true);
                }
            });
        }
    }
    
    public void uncountProxy() {
        remainingProxies --;
        
        if (!terminate) {

            Display.getDefault().syncExec(new Runnable() {
                public void run() {
                    getRemainingHitsCount().setText("" + getRemainingProxies());
                    getProgressBar().setMaximum(getTotalProxies());
                    getProgressBar().setSelection(getTotalProxies() - getRemainingProxies());
                }
            });
        }
    }
    
    public void updateTime() {
        diffDateSeconds = (int) (System.currentTimeMillis() - startDate) / 1000;
        
        diffDateMinutes = diffDateSeconds / 60;
        diffDateSeconds -= diffDateMinutes * 60;
        
        if (!terminate) {

            Display.getDefault().syncExec(new Runnable() {
                public void run() {
                    NumberFormat formatter = new DecimalFormat("##00");
                    getElapsedTimeCount().setText(formatter.format(getDiffDateMinutes()) + ":" +
                            formatter.format(getDiffDateSeconds()));
                }
            });
        }
    }

    public final Label getRemainingHitsCount() {
        return remainingHitsCount;
    }

    public final void setRemainingHitsCount(Label remainingHitsCount) {
        this.remainingHitsCount = remainingHitsCount;
    }

    public final int getRemainingProxies() {
        return remainingProxies;
    }

    public final Label getElapsedTimeCount() {
        return elapsedTimeCount;
    }

    public final int getDiffDateSeconds() {
        return diffDateSeconds;
    }

    public final void setDiffDateSeconds(int diffDateSeconds) {
        this.diffDateSeconds = diffDateSeconds;
    }

    public final int getDiffDateMinutes() {
        return diffDateMinutes;
    }

    public final void setDiffDateMinutes(int diffDateMinutes) {
        this.diffDateMinutes = diffDateMinutes;
    }

    public final Button getPauseButton() {
        return pauseButton;
    }

    public final int getTotalProxies() {
        return totalProxies;
    }

    public final void setTotalProxies(int totalProxies) {
        this.totalProxies = totalProxies;
        this.remainingProxies = totalProxies;
    }

    public final ProgressBar getProgressBar() {
        return progressBar;
    }
}
