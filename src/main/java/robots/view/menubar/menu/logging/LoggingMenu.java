package robots.view.menubar.menu.logging;

import javax.swing.*;

public class LoggingMenu extends JMenu {
    private static final String TITLE = "Logging";
    private static final String LOG_MESSAGE = "New line";

    public LoggingMenu(int alias) {
        super(TITLE);
        setMnemonic(alias);
        add(new LogMenuItem(LOG_MESSAGE));
    }
}
