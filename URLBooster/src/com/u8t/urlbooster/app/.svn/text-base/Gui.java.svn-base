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
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;
import com.u8t.urlbooster.bean.Configuration.ConfigType;
import com.u8t.urlbooster.bean.Configuration.DebugLevel;
import com.u8t.urlbooster.bean.Configuration.HitMode;
import com.u8t.urlbooster.bean.Configuration.HitOrder;
import com.u8t.urlbooster.bean.Configuration.ProxyType;
import com.u8t.urlbooster.components.LogManager;
import com.u8t.urlbooster.gui.AboutBox;
import com.u8t.urlbooster.gui.ExecutionBoard;


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
public class Gui extends Application {

    private Display display;
    private Combo debugLevelCombo;
    private Label proxiesTypesLabel;
    private Button useValidatorButton;
    private Group group1;
    private Button excludeGoogProxiesButton;
    private Button resolveNameServersButton;
    private Button genericURLValidatorRegex;
    private Text genericURLValidator;
    private Label genericURLValidatorLabel;
    private MenuItem aboutMenuItem;
    private Menu helpMenu;
    private MenuItem helpMenuHeader;
    private MenuItem saveConfigMenuItem;
    private MenuItem openConfigMenuItem;
    private Menu fileMenu;
    private Text genericURL;
    private Label genericUrlLabel;
    private Text iterations;
    private Label iterationsLabel;
    private Label label4;
    private Label label3;
    private Label label2;
    private MenuItem fileMenuHeader;
    private Menu menu;
    private Label label1;
    private Text environmentObjective;
    private Text securityObjective;
    private Text businessWeight;
    private Text environmentWeight;
    private Text securityWeight;
    private Text industryObjective;
    private Text transportObjective;
    private Text industryWeight;
    private Text transportWeight;
    private Label businessLabel;
    private Label environmentLabel;
    private Label securityLabel;
    private Label industryLabel;
    private Label transportLabel;
    private Label citizenLabel;
    private Text citizenWeight;
    private Button autoCleanupProxiesButton;
    private Combo proxiesTypesCombo;
    private Group proxiesGroup;
    private Button goButton;
    private Label statsRefreshIntervalSLabel;
    private Text statsRefreshInterval;
    private Label statisticsRefreshIntervalLabel;
    private Label hitModeLabel;
    private Combo hitModeCombo;
    private Group minivilleConfigurationGroup;
    private Group genericConfigurationGroup;
    private Group advancedGroup;
    private Label maxThreadInactivitySLabel;
    private Label httpConnectionTimeoutSLabel;
    private Label debugLevelLabel;
    private Button emitBeepButton;
    private Label maxThreadInactivityLabel;
    private Label httpConnectionTimeoutLabel;
    private Combo configCombo;
    private Combo proxiesHitOrderCombo;
    private Label proxiesHitOrderLabel;
    private Button openProxiesPathButton;
    private Button openGoodProxiesPathButton;
    private Button openBadProxiesPathButton;
    private Text proxiesPath;
    private Label proxiesPathLabel;
    private Text maxThreadInactivity;
    private Text httpConnectionTimeout;
    private Text maxThreadCount;
    private Button saveBadProxiesButton;
    private Label maxThreadCountLabel;
    private Text badProxiesPath;
    private Label badProxiesPathLabel;
    private Label goodProxiesPathLabel;
    private Text goodProxiesPath;
    private Button saveGoodProxiesButton;
    private Label cityLabel;
    private Label configTypeLabel;
    private Text cityText;
    private Shell shell;
    
    // About window
    //$hide>>$
    private AboutBox aboutWindow;
    //$hide<<$
    
    public void run() {
        
        //$hide>>$
        
        display = Display.getDefault();
        
        final Image splashImage = SWTResourceManager.getImage("/images/Splash.jpg");
        final Shell splash = new Shell(SWT.ON_TOP);
        Label label = new Label(splash, SWT.NONE);
        label.setImage(splashImage);
        FormLayout layout = new FormLayout();
        splash.setLayout(layout);
        splash.pack();
        Rectangle splashRect = splash.getBounds();
        Rectangle displayRect = display.getBounds();
        int x = (displayRect.width - splashRect.width) / 2;
        int y = (displayRect.height - splashRect.height) / 2;
        splash.setLocation(x, y);
        splash.open();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LogManager.traceException(e);
        }
        
        //$hide<<$
        
        createContents();
        
        //$hide>>$
        
        Rectangle windowRect = shell.getBounds();
        x = (displayRect.width - windowRect.width) / 2;
        y = (displayRect.height - windowRect.height) / 2;
        if (x > 0 && y > 0) {
            shell.setLocation(x, y);
        }
        
        initializeContents();
        
        shell.open();
        shell.layout();

        // On demande si l'on souhaite charger la conf par défaut
        MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
        messageBox.setText("Default configuration");
        messageBox.setMessage("Do you want to load the default configuration (urlbooster.xml) ?");

        splash.close();
        
        if (messageBox.open() == SWT.YES) {
            getConfig().loadConfigFromFile(getConfig().getPropertiesFile());
        }
        
