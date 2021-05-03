package robots.view.menu;

import com.google.common.eventbus.Subscribe;
import robots.BundleConfig;
import robots.EventBusHolder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

class LanguageMenu extends JMenu {
    private static final String RESOURCE_KEY = "langMenuTitle";
    private static final String RUSSIAN_LANG = "Русский";
    private static final String ENGLISH_LANG = "English";

    public LanguageMenu(ResourceBundle bundle, int alias) {
        super(bundle.getString(RESOURCE_KEY));
        setMnemonic(alias);
        JMenuItem ruMenuItem = new JMenuItem(RUSSIAN_LANG);
        ruMenuItem.addActionListener(actionEvent -> {
            Locale.setDefault(new Locale("ru", "RU"));
            EventBusHolder.get().post(actionEvent);
        });
        JMenuItem enMenuItem = new JMenuItem(ENGLISH_LANG);
        enMenuItem.addActionListener(actionEvent -> {
            Locale.setDefault(new Locale("en", "US"));
            EventBusHolder.get().post(actionEvent);
        });
        add(ruMenuItem);
        add(enMenuItem);
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
