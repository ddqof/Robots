package robots.view.menu;

import robots.model.log.Logger;

public class ResetLogMenuItem extends AbstractMenuItem {
    private static final String RESOURCE_KEY = "resetLogMenuItemTitle";

    public ResetLogMenuItem() {
        super(RESOURCE_KEY);
        addActionListener(event -> Logger.reset());
    }
}
