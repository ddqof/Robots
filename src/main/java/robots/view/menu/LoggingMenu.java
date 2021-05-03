package robots.view.menu;

import com.google.common.eventbus.Subscribe;
import robots.BundleConfig;
import robots.EventBusHolder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

class LoggingMenu extends JMenu {
    private static final String LOG_MESSAGE = "New line";
    private static final String RESOURCE_KEY = "logMenuTitle";

    public LoggingMenu(ResourceBundle bundle, int alias) {
        super(bundle.getString(RESOURCE_KEY));
        setMnemonic(alias);
        add(new LogMenuItem(LOG_MESSAGE));
        EventBusHolder.get().register(this);
    }

    @Subscribe
    private void onLanguageUpdate(ActionEvent e) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                BundleConfig.MENU_LABELS_BUNDLE_NAME, Locale.getDefault()
        );
        setText(resourceBundle.getString(RESOURCE_KEY));
        revalidate();
    }
}
