package robots.view.menu;

import robots.model.log.Logger;

class PushLogMenuItem extends AbstractMenuItem {
    private static final String RESOURCE_KEY = "pushLogMenuItemTitle";

    public PushLogMenuItem(String logMessage) {
        super(RESOURCE_KEY);
        addActionListener(event -> Logger.debug(logMessage));
    }
}
