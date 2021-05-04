package robots.view.menu;

import robots.BundleConfig;
import robots.locale.LocaleChangeListener;
import robots.locale.LocaleListenersHolder;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

class LookAndFeelMenu extends JMenu implements LocaleChangeListener {
    public static final String SYSTEM_THEME_RESOURCE_KEY = "SystemThemeMenuItemTitle";
    public static final String CROSS_PLATFORM_RESOURCE_KEY = "CrossPlatformMenuItemTitle";
    private static final String RESOURCE_KEY = "lookAndFeelMenuTitle";

    public LookAndFeelMenu(ResourceBundle bundle, int alias) {
        super(bundle.getString(RESOURCE_KEY));
        setMnemonic(alias);
        add(new ThemeMenuItem(
                bundle,
                SYSTEM_THEME_RESOURCE_KEY,
                UIManager.getSystemLookAndFeelClassName()));
        add(new ThemeMenuItem(
                bundle,
                CROSS_PLATFORM_RESOURCE_KEY,
                UIManager.getCrossPlatformLookAndFeelClassName()));
        LocaleListenersHolder.register(this);
    }

    @Override
    public void onLanguageUpdate() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                BundleConfig.MENU_LABELS_BUNDLE_NAME, Locale.getDefault()
        );
        setText(resourceBundle.getString(RESOURCE_KEY));
    }
}
