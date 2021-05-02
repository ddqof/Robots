package robots.view.menubar.menu.theme;

import javax.swing.*;

class ThemeMenuItem extends JMenuItem {

    public ThemeMenuItem(String schemeTitle, String systemLookAndFeelClassName) {
        super(schemeTitle);
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
