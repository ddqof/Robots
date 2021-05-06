package robots.view.menu.localized;

import robots.locale.LocaleListenersHolder;
import robots.model.log.Logger;

class LogMenuItem extends LocalizedMenuItem {
    private static final String RESOURCE_KEY = "logMenuItemTitle";

    public LogMenuItem(String logMessage) {
        super(RESOURCE_KEY);
        addActionListener(event -> Logger.debug(logMessage));
    }
}
