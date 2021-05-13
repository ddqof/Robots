package robots.view.menu;

import javax.swing.*;

class ThemeMenuItem extends AbstractMenuItem {
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
    }
}
