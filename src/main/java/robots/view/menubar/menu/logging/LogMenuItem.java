package robots.view.menubar.menu.logging;

import com.google.common.eventbus.Subscribe;
import robots.BundleConfig;
import robots.EventBusHolder;
import robots.model.log.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

class LogMenuItem extends JMenuItem {
    private static final String RESOURCE_KEY = "logMenuItemTitle";

    public LogMenuItem(String logMessage) {
        super(
                ResourceBundle.getBundle(
                        BundleConfig.MENU_LABELS_BUNDLE_NAME).getString(RESOURCE_KEY)
        );
        addActionListener(event -> Logger.debug(logMessage));
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
