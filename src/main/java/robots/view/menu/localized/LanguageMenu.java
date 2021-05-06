package robots.view.menu.localized;

import robots.locale.LocaleListenersHolder;

import javax.swing.*;
import java.util.Locale;

class LanguageMenu extends LocalizedMenu {
    private static final String RESOURCE_KEY = "langMenuTitle";
    private static final String RUSSIAN_LANG = "Русский";
    private static final String ENGLISH_LANG = "English";

    public LanguageMenu(int alias) {
        super(RESOURCE_KEY);
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
    }
}
