package robots.view.menubar.menu.theme;

import com.google.common.eventbus.Subscribe;
import robots.BundleConfig;
import robots.EventBusHolder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class LookAndFeelMenu extends JMenu {
    public static final String SYSTEM_THEME_TITLE = "System theme";
    public static final String CROSS_PLATFORM_THEME_TITLE = "Cross-platform theme";
    private static final String RESOURCE_KEY = "lookAndFeelMenuTitle";

    public LookAndFeelMenu(int alias) {
        super(ResourceBundle.getBundle(
                BundleConfig.MENU_LABELS_BUNDLE_NAME).getString(RESOURCE_KEY));
        setMnemonic(alias);
        add(new ThemeMenuItem(
                SYSTEM_THEME_TITLE,
                UIManager.getSystemLookAndFeelClassName()));
        add(new ThemeMenuItem(
                CROSS_PLATFORM_THEME_TITLE,
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
