package robots.view.menu;

import com.google.common.eventbus.Subscribe;
import robots.BundleConfig;
import robots.EventBusHolder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

class ThemeMenuItem extends JMenuItem {

    public ThemeMenuItem(String schemeTitle, String systemLookAndFeelClassName) {
        super(schemeTitle);
        addActionListener((event) -> {
            try {
                UIManager.setLookAndFeel(systemLookAndFeelClassName);
                SwingUtilities.updateComponentTreeUI(this);
            } catch (ClassNotFoundException | InstantiationException
                    | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
            }
            this.invalidate();
        });
        EventBusHolder.get().register(this);
    }

    @Subscribe
    private void onLanguageUpdate(ActionEvent e) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                BundleConfig.MENU_LABELS_BUNDLE_NAME, Locale.getDefault()
        );
        if (getText().equals(LookAndFeelMenu.SYSTEM_THEME_RESOURCE_KEY)) {
            setText(resourceBundle.getString("SystemThemeMenuItemTitle"));
        } else {
            setText(resourceBundle.getString("CrossPlatformMenuItemTitle"));
        }
    }
}
