package robots.view.menu;

import robots.BundleConfig;
import robots.locale.LocaleChangeListener;
import robots.locale.LocaleListenersHolder;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

class LanguageMenu extends JMenu implements LocaleChangeListener {
    private static final String RESOURCE_KEY = "langMenuTitle";
    private static final String RUSSIAN_LANG = "Русский";
    private static final String ENGLISH_LANG = "English";

    public LanguageMenu(ResourceBundle bundle, int alias) {
        super(bundle.getString(RESOURCE_KEY));
        setMnemonic(alias);
        JMenuItem ruMenuItem = new JMenuItem(RUSSIAN_LANG);
        ruMenuItem.addActionListener(actionEvent -> {
            Locale.setDefault(new Locale("ru", "RU"));
            LocaleListenersHolder.updateLanguage();
        });
        JMenuItem enMenuItem = new JMenuItem(ENGLISH_LANG);
        enMenuItem.addActionListener(actionEvent -> {
            Locale.setDefault(new Locale("en", "US"));
            LocaleListenersHolder.updateLanguage();
        });
        add(ruMenuItem);
        add(enMenuItem);
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
