package robots.view.menu;

import robots.BundleConfig;
import robots.locale.LocaleChangeListener;
import robots.locale.LocaleListenersHolder;
import robots.model.log.Logger;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

class LogMenuItem extends JMenuItem implements LocaleChangeListener {
    private static final String RESOURCE_KEY = "logMenuItemTitle";

    public LogMenuItem(String logMessage) {
        super(
                ResourceBundle.getBundle(
                        BundleConfig.MENU_LABELS_BUNDLE_NAME).getString(RESOURCE_KEY)
        );
        addActionListener(event -> Logger.debug(logMessage));
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
