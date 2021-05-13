package robots.view.menu;

import robots.locale.LocaleChangeListener;
import robots.locale.LocaleListenersHolder;

import javax.swing.*;

class LookAndFeelMenu extends AbstractMenu implements LocaleChangeListener {
    public static final String SYSTEM_THEME_RESOURCE_KEY = "SystemThemeMenuItemTitle";
    public static final String CROSS_PLATFORM_RESOURCE_KEY = "CrossPlatformMenuItemTitle";
    private static final String RESOURCE_KEY = "lookAndFeelMenuTitle";

    public LookAndFeelMenu(int alias) {
        super(RESOURCE_KEY);
        setMnemonic(alias);
        add(new ThemeMenuItem(
                SYSTEM_THEME_RESOURCE_KEY,
                UIManager.getSystemLookAndFeelClassName()));
        add(new ThemeMenuItem(
                CROSS_PLATFORM_RESOURCE_KEY,
                UIManager.getCrossPlatformLookAndFeelClassName()));
        LocaleListenersHolder.register(this);
    }
}
