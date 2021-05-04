package robots.view.menu;

import robots.BundleConfig;
import robots.locale.LocaleChangeListener;
import robots.locale.LocaleListenersHolder;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

class LoggingMenu extends JMenu implements LocaleChangeListener {
    private static final String LOG_MESSAGE = "New line";
    private static final String RESOURCE_KEY = "logMenuTitle";

    public LoggingMenu(ResourceBundle bundle, int alias) {
        super(bundle.getString(RESOURCE_KEY));
        setMnemonic(alias);
        add(new LogMenuItem(LOG_MESSAGE));
        LocaleListenersHolder.register(this);
    }

    @Override
    public void onLanguageUpdate() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                BundleConfig.MENU_LABELS_BUNDLE_NAME, Locale.getDefault()
        );
        setText(resourceBundle.getString(RESOURCE_KEY));
        revalidate();
    }
}
