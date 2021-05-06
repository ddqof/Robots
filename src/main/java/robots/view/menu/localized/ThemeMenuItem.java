package robots.view.menu.localized;

import robots.locale.LocaleListenersHolder;

import javax.swing.*;

class ThemeMenuItem extends LocalizedMenuItem {
    public ThemeMenuItem(String resourceKey, String systemLookAndFeelClassName) {
        super(resourceKey);
        addActionListener((event) -> {
            try {
                UIManager.setLookAndFeel(systemLookAndFeelClassName);
                SwingUtilities.updateComponentTreeUI(this);
            } catch (ClassNotFoundException | InstantiationException
                    | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
            }
            this.invalidate();
        });
        LocaleListenersHolder.register(this);
    }
}
