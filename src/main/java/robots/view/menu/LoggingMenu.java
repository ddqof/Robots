package robots.view.menu;

class LoggingMenu extends AbstractMenu {
    private static final String LOG_MESSAGE = "New line";
    private static final String RESOURCE_KEY = "logMenuTitle";

    public LoggingMenu(int alias) {
        super(RESOURCE_KEY);
        setMnemonic(alias);
        add(new LogMenuItem(LOG_MESSAGE));
    }
}
