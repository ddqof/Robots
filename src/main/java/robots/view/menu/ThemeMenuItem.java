package robots.view.menu;

import com.google.common.eventbus.Subscribe;
import robots.BundleConfig;
import robots.EventBusHolder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

class ThemeMenuItem extends JMenuItem {
    private final String resourceKey;

    public ThemeMenuItem(ResourceBundle bundle, String resourceKey, String systemLookAndFeelClassName) {
        super(bundle.getString(resourceKey));
        this.resourceKey = resourceKey;
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
        setText(resourceBundle.getString(resourceKey));
    }
}
