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

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.cloudgarden.resource.SWTResourceManager;
import com.u8t.urlbooster.bean.Configuration;
import com.u8t.urlbooster.components.LogManager;

public class AboutBox {

    private Label versionLabel;
    private Label aboutPicture;
    private Button closeButton;
    private Shell shell;
    private Display parentDisplay;
    private CTabFolder aboutTabFolder;
    private CTabItem aboutTabItem;
    private Composite aboutComposite;

    public AboutBox(final Display display) {
        super();
        parentDisplay = display;
    }

    private void createContents() {
        shell = new Shell(parentDisplay, SWT.APPLICATION_MODAL);
        shell.setLayout(null);
        shell.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
        shell.setImage(SWTResourceManager.getImage("/images/info_obj.gif"));
        shell.setSize(360, 407);
        shell.setText("PRK[ex] About");

        aboutTabFolder = new CTabFolder(shell, SWT.FLAT | SWT.BORDER);
        aboutTabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
        aboutTabFolder.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
        aboutTabFolder.setSimple(false);
        aboutTabFolder.setBounds(5, 5, 348, 367);

        aboutTabItem = new CTabItem(aboutTabFolder, SWT.NONE);
        aboutTabItem.setImage(SWTResourceManager.getImage("/images/info_obj.gif"));
        aboutTabItem.setText("About");

        aboutComposite = new Composite(aboutTabFolder, SWT.NONE);
        aboutComposite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
        aboutTabItem.setControl(aboutComposite);

        aboutPicture = new Label(aboutComposite, SWT.NONE);
        aboutPicture.setImage(SWTResourceManager.getImage("/images/About.jpg"));
        aboutPicture.setBounds(0, 0, 347, 346);

        closeButton = new Button(shell, SWT.FLAT);
        closeButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(final SelectionEvent e) {
                LogManager.logFiner(e.toString());
                closeWindow();
            }
        });
        closeButton.setBounds(133, 378, 84, 21);
        closeButton.setText("Close");

        versionLabel = new Label(shell, SWT.NONE);
        versionLabel.setAlignment(SWT.RIGHT);
        versionLabel.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
        versionLabel.setBounds(252, 378, 98, 14);
        versionLabel.setText("version " + Configuration.version);
    }

    public final void open() {
        createContents();

        Rectangle windowRect = shell.getBounds();
        Rectangle displayRect = parentDisplay.getBounds();
        int x = (displayRect.width - windowRect.width) / 2;
        int y = (displayRect.height - windowRect.height) / 2;
        if (x > 0 && y > 0) {
            shell.setLocation(x, y);
        }

        shell.open();
    }

    public final void closeWindow() {
        shell.dispose();
    }

    public final Shell getShell() {
        return shell;
    }

    public final void setShell(final Shell shell) {
        this.shell = shell;
    }
}
