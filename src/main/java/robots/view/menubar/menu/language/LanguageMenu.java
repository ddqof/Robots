package robots.view.menubar.menu.language;

import com.google.common.eventbus.Subscribe;
import robots.BundleConfig;
import robots.EventBusHolder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageMenu extends JMenu {
    private static final String RESOURCE_KEY = "langMenuTitle";
    private static final String RUSSIAN_LANG = "Русский";
    private static final String ENGLISH_LANG = "English";
    private static final String NO_BUTTON_KEY = "OptionPane.noButtonText";
    private static final String YES_BUTTON_KEY = "OptionPane.yesButtonText";

    public LanguageMenu(int alias) {
        super(
                ResourceBundle.getBundle(
                        BundleConfig.MENU_LABELS_BUNDLE_NAME).getString(RESOURCE_KEY)
        );
        setMnemonic(alias);
        JMenuItem ruMenuItem = new JMenuItem(RUSSIAN_LANG);
        ruMenuItem.addActionListener(actionEvent -> {
            Locale.setDefault(new Locale("ru", "RU"));
            localizePaneButtons();
            EventBusHolder.get().post(actionEvent);
        });
        JMenuItem enMenuItem = new JMenuItem(ENGLISH_LANG);
        enMenuItem.addActionListener(actionEvent -> {
            Locale.setDefault(new Locale("en", "US"));
            localizePaneButtons();
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

    private void localizePaneButtons() {
        UIManager.put(
                YES_BUTTON_KEY,
                ResourceBundle
                        .getBundle(BundleConfig.BUTTON_LABELS_BUNDLE_NAME)
                        .getString(YES_BUTTON_KEY));
        UIManager.put(
                NO_BUTTON_KEY,
                ResourceBundle
                        .getBundle(BundleConfig.BUTTON_LABELS_BUNDLE_NAME)
                        .getString(NO_BUTTON_KEY));
    }
}
