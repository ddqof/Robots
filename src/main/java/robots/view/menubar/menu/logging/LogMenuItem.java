package robots.view.menubar.menu.logging;

import robots.model.log.Logger;

import javax.swing.*;

class LogMenuItem extends JMenuItem {
    public static final String TITLE = "Create a log message";

    public LogMenuItem(String logMessage) {
        super(TITLE);
        addActionListener((event) -> Logger.debug(logMessage));
    }
}
