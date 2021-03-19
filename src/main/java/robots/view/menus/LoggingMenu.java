package robots.view.menus;

import javax.swing.*;

public class LoggingMenu extends JMenu {

    public LoggingMenu(String title, int alias) {
        super(title);
        setMnemonic(alias);
    }
}
