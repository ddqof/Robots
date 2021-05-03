package robots.view.menu;

import com.google.common.eventbus.Subscribe;
import robots.BundleConfig;
import robots.EventBusHolder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

class LookAndFeelMenu extends JMenu {
    public static final String SYSTEM_THEME_RESOURCE_KEY = "SystemThemeMenuItemTitle";
    public static final String CROSS_PLATFORM_RESOURCE_KEY = "CrossPlatformMenuItemTitle";
    private static final String RESOURCE_KEY = "lookAndFeelMenuTitle";

    public LookAndFeelMenu(ResourceBundle bundle, int alias) {
        super(bundle.getString(RESOURCE_KEY));
        setMnemonic(alias);
        add(new ThemeMenuItem(
                bundle.getString(SYSTEM_THEME_RESOURCE_KEY),
                UIManager.getSystemLookAndFeelClassName()));
        add(new ThemeMenuItem(
                bundle.getString(CROSS_PLATFORM_RESOURCE_KEY),
                UIManager.getCrossPlatformLookAndFeelClassName()));
        EventBusHolder.get().register(this);
    }

    @Subscribe
    private void onLanguageUpdate(ActionEvent e) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                BundleConfig.MENU_LABELS_BUNDLE_NAME, Locale.getDefault()
        );
        setText(resourceBundle.getString(RESOURCE_KEY));
    }
}
