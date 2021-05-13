package robots.view.menu;

import robots.model.log.Logger;

class LogMenuItem extends AbstractMenuItem {
    private static final String RESOURCE_KEY = "logMenuItemTitle";

    public LogMenuItem(String logMessage) {
        super(RESOURCE_KEY);
        addActionListener(event -> Logger.debug(logMessage));
    }
}
