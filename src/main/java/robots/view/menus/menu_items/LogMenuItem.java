package robots.view.menus.menu_items;

import robots.model.log.Logger;

import javax.swing.*;

public class LogMenuItem extends JMenuItem {

    public LogMenuItem(String itemTitle, int alias, String logMessage) {
        super(itemTitle, alias);
        addActionListener((event) -> Logger.debug(logMessage));
    }
}