        updateFormWithConfig();
        
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
        //$hide<<$
    }
    
    private void createContents() {
        shell = new Shell(SWT.MIN);

            {
                //Register as a resource user - SWTResourceManager will
                //handle the obtaining and disposing of resources
                SWTResourceManager.registerResourceUser(shell);
            }

        shell.setLayout(null);
        //$hide>>$
        shell.addListener(SWT.Close, new Listener() {
            public void handleEvent(final Event event) {
                LogManager.logFiner(event.toString());
                applicationClose();
                event.doit = false;
            }
        });
        //$hide<<$
        shell.setImage(SWTResourceManager.getImage("/images/urlbooster.gif"));
        shell.setText("URLBooster");
        shell.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
        shell.setSize(503, 525);
        {
            menu = new Menu(shell, SWT.BAR);
            shell.setMenuBar(menu);
            {
                fileMenuHeader = new MenuItem(menu, SWT.CASCADE);
                fileMenuHeader.setText("&File");
                {
                    fileMenu = new Menu(fileMenuHeader);
                    fileMenuHeader.setMenu(fileMenu);
                    {
                        openConfigMenuItem = new MenuItem(fileMenu, SWT.PUSH);
                        openConfigMenuItem.setText("&Open config...");
                        openConfigMenuItem
                            .addSelectionListener(new SelectionAdapter() {
                            public void widgetSelected(SelectionEvent evt) {
                                openConfigMenuItemWidgetSelected(evt);
                            }
                            });
                    }
                    {
                        saveConfigMenuItem = new MenuItem(fileMenu, SWT.PUSH);
                        saveConfigMenuItem.setText("&Save config as...");
                        saveConfigMenuItem
                            .addSelectionListener(new SelectionAdapter() {
                            public void widgetSelected(SelectionEvent evt) {
                                saveConfigMenuItemWidgetSelected(evt);
                            }
                            });
                    }
                }
            }
            {
                helpMenuHeader = new MenuItem(menu, SWT.CASCADE);
                helpMenuHeader.setText("&Help");
                {
                    helpMenu = new Menu(helpMenuHeader);
                    helpMenuHeader.setMenu(helpMenu);
                    {
                        aboutMenuItem = new MenuItem(helpMenu, SWT.PUSH);
                        aboutMenuItem.setText("&About");
                        aboutMenuItem
                            .addSelectionListener(new SelectionAdapter() {
                            public void widgetSelected(SelectionEvent evt) {
                                aboutMenuItemWidgetSelected(evt);
                            }
                            });
                    }
                }
            }

        }

        {
            configCombo = new Combo(shell, SWT.READ_ONLY);
            configCombo.setBounds(84, 7, 98, 21);
            configCombo.setText("generic");
            configCombo.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent evt) {
                    configComboWidgetSelected(evt);
                }
            });
        }
        {
            iterations = new Text(shell, SWT.BORDER);
            iterations.setBounds(385, 7, 35, 21);
            iterations.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent evt) {
                    iterationsFocusLost(evt);
                }
            });
        }
        {
            goButton = new Button(shell, SWT.PUSH | SWT.CENTER);
            goButton.setText("Go !");
            goButton.setBounds(427, 7, 63, 21);
            goButton.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent evt) {
                    goButtonWidgetSelected(evt);
                }
            });
        }
        {
            genericConfigurationGroup = new Group(shell, SWT.NONE);
            genericConfigurationGroup.setLayout(null);
            genericConfigurationGroup.setText("Generic configuration");
            genericConfigurationGroup.setBounds(7, 35, 483, 161);
            genericConfigurationGroup.setBackground(Display.getCurrent()
                .getSystemColor(SWT.COLOR_WHITE));
            {
                genericUrlLabel = new Label(genericConfigurationGroup, SWT.NONE);
                genericUrlLabel.setText("URL to hit");
                genericUrlLabel.setBounds(21, 21, 49, 14);
                genericUrlLabel.setBackground(Display.getCurrent()
                    .getSystemColor(SWT.COLOR_WHITE));
            }
            {
                genericURL = new Text(genericConfigurationGroup, SWT.BORDER);
                genericURL.setText("http://");
                genericURL.setBounds(7, 35, 469, 21);
                genericURL.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent evt) {
                        genericURLFocusLost(evt);
                    }
                });
            }
            {
                genericURLValidatorLabel = new Label(
                    genericConfigurationGroup,
                    SWT.NONE);
                genericURLValidatorLabel.setText("URL Validator (specific text to be found in the HTTP response for the proxy to be validated)");
                genericURLValidatorLabel.setBounds(21, 63, 441, 14);
                genericURLValidatorLabel.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
            }
            {
                genericURLValidator = new Text(
                    genericConfigurationGroup,
                    SWT.BORDER);
                genericURLValidator.setBounds(7, 77, 469, 21);
                genericURLValidator.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent evt) {
                        urlValidatorTextFocusLost(evt);
                    }
                });
            }
            {
                genericURLValidatorRegex = new Button(genericConfigurationGroup, SWT.CHECK | SWT.LEFT);
                genericURLValidatorRegex.setText("Validator is a regular expression");
                genericURLValidatorRegex.setBounds(23, 102, 179, 17);
                genericURLValidatorRegex.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
                genericURLValidatorRegex.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent evt) {
                        genericURLValidatorRegexWidgetSelected(evt);
                    }
                });
            }
        }
        {
            minivilleConfigurationGroup = new Group(shell, SWT.NONE);
            minivilleConfigurationGroup.setLayout(null);
            minivilleConfigurationGroup.setText("Miniville / Antiville / MyMinicity configuration");
            minivilleConfigurationGroup.setBounds(7, 35, 483, 161);
            minivilleConfigurationGroup.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
            minivilleConfigurationGroup.setVisible(false);
            {
                cityLabel = new Label(minivilleConfigurationGroup, SWT.NONE);
                cityLabel.setText("City");
                cityLabel.setBounds(21, 24, 21, 21);
                cityLabel.setBackground(Display.getCurrent().getSystemColor(
                    SWT.COLOR_WHITE));
            }
            {
                cityText = new Text(minivilleConfigurationGroup, SWT.BORDER);
                cityText.setBounds(49, 21, 105, 21);
                cityText.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent evt) {
                        minivilleTextFocusLost(evt);
                    }
                });
            }
            {
                hitModeLabel = new Label(minivilleConfigurationGroup, SWT.NONE);
                hitModeLabel.setText("Hit mode");
                hitModeLabel.setBounds(173, 24, 49, 21);
                hitModeLabel.setBackground(Display.getCurrent().getSystemColor(
                    SWT.COLOR_WHITE));
            }
            {
                hitModeCombo = new Combo(
                    minivilleConfigurationGroup,
                    SWT.READ_ONLY);
                hitModeCombo.setText("Intelligent");
                hitModeCombo.setBounds(224, 21, 98, 21);
                hitModeCombo.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent evt) {
                        hitModeComboWidgetSelected(evt);
                    }
                });
            }
            {
                statisticsRefreshIntervalLabel = new Label(
                    minivilleConfigurationGroup,
                    SWT.NONE);
                statisticsRefreshIntervalLabel.setText("Stats refresh");
                statisticsRefreshIntervalLabel.setBounds(343, 24, 70, 21);
                statisticsRefreshIntervalLabel.setBackground(Display
                    .getCurrent().getSystemColor(SWT.COLOR_WHITE));
            }
            {
                statsRefreshInterval = new Text(
                    minivilleConfigurationGroup,
                    SWT.BORDER);
                statsRefreshInterval.setText("300");
                statsRefreshInterval.setBounds(413, 21, 35, 21);
                statsRefreshInterval.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent evt) {
                        statsRefreshIntervalFocusLost(evt);
                    }
                });
            }
            {
                statsRefreshIntervalSLabel = new Label(
                    minivilleConfigurationGroup,
                    SWT.NONE);
                statsRefreshIntervalSLabel.setText("s");
                statsRefreshIntervalSLabel.setBounds(455, 24, 14, 21);
                statsRefreshIntervalSLabel.setBackground(Display.getCurrent()
                    .getSystemColor(SWT.COLOR_WHITE));
            }
            {
                group1 = new Group(minivilleConfigurationGroup, SWT.NONE);
                group1.setLayout(null);
                group1.setText("Weights / Objectives");
                group1.setBounds(8, 50, 341, 103);
                group1.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
            }
            {
                useValidatorButton = new Button(minivilleConfigurationGroup, SWT.CHECK | SWT.LEFT);
                useValidatorButton.setText("Use validator");
                useValidatorButton.setBounds(365, 56, 90, 21);
                useValidatorButton.setBackground(Display.getCurrent()
                        .getSystemColor(SWT.COLOR_WHITE));
                useValidatorButton.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent evt) {
                        useValidatorButtonWidgetSelected(evt);
                    }
                });
            }
            {
                citizenLabel = new Label(group1, SWT.NONE);
                citizenLabel.setText("Citizen");
                citizenLabel.setBounds(13, 21, 42, 21);
                citizenLabel.setBackground(Display.getCurrent().getSystemColor(
                    SWT.COLOR_WHITE));
            }
            {
                citizenWeight = new Text(group1, SWT.BORDER);
                citizenWeight.setText("0");
                citizenWeight.setBounds(76, 18, 35, 21);
                citizenWeight.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent evt) {
                        citizenWeightFocusLost(evt);
                    }
                });
            }
            {
                transportLabel = new Label(
                        group1,
                    SWT.NONE);
                transportLabel.setText("Transport");
                transportLabel.setBounds(13, 49, 63, 21);
                transportLabel.setBackground(Display.getCurrent()
                    .getSystemColor(SWT.COLOR_WHITE));
            }
            {
                transportWeight = new Text(
                        group1,
                    SWT.BORDER);
                transportWeight.setText("0");
                transportWeight.setBounds(76, 46, 35, 21);
                transportWeight.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent evt) {
                        transportWeightFocusLost(evt);
                    }
                });
            }
            {
                label1 = new Label(group1, SWT.NONE);
                label1.setText("/");
                label1.setBounds(118, 49, 7, 21);
                label1.setBackground(Display.getCurrent().getSystemColor(
                    SWT.COLOR_WHITE));
            }
            {
                transportObjective = new Text(
                        group1,
                    SWT.BORDER);
                transportObjective.setText("100");
                transportObjective.setBounds(125, 46, 35, 21);
                transportObjective.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent evt) {
                        transportObjectiveFocusLost(evt);
                    }
                });
            }
            {
                industryLabel = new Label(group1, SWT.NONE);
                industryLabel.setText("Industry");
                industryLabel.setBounds(13, 77, 63, 21);
                industryLabel.setBackground(Display.getCurrent()
                    .getSystemColor(SWT.COLOR_WHITE));
            }
            {
                industryWeight = new Text(
                        group1,
                    SWT.BORDER);
                industryWeight.setText("0");
                industryWeight.setBounds(76, 74, 35, 21);
                industryWeight.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent evt) {
                        industryWeightFocusLost(evt);
                    }
                });
            }
            {
                label2 = new Label(group1, SWT.NONE);
                label2.setText("/");
                label2.setBounds(118, 77, 7, 21);
                label2.setBackground(Display.getCurrent().getSystemColor(
                    SWT.COLOR_WHITE));
            }
            {
                industryObjective = new Text(
                        group1,
                    SWT.BORDER);
                industryObjective.setText("0");
                industryObjective.setBounds(125, 74, 35, 21);
                industryObjective.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent evt) {
                        industryObjectiveFocusLost(evt);
                    }
                });
            }
            {
                securityLabel = new Label(group1, SWT.NONE);
                securityLabel.setText("Security");
                securityLabel.setBounds(177, 21, 63, 21);
                securityLabel.setBackground(Display.getCurrent()
                    .getSystemColor(SWT.COLOR_WHITE));
            }
            {
                securityWeight = new Text(
                        group1,
                    SWT.BORDER);
                securityWeight.setText("0");
                securityWeight.setBounds(246, 18, 35, 21);
                securityWeight.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent evt) {
                        securityWeightFocusLost(evt);
                    }
                });
            }
            {
                label3 = new Label(group1, SWT.NONE);
                label3.setText("/");
                label3.setBounds(288, 21, 7, 21);
                label3.setBackground(Display.getCurrent().getSystemColor(
                    SWT.COLOR_WHITE));
            }
            {
                securityObjective = new Text(
                        group1,
                    SWT.BORDER);
                securityObjective.setText("0");
                securityObjective.setBounds(295, 18, 35, 21);
                securityObjective.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent evt) {
                        securityObjectiveFocusLost(evt);
                    }
                });
            }
            {
                environmentLabel = new Label(
                    group1,
                    SWT.NONE);
                environmentLabel.setText("Environment");
                environmentLabel.setBounds(177, 49, 63, 21);
                environmentLabel.setBackground(Display.getCurrent()
                    .getSystemColor(SWT.COLOR_WHITE));
            }
            {
                environmentWeight = new Text(
                        group1,
                    SWT.BORDER);
                environmentWeight.setText("0");
                environmentWeight.setBounds(246, 46, 35, 21);
                environmentWeight.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent evt) {
                        environmentWeightFocusLost(evt);
                    }
                });
            }
            {
                label4 = new Label(group1, SWT.NONE);
                label4.setText("/");
                label4.setBounds(288, 49, 7, 21);
                label4.setBackground(Display.getCurrent().getSystemColor(
                    SWT.COLOR_WHITE));
            }
            {
                environmentObjective = new Text(
                        group1,
                    SWT.BORDER);
                environmentObjective.setText("0");
                environmentObjective.setBounds(295, 46, 35, 21);
                environmentObjective.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent evt) {
                        environmentObjectiveFocusLost(evt);
                    }
                });
            }
            {
                businessLabel = new Label(group1, SWT.NONE);
                businessLabel.setText("Business");
                businessLabel.setBounds(177, 77, 63, 21);
                businessLabel.setBackground(Display.getCurrent()
                    .getSystemColor(SWT.COLOR_WHITE));
            }
            {
                businessWeight = new Text(
                        group1,
                    SWT.BORDER);
                businessWeight.setText("0");
                businessWeight.setBounds(246, 74, 35, 21);
                businessWeight.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent evt) {
                        businessWeightFocusLost(evt);
                    }
                });
            }
        }
        {
            proxiesGroup = new Group(shell, SWT.NONE);
            proxiesGroup.setLayout(null);
            proxiesGroup.setText("Proxies");
            proxiesGroup.setBounds(7, 203, 483, 133);
            proxiesGroup.setBackground(Display.getCurrent().getSystemColor(
                SWT.COLOR_WHITE));
            {
                proxiesPathLabel = new Label(proxiesGroup, SWT.NONE);
                proxiesPathLabel.setText("Proxies path");
                proxiesPathLabel.setBounds(21, 24, 63, 21);
                proxiesPathLabel.setBackground(Display.getCurrent()
                    .getSystemColor(SWT.COLOR_WHITE));
            }
            {
                proxiesPath = new Text(proxiesGroup, SWT.BORDER);
                proxiesPath.setBounds(91, 21, 343, 21);
                proxiesPath.setText("proxies.txt");
                proxiesPath.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent evt) {
                        proxiesPathFocusLost(evt);
                    }
                });
            }
            {
                openProxiesPathButton = new Button(proxiesGroup, SWT.PUSH
                    | SWT.FLAT
                    | SWT.CENTER);
                openProxiesPathButton.setImage(SWTResourceManager
                    .getImage("images/folder_open.gif"));
                openProxiesPathButton.setBounds(441, 21, 28, 21);
                openProxiesPathButton
                    .addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            openProxiesPathButtonWidgetSelected(evt);
                        }
                    });
            }
            {
                saveGoodProxiesButton = new Button(proxiesGroup, SWT.CHECK
                    | SWT.LEFT);
                saveGoodProxiesButton.setText("Save good proxies");
                saveGoodProxiesButton.setBounds(21, 49, 119, 21);
                saveGoodProxiesButton.setSelection(true);
                saveGoodProxiesButton
                    .addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            saveGoodProxiesButtonWidgetSelected(evt);
                        }
                    });
                saveGoodProxiesButton.setBackground(Display.getCurrent()
                    .getSystemColor(SWT.COLOR_WHITE));
            }
            {
                goodProxiesPathLabel = new Label(proxiesGroup, SWT.NONE);
                goodProxiesPathLabel.setText("path");
                goodProxiesPathLabel.setBounds(147, 52, 28, 21);
                goodProxiesPathLabel.setBackground(Display.getCurrent()
                    .getSystemColor(SWT.COLOR_WHITE));
            }
            {
                goodProxiesPath = new Text(proxiesGroup, SWT.BORDER);
                goodProxiesPath.setBounds(175, 49, 259, 21);
                goodProxiesPath.setText("good.txt");
                goodProxiesPath.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent evt) {
                        goodProxiesPathFocusLost(evt);
                    }
                });
            }
            {
                openGoodProxiesPathButton = new Button(proxiesGroup, SWT.PUSH
                    | SWT.FLAT
                    | SWT.CENTER);
                openGoodProxiesPathButton.setImage(SWTResourceManager
                    .getImage("images/folder_open.gif"));
                openGoodProxiesPathButton.setBounds(441, 49, 28, 21);
                openGoodProxiesPathButton
                    .addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            openGoodProxiesPathButtonWidgetSelected(evt);
                        }
                    });
            }
            {
                saveBadProxiesButton = new Button(proxiesGroup, SWT.CHECK
                    | SWT.LEFT);
                saveBadProxiesButton.setText("Save bad proxies");
                saveBadProxiesButton.setBounds(21, 77, 119, 21);
                saveBadProxiesButton
                    .addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            saveBadProxiesButtonWidgetSelected(evt);
                        }
                    });
                saveBadProxiesButton.setBackground(Display.getCurrent()
                    .getSystemColor(SWT.COLOR_WHITE));
            }
            {
                badProxiesPathLabel = new Label(proxiesGroup, SWT.NONE);
                badProxiesPathLabel.setText("path");
                badProxiesPathLabel.setBounds(147, 80, 28, 21);
                badProxiesPathLabel.setEnabled(false);
                badProxiesPathLabel.setBackground(Display.getCurrent()
                    .getSystemColor(SWT.COLOR_WHITE));
            }
            {
                badProxiesPath = new Text(proxiesGroup, SWT.BORDER);
                badProxiesPath.setBounds(175, 77, 259, 21);
                badProxiesPath.setEnabled(false);
                badProxiesPath.setText("bad.txt");
                badProxiesPath.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent evt) {
                        badProxiesPathFocusLost(evt);
                    }
                });
            }
            {
                openBadProxiesPathButton = new Button(proxiesGroup, SWT.PUSH
                    | SWT.FLAT
                    | SWT.CENTER);
                openBadProxiesPathButton.setImage(SWTResourceManager
                    .getImage("images/folder_open.gif"));
                openBadProxiesPathButton.setBounds(441, 77, 28, 21);
                openBadProxiesPathButton
                    .addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            openBadProxiesPathButtonWidgetSelected(evt);
                        }
                    });
                openBadProxiesPathButton.setEnabled(false);
            }
            {
                autoCleanupProxiesButton = new Button(proxiesGroup, SWT.CHECK
                    | SWT.LEFT);
                autoCleanupProxiesButton.setText("Auto cleanup proxies file");
                autoCleanupProxiesButton.setBounds(21, 105, 147, 21);
                autoCleanupProxiesButton.setSelection(true);
                autoCleanupProxiesButton
                    .addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            autoCleanupProxiesButtonWidgetSelected(evt);
                        }
                    });
                autoCleanupProxiesButton.setBackground(Display.getCurrent()
                    .getSystemColor(SWT.COLOR_WHITE));
            }
            {
                proxiesTypesCombo = new Combo(proxiesGroup, SWT.READ_ONLY);
                proxiesTypesCombo.setBounds(314, 105, 120, 21);
                proxiesTypesCombo.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent evt) {
                        proxiesTypesComboWidgetSelected(evt);
                    }
                });
            }
            {
                proxiesTypesLabel = new Label(proxiesGroup, SWT.NONE);
                proxiesTypesLabel.setText("Proxies types");
                proxiesTypesLabel.setBounds(244, 108, 70, 16);
                proxiesTypesLabel.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
            }
        }
        {
            advancedGroup = new Group(shell, SWT.NONE);
            advancedGroup.setLayout(null);
            advancedGroup.setText("Advanced");
            advancedGroup.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
            advancedGroup.setBounds(7, 342, 483, 123);
            {
                maxThreadInactivityLabel = new Label(advancedGroup, SWT.NONE);
                maxThreadInactivityLabel.setText("Max thread inactivity");
                maxThreadInactivityLabel.setBounds(21, 17, 105, 21);
                maxThreadInactivityLabel.setBackground(Display.getCurrent()
                    .getSystemColor(SWT.COLOR_WHITE));
            }
            {
                maxThreadInactivity = new Text(advancedGroup, SWT.BORDER);
                maxThreadInactivity.setText("15000");
                maxThreadInactivity.setBounds(147, 14, 49, 21);
                maxThreadInactivity.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent evt) {
                        maxThreadInactivityFocusLost(evt);
                    }
                });
            }
            {
                maxThreadInactivitySLabel = new Label(advancedGroup, SWT.NONE);
                maxThreadInactivitySLabel.setText("s");
                maxThreadInactivitySLabel.setBounds(203, 17, 21, 21);
                maxThreadInactivitySLabel.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
            }
            {
                proxiesHitOrderLabel = new Label(advancedGroup, SWT.NONE);
                proxiesHitOrderLabel.setText("Proxies hit order");
                proxiesHitOrderLabel.setBounds(273, 17, 84, 21);
                proxiesHitOrderLabel.setBackground(Display.getCurrent()
                    .getSystemColor(SWT.COLOR_WHITE));
            }
            {
                proxiesHitOrderCombo = new Combo(advancedGroup, SWT.READ_ONLY);
                proxiesHitOrderCombo.setText("File");
                proxiesHitOrderCombo.setBounds(357, 14, 98, 21);
                proxiesHitOrderCombo
                    .addSelectionListener(new SelectionAdapter() {
                        public void widgetSelected(SelectionEvent evt) {
                            proxiesHitOrderComboWidgetSelected(evt);
                        }
                    });
            }
            {
                httpConnectionTimeoutLabel = new Label(advancedGroup, SWT.NONE);
                httpConnectionTimeoutLabel.setText("HTTP connection timeout");
                httpConnectionTimeoutLabel.setBounds(21, 45, 126, 21);
                httpConnectionTimeoutLabel.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
            }
            {
                httpConnectionTimeout = new Text(advancedGroup, SWT.BORDER);
                httpConnectionTimeout.setText("10000");
                httpConnectionTimeout.setBounds(147, 42, 49, 21);
                httpConnectionTimeout.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent evt) {
                        httpConnectionTimeoutFocusLost(evt);
                    }
                });
            }
            {
                httpConnectionTimeoutSLabel = new Label(advancedGroup, SWT.NONE);
                httpConnectionTimeoutSLabel.setText("s");
                httpConnectionTimeoutSLabel.setBounds(203, 45, 21, 21);
                httpConnectionTimeoutSLabel.setBackground(Display.getCurrent()
                    .getSystemColor(SWT.COLOR_WHITE));
            }
            {
                debugLevelLabel = new Label(advancedGroup, SWT.NONE);
                debugLevelLabel.setText("Debug level");
                debugLevelLabel.setBounds(273, 45, 84, 21);
                debugLevelLabel.setBackground(Display.getCurrent()
                    .getSystemColor(SWT.COLOR_WHITE));
            }
            {
                debugLevelCombo = new Combo(advancedGroup, SWT.READ_ONLY);
                debugLevelCombo.setText("Off");
                debugLevelCombo.setBounds(357, 42, 98, 21);
                debugLevelCombo.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent evt) {
                        debugLevelComboWidgetSelected(evt);
                    }
                });
            }
            {
                maxThreadCountLabel = new Label(advancedGroup, SWT.NONE);
                maxThreadCountLabel.setText("Max thread count");
                maxThreadCountLabel.setBounds(21, 73, 119, 21);
                maxThreadCountLabel.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
            }
            {
                maxThreadCount = new Text(advancedGroup, SWT.BORDER);
                maxThreadCount.setText("15");
                maxThreadCount.setBounds(147, 70, 28, 21);
                maxThreadCount.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent evt) {
                        maxThreadCountFocusLost(evt);
                    }
                });
            }
            {
                emitBeepButton = new Button(advancedGroup, SWT.CHECK | SWT.LEFT);
                emitBeepButton.setText("Emit beep when good proxy is found");
                emitBeepButton.setBounds(273, 70, 203, 21);
                emitBeepButton.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent evt) {
                        emitBeepButtonWidgetSelected(evt);
                    }
                });
                emitBeepButton.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
                emitBeepButton.setToolTipText("Every time a good proxy is found, a beep will be emitted\nthrough speaker.");
            }
            {
                resolveNameServersButton = new Button(advancedGroup, SWT.CHECK | SWT.LEFT);
                resolveNameServersButton.setText("Resolve name servers");
                resolveNameServersButton.setBounds(20, 99, 202, 16);
                resolveNameServersButton.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
                resolveNameServersButton.setToolTipText("If this option is enabled, proxies full names (ex: proxy1.proxies.com) will be resolved into IP addresses.\nOnly IP address will be saved into good and bad proxies files.");
                resolveNameServersButton.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent evt) {
                        resolveNameServersButtonWidgetSelected(evt);
                    }
                });
            }
            {
                excludeGoogProxiesButton = new Button(advancedGroup, SWT.CHECK | SWT.LEFT);
                excludeGoogProxiesButton.setText("Exclude good proxies when iterating");
                excludeGoogProxiesButton.setBounds(273, 99, 196, 16);
                excludeGoogProxiesButton.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
                excludeGoogProxiesButton.setToolTipText("If this option is enabled, good proxies will not be taken into\naccount when iterating. This option is efficient when used in\nconjunction of special tags with good proxies filename.\nFor example, if you want the whole proxies list to be checked\nonce a day, you could ask the program to save the good proxies\nin a file containing the special tag <ts> (day timestamp), and\nonly proxies that have not been considered as good ones during\nthe present day will be checked.\n");
                excludeGoogProxiesButton.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent evt) {
                        excludeGoogProxiesButtonWidgetSelected(evt);
                    }
                });
            }
        }
        {
            configTypeLabel = new Label(shell, SWT.NONE);
            configTypeLabel.setText("Config type");
            configTypeLabel.setBounds(14, 10, 63, 21);
            configTypeLabel.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
        }
        {
            iterationsLabel = new Label(shell, SWT.NONE);
            iterationsLabel.setText("Iterations");
            iterationsLabel.setBounds(329, 10, 49, 21);
            iterationsLabel.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
        }

    }
    
  //$hide>>$
    protected final void initializeContents() {
        for (ConfigType configType: ConfigType.values()) {
            configCombo.add("" + configType);
        }
        
        for (HitOrder hitOrder: HitOrder.values()) {
            proxiesHitOrderCombo.add("" + hitOrder);
        }
        
        for (HitMode hitMode: HitMode.values()) {
            hitModeCombo.add("" + hitMode);
        }
        
        for (DebugLevel debugLevel: DebugLevel.values()) {
            debugLevelCombo.add("" + debugLevel);
        }
        
        for (ProxyType proxyType: ProxyType.values()) {
            proxiesTypesCombo.add(getConfig().getProxyTypeLiteral(proxyType));
        }
    }

    protected final void applicationClose() {
        MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO | SWT.CANCEL);
        messageBox.setText("Confirmation");
        messageBox.setMessage("Do you want to save current configuration to file " + getConfig().getPropertiesFile() + " before exiting URLBooster?");

        int returnValue = messageBox.open();
        switch(returnValue) {
        case SWT.YES:
            getConfig().saveConfigToFile(getConfig().getPropertiesFile());
            System.exit(0);
            break;
        case SWT.NO:
            System.exit(0);
            break;
        case SWT.CANCEL:
            break;
        }
    }
    
    private final void updateFormWithConfig() {
        
        configCombo.setText("" + getConfig().getConfigType());
        if (getConfig().getConfigType() == ConfigType.Generic) {
            minivilleConfigurationGroup.setVisible(false);
            genericConfigurationGroup.setVisible(true);
            
            genericURL.setText(getConfig().getGenericURL());
            genericURLValidator.setText(getConfig().getGenericURLValidator());
            genericURLValidatorRegex.setSelection(getConfig().isGenericURLValidatorRegex());
        } else if (getConfig().getConfigType() == ConfigType.Miniville ||
                   getConfig().getConfigType() == ConfigType.Antiville ||
                   getConfig().getConfigType() == ConfigType.MyMinicity) {
            minivilleConfigurationGroup.setVisible(true);
            genericConfigurationGroup.setVisible(false);

            cityText.setText(getConfig().getMinivilleConfiguration().getMinivilleName());
            useValidatorButton.setSelection(getConfig().getMinivilleConfiguration().isUseMinivilleValidator());
            
            if (getConfig().getConfigType() == ConfigType.Antiville) {
                hitModeCombo.setEnabled(false);
                hitModeLabel.setEnabled(false);
                statisticsRefreshIntervalLabel.setEnabled(false);
                statsRefreshInterval.setEnabled(false);
                statsRefreshIntervalSLabel.setEnabled(false);
                citizenLabel.setEnabled(false);
                citizenWeight.setEnabled(false);
                transportLabel.setEnabled(false);
                transportObjective.setEnabled(false);
                transportWeight.setEnabled(false);
                industryLabel.setEnabled(false);
                industryObjective.setEnabled(false);
                industryWeight.setEnabled(false);
                securityLabel.setEnabled(false);
                securityWeight.setEnabled(false);
                securityObjective.setEnabled(false);
                environmentLabel.setEnabled(false);
                environmentWeight.setEnabled(false);
                environmentObjective.setEnabled(false);
                businessLabel.setEnabled(false);
                businessWeight.setEnabled(false);
            } else {
                hitModeCombo.setEnabled(true);
                hitModeLabel.setEnabled(true);
                statisticsRefreshIntervalLabel.setEnabled(true);
                statsRefreshInterval.setEnabled(true);
                statsRefreshIntervalSLabel.setEnabled(true);
                citizenLabel.setEnabled(true);
                citizenWeight.setEnabled(true);
                transportLabel.setEnabled(true);
                transportWeight.setEnabled(true);
                industryLabel.setEnabled(true);
                industryWeight.setEnabled(true);
                securityLabel.setEnabled(true);
                securityWeight.setEnabled(true);
                environmentLabel.setEnabled(true);
                environmentWeight.setEnabled(true);
                businessLabel.setEnabled(true);
                businessWeight.setEnabled(true);
                
                // On désactive tout...
                statsRefreshInterval.setEnabled(false);
                citizenWeight.setEnabled(false);
                transportWeight.setEnabled(false);
                industryWeight.setEnabled(false);
                securityWeight.setEnabled(false);
                environmentWeight.setEnabled(false);
                businessWeight.setEnabled(false);
                transportObjective.setEnabled(false);
                industryObjective.setEnabled(false);
                securityObjective.setEnabled(false);
                environmentObjective.setEnabled(false);
                
                // ... pour ne réactiver que le nécessaire
                switch (getConfig().getMinivilleConfiguration().getHitMode()) {
                case Intelligent:
                    statsRefreshInterval.setEnabled(true);
                    citizenWeight.setEnabled(true);
                    transportWeight.setEnabled(true);
                    industryWeight.setEnabled(true);
                    securityWeight.setEnabled(true);
                    environmentWeight.setEnabled(true);
                    businessWeight.setEnabled(true);
                    transportObjective.setEnabled(true);
                    industryObjective.setEnabled(true);
                    securityObjective.setEnabled(true);
                    environmentObjective.setEnabled(true);
                    break;
                case PseudoRandom:
                    citizenWeight.setEnabled(true);
                    transportWeight.setEnabled(true);
                    industryWeight.setEnabled(true);
                    securityWeight.setEnabled(true);
                    environmentWeight.setEnabled(true);
                    businessWeight.setEnabled(true);
                    break;
                case Random:
                    break;
                }
            }
            
            hitModeCombo.setText("" + getConfig().getMinivilleConfiguration().getHitMode());
            statsRefreshInterval.setText("" + getConfig().getMinivilleConfiguration().getRefreshStatisticsInterval());
            if (getConfig().getMinivilleConfiguration().getRefreshStatisticsInterval() < 60)
                statsRefreshInterval.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
            else
                statsRefreshInterval.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));
            citizenWeight.setText("" + getConfig().getMinivilleConfiguration().getWeightCitizen());
            transportWeight.setText("" + getConfig().getMinivilleConfiguration().getWeightTransport());
            industryWeight.setText("" + getConfig().getMinivilleConfiguration().getWeightIndustry());
            securityWeight.setText("" + getConfig().getMinivilleConfiguration().getWeightSecurity());
            environmentWeight.setText("" + getConfig().getMinivilleConfiguration().getWeightEnvironment());
            businessWeight.setText("" + getConfig().getMinivilleConfiguration().getWeightBusiness());
            transportObjective.setText("" + getConfig().getMinivilleConfiguration().getObjectiveTransport());
            industryObjective.setText("" + getConfig().getMinivilleConfiguration().getObjectiveIndustry());
            securityObjective.setText("" + getConfig().getMinivilleConfiguration().getObjectiveSecurity());
            environmentObjective.setText("" + getConfig().getMinivilleConfiguration().getObjectiveEnvironment());            
        }
        
        iterations.setText("" + getConfig().getIterations());
        
        proxiesPath.setText(getConfig().getProxiesFile());
        
        saveGoodProxiesButton.setSelection(getConfig().isSaveGoodProxies());
        goodProxiesPath.setText(getConfig().getGoodProxiesFile());
        if (getConfig().isSaveGoodProxies()) {
            goodProxiesPathLabel.setEnabled(true);
            goodProxiesPath.setEnabled(true);
            openGoodProxiesPathButton.setEnabled(true);
        } else {
            goodProxiesPathLabel.setEnabled(false);
            goodProxiesPath.setEnabled(false);
            openGoodProxiesPathButton.setEnabled(false);
        }

        saveBadProxiesButton.setSelection(getConfig().isSaveBadProxies());
        badProxiesPath.setText(getConfig().getBadProxiesFile());
        if (getConfig().isSaveBadProxies()) {
            badProxiesPathLabel.setEnabled(true);
            badProxiesPath.setEnabled(true);
            openBadProxiesPathButton.setEnabled(true);
        } else {
            badProxiesPathLabel.setEnabled(false);
            badProxiesPath.setEnabled(false);
            openBadProxiesPathButton.setEnabled(false);
        }
        
        maxThreadInactivity.setText("" + getConfig().getMaxThreadsInactivity() / 1000);
        httpConnectionTimeout.setText("" + getConfig().getHttpConnectionTimeout() / 1000);
        maxThreadCount.setText("" + getConfig().getMaxThreadCount());
        proxiesHitOrderCombo.setText("" + getConfig().getProxyHitOrder());
        
        proxiesTypesCombo.setText(getConfig().getProxyTypeLiteral(getConfig().getProxyType()));
        debugLevelCombo.setText("" + getConfig().getDebugLevel());
        
        emitBeepButton.setSelection(getConfig().isEmitBeep());
        resolveNameServersButton.setSelection(getConfig().isResolveNameServers());
        excludeGoogProxiesButton.setSelection(getConfig().isExludeGoodProxies());
    }
    
    private void go() {
        
        ExecutionBoard executionBoard = new ExecutionBoard(getConfig());
        executionBoard.open();
    }

    public void exitProgram(String reason) {
        logInfo("program has exited");
        if (!reason.equals(""))
            logInfo("reason: " + reason);
        else
            logInfo("reason: unknown reason");

        System.exit(-1);
    }
    
    public Text getMinivilleText() {
        return cityText;
    }
    
    public Combo getConfigCombo() {
        return configCombo;
    }
    
  //$hide<<$
    
    protected void saveGoodProxiesButtonWidgetSelected(SelectionEvent evt) {
        LogManager.logFiner("saveGoodProxiesButton.widgetSelected, event=" + evt);

        getConfig().setSaveGoodProxies(saveGoodProxiesButton.getSelection());
        updateFormWithConfig();
    }
    
    protected void goButtonWidgetSelected(SelectionEvent evt) {
        LogManager.logFiner("goButton.widgetSelected, event=" + evt);
        
        // TODO: vérifications avant lancement
        
        go();
    }
    
    protected void configComboWidgetSelected(SelectionEvent evt) {
        LogManager.logFiner("configCombo.widgetSelected, event=" + evt);
        
        for (ConfigType configType: ConfigType.values())
            if (configCombo.getText().equals("" + configType)) {
                getConfig().setConfigType(configType);
                break;
            }
        updateFormWithConfig();
    }
    
    protected void saveBadProxiesButtonWidgetSelected(SelectionEvent evt) {
        LogManager.logFiner("saveBadProxiesButton.widgetSelected, event=" + evt);

        getConfig().setSaveBadProxies(saveBadProxiesButton.getSelection());
        updateFormWithConfig();
    }
    
    protected void hitModeComboWidgetSelected(SelectionEvent evt) {
        LogManager.logFiner("hitModeCombo.widgetSelected, event=" + evt);
        
        for (HitMode hitMode: HitMode.values())
            if (hitModeCombo.getText().equals("" + hitMode)) {
                getConfig().getMinivilleConfiguration().setHitMode(hitMode);
                break;
            }
        updateFormWithConfig();
    }

    protected void genericURLFocusLost(FocusEvent evt) {
        LogManager.logFiner("genericURL.focusLost, event=" + evt);
        getConfig().setGenericURL(genericURL.getText());
        updateFormWithConfig();
    }
    
    protected void urlValidatorTextFocusLost(FocusEvent evt) {
        LogManager.logFiner("urlValidatorText.focusLost, event=" + evt);
        
        getConfig().setGenericURLValidator(genericURLValidator.getText());
        updateFormWithConfig();
    }
    
    protected void minivilleTextFocusLost(FocusEvent evt) {
        LogManager.logFiner("minivilleText.focusLost, event=" + evt);
        getConfig().getMinivilleConfiguration().setMinivilleName(cityText.getText());
        updateFormWithConfig();
    }
    
    protected void statsRefreshIntervalFocusLost(FocusEvent evt) {
        LogManager.logFiner("statsRefreshInterval.focusLost, event=" + evt);
        try {
            int userEntry = new Integer(statsRefreshInterval.getText()).intValue();
            if (userEntry > 0)
                getConfig().getMinivilleConfiguration().setRefreshStatisticsInterval(userEntry);
        }
        catch (NumberFormatException e) {
            LogManager.traceException(e);
        }
        updateFormWithConfig();
    }
    
    protected void citizenWeightFocusLost(FocusEvent evt) {
        LogManager.logFiner("citizenWeight.focusLost, event=" + evt);
        try {
            int userEntry = new Integer(citizenWeight.getText()).intValue();
            if (userEntry >= 0)
                getConfig().getMinivilleConfiguration().setWeightCitizen(userEntry);
        }
        catch (NumberFormatException e) {
            LogManager.traceException(e);
        }
        updateFormWithConfig();
    }
    
    protected void transportWeightFocusLost(FocusEvent evt) {
        LogManager.logFiner("transportWeight.focusLost, event=" + evt);
        try {
            int userEntry = new Integer(transportWeight.getText()).intValue();
            if (userEntry >= 0)
                getConfig().getMinivilleConfiguration().setWeightTransport(userEntry);
        }
        catch (NumberFormatException e) {
            LogManager.traceException(e);
        }
        updateFormWithConfig();
    }
    
    protected void transportObjectiveFocusLost(FocusEvent evt) {
        LogManager.logFiner("transportObjective.focusLost, event=" + evt);
        try {
            int userEntry = new Integer(transportObjective.getText()).intValue();
            if (userEntry >= 0)
                getConfig().getMinivilleConfiguration().setObjectiveTransport(userEntry);
        }
        catch (NumberFormatException e) {
            LogManager.traceException(e);
        }
        updateFormWithConfig();
    }
    
    protected void industryWeightFocusLost(FocusEvent evt) {
        LogManager.logFiner("industryWeight.focusLost, event=" + evt);
        try {
            int userEntry = new Integer(industryWeight.getText()).intValue();
            if (userEntry >= 0)
                getConfig().getMinivilleConfiguration().setWeightIndustry(userEntry);
        }
        catch (NumberFormatException e) {
            LogManager.traceException(e);
        }
        updateFormWithConfig();
    }
    
    protected void industryObjectiveFocusLost(FocusEvent evt) {
        LogManager.logFiner("industryObjective.focusLost, event=" + evt);
        try {
            int userEntry = new Integer(industryObjective.getText()).intValue();
            if (userEntry >= 0)
                getConfig().getMinivilleConfiguration().setObjectiveIndustry(userEntry);
        }
        catch (NumberFormatException e) {
            LogManager.traceException(e);
        }
        updateFormWithConfig();
    }
    
    protected void securityWeightFocusLost(FocusEvent evt) {
        LogManager.logFiner("securityWeight.focusLost, event=" + evt);
        try {
            int userEntry = new Integer(securityWeight.getText()).intValue();
            if (userEntry >= 0)
                getConfig().getMinivilleConfiguration().setWeightSecurity(userEntry);
        }
        catch (NumberFormatException e) {
            LogManager.traceException(e);
        }
        updateFormWithConfig();
    }
    
    protected void securityObjectiveFocusLost(FocusEvent evt) {
        LogManager.logFiner("securityObjective.focusLost, event=" + evt);
        try {
            int userEntry = new Integer(securityObjective.getText()).intValue();
            if (userEntry >= 0)
                getConfig().getMinivilleConfiguration().setObjectiveSecurity(userEntry);
        }
        catch (NumberFormatException e) {
            LogManager.traceException(e);
        }
        updateFormWithConfig();
    }
    
    protected void environmentWeightFocusLost(FocusEvent evt) {
        LogManager.logFiner("environmentWeight.focusLost, event=" + evt);
        try {
            int userEntry = new Integer(environmentWeight.getText()).intValue();
            if (userEntry >= 0)
                getConfig().getMinivilleConfiguration().setWeightEnvironment(userEntry);
        }
        catch (NumberFormatException e) {
            LogManager.traceException(e);
        }
        updateFormWithConfig();
    }
    
    protected void environmentObjectiveFocusLost(FocusEvent evt) {
        LogManager.logFiner("environmentObjective.focusLost, event=" + evt);
        try {
            int userEntry = new Integer(environmentObjective.getText()).intValue();
            if (userEntry >= 0)
                getConfig().getMinivilleConfiguration().setObjectiveEnvironment(userEntry);
        }
        catch (NumberFormatException e) {
            LogManager.traceException(e);
        }
        updateFormWithConfig();
    }
    
    protected void businessWeightFocusLost(FocusEvent evt) {
        LogManager.logFiner("businessWeight.focusLost, event=" + evt);
        try {
            int userEntry = new Integer(businessWeight.getText()).intValue();
            if (userEntry >= 0)
                getConfig().getMinivilleConfiguration().setWeightBusiness(userEntry);
        }
        catch (NumberFormatException e) {
            LogManager.traceException(e);
        }
        updateFormWithConfig();
    }
    
    protected void proxiesPathFocusLost(FocusEvent evt) {
        LogManager.logFiner("proxiesPath.focusLost, event=" + evt);
        getConfig().setProxiesFile(proxiesPath.getText());
        updateFormWithConfig();
    }
    
    protected void goodProxiesPathFocusLost(FocusEvent evt) {
        LogManager.logFiner("goodProxiesPath.focusLost, event=" + evt);
        getConfig().setGoodProxiesFile(goodProxiesPath.getText());
        updateFormWithConfig();
    }
    
    protected void badProxiesPathFocusLost(FocusEvent evt) {
        LogManager.logFiner("badProxiesPath.focusLost, event=" + evt);
        getConfig().setBadProxiesFile(badProxiesPath.getText());
        updateFormWithConfig();
    }
    
    protected void openProxiesPathButtonWidgetSelected(SelectionEvent evt) {
        LogManager.logFiner("openProxiesPathButton.widgetSelected, event=" + evt);
        
        //$hide>>$
        FileDialog fd = new FileDialog(shell);
        fd.setFilterExtensions(new String[] {"*.txt", "*.*"});
        fd.open();
        String filename = fd.getFileName();

        if (!filename.equals("")) {
            getConfig().setProxiesFile(fd.getFilterPath() + "\\" + filename);
        }
        updateFormWithConfig();
        //$hide<<$
    }
    
    protected void openGoodProxiesPathButtonWidgetSelected(SelectionEvent evt) {
        LogManager.logFiner("openGoodProxiesPathButton.widgetSelected, event=" + evt);
        
        //$hide>>$
        FileDialog fd = new FileDialog(shell);
        fd.setFilterExtensions(new String[] {"*.txt", "*.*"});
        fd.open();
        String filename = fd.getFileName();

        if (!filename.equals("")) {
            getConfig().setGoodProxiesFile(fd.getFilterPath() + "\\" + filename);
        }
        updateFormWithConfig();
        //$hide<<$
    }
    
    protected void openBadProxiesPathButtonWidgetSelected(SelectionEvent evt) {
        LogManager.logFiner("openBadProxiesPathButton.widgetSelected, event=" + evt);
        
        //$hide>>$
        FileDialog fd = new FileDialog(shell);
        fd.setFilterExtensions(new String[] {"*.txt", "*.*"});
        fd.open();
        String filename = fd.getFileName();

        if (!filename.equals("")) {
            getConfig().setBadProxiesFile(fd.getFilterPath() + "\\" + filename);
        }
        updateFormWithConfig();
        //$hide<<$
    }
    
    protected void proxiesHitOrderComboWidgetSelected(SelectionEvent evt) {
        LogManager.logFiner("proxiesHitOrderCombo.widgetSelected, event=" + evt);
        for (HitOrder hitOrder: HitOrder.values())
            if (proxiesHitOrderCombo.getText().equals("" + hitOrder)) {
                getConfig().setProxyHitOrder(hitOrder);
                break;
            }
        updateFormWithConfig();
    }
    
    protected void debugLevelComboWidgetSelected(SelectionEvent evt) {
        LogManager.logFiner("debugLevelCombo.widgetSelected, event=" + evt);
        for (DebugLevel debugLevel: DebugLevel.values())
            if (debugLevelCombo.getText().equals("" + debugLevel)) {
                getConfig().setDebugLevel(debugLevel);
                break;
            }
        updateFormWithConfig();
    }
    
    protected void emitBeepButtonWidgetSelected(SelectionEvent evt) {
        LogManager.logFiner("emitBeepButton.widgetSelected, event=" + evt);
        getConfig().setEmitBeep(emitBeepButton.getSelection());
        updateFormWithConfig();
    }
    
    protected void maxThreadInactivityFocusLost(FocusEvent evt) {
        LogManager.logFiner("maxThreadInactivity.focusLost, event=" + evt);
        try {
            int userEntry = new Integer(maxThreadInactivity.getText()).intValue() * 1000;
            if (userEntry > 0)
                getConfig().setMaxThreadsInactivity(userEntry);
        }
        catch (NumberFormatException e) {
            LogManager.traceException(e);
        }
        updateFormWithConfig();
    }
    
    protected void httpConnectionTimeoutFocusLost(FocusEvent evt) {
        LogManager.logFiner("httpConnectionTimeout.focusLost, event=" + evt);
        try {
            int userEntry = new Integer(httpConnectionTimeout.getText()).intValue() * 1000;
            if (userEntry > 0)
                getConfig().setHttpConnectionTimeout(new Integer(httpConnectionTimeout.getText()).intValue() * 1000);
        }
        catch (NumberFormatException e) {
            LogManager.traceException(e);
        }
        updateFormWithConfig();
    }
    
    protected void maxThreadCountFocusLost(FocusEvent evt) {
        LogManager.logFiner("maxThreadCount.focusLost, event=" + evt);
        try {
            int userEntry = new Integer(maxThreadCount.getText()).intValue();
            if (userEntry > 0)
                getConfig().setMaxThreadCount(new Integer(maxThreadCount.getText()).intValue());
        }
        catch (NumberFormatException e) {
            LogManager.traceException(e);
        }
        updateFormWithConfig();
    }
    
    protected void iterationsFocusLost(FocusEvent evt) {
        LogManager.logFiner("iterations.focusLost, event=" + evt);
        try {
            getConfig().setIterations(new Integer(iterations.getText()).intValue());
        }
        catch (NumberFormatException e) {
            LogManager.traceException(e);
        }
        updateFormWithConfig();
    }
    
    protected void openConfigMenuItemWidgetSelected(SelectionEvent evt) {
        LogManager.logFiner("openConfigMenuItem.widgetSelected, event=" + evt);

        //$hide>>$
        FileDialog fd = new FileDialog(shell);
        fd.setFilterExtensions(new String[] {"*.xml", "*.ini", "*.*"});
        fd.setText("Open configuration");
        fd.open();
        String filename = fd.getFileName();

        if (!filename.equals("")) {
            getConfig().loadConfigFromFile(fd.getFilterPath() + "\\" + filename);
        }
        
        updateFormWithConfig();
        //$hide<<$
    }
    
    protected void saveConfigMenuItemWidgetSelected(SelectionEvent evt) {
        LogManager.logFiner("saveConfigMenuItem.widgetSelected, event=" + evt);

        //$hide>>$
        FileDialog fd = new FileDialog(shell);
        fd.setFilterExtensions(new String[] {"*.xml"});
        fd.setText("Save configuration");
        fd.open();
        String filename = fd.getFileName();

        if (!filename.equals("")) {
            if (!(filename.indexOf(".xml") > 0))
                filename = filename + ".xml";
            getConfig().saveConfigToFile(fd.getFilterPath() + "\\" + filename);
        }
        
        updateFormWithConfig();
        //$hide<<$
    }
    
    protected void autoCleanupProxiesButtonWidgetSelected(SelectionEvent evt) {
        LogManager.logFiner("autoCleanupProxiesButton.widgetSelected, event=" + evt);
        
        getConfig().setCleanProxiesFile(autoCleanupProxiesButton.getSelection());
        updateFormWithConfig();
    }
    
    protected void aboutMenuItemWidgetSelected(SelectionEvent evt) {
        LogManager.logFiner("aboutMenuItem.widgetSelected, event=" + evt);
        //$hide>>$
        if (aboutWindow == null || aboutWindow.getShell().isDisposed()) {
            aboutWindow = new AboutBox(display);
            aboutWindow.open();
        }
        //$hide<<$
    }
    
    protected void genericURLValidatorRegexWidgetSelected(SelectionEvent evt) {
        LogManager.logFiner("genericURLValidatorRegex.widgetSelected, event="+evt);

        getConfig().setGenericURLValidatorRegex(genericURLValidatorRegex.getSelection());
        updateFormWithConfig();
    }
    
    protected void excludeGoogProxiesButtonWidgetSelected(SelectionEvent evt) {
        LogManager.logFiner("excludeGoogProxiesButton.widgetSelected, event="+evt);

        getConfig().setExludeGoodProxies(excludeGoogProxiesButton.getSelection());
        updateFormWithConfig();
    }
    
    protected void resolveNameServersButtonWidgetSelected(SelectionEvent evt) {
        LogManager.logFiner("resolveNameServersButton.widgetSelected, event="+evt);
        
        getConfig().setResolveNameServers(resolveNameServersButton.getSelection());
        updateFormWithConfig();
    }
    
    protected void useValidatorButtonWidgetSelected(SelectionEvent evt) {
        LogManager.logFiner("useValidatorButton.widgetSelected, event="+evt);
        
        getConfig().getMinivilleConfiguration().setUseMinivilleValidator(useValidatorButton.getSelection());
        updateFormWithConfig();
    }
    
    protected void proxiesTypesComboWidgetSelected(SelectionEvent evt) {
        LogManager.logFiner("proxiesTypesCombo.widgetSelected, event="+evt);
        
        for (ProxyType proxyType: ProxyType.values()) {
            if (proxiesTypesCombo.getText().equals(getConfig().getProxyTypeLiteral(proxyType)))
                getConfig().setProxyType(proxyType);
        }
    }
}
