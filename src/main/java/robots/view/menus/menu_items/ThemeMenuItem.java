package robots.view.menus.menu_items;

import javax.swing.*;

public class ThemeMenuItem extends JMenuItem {

    public ThemeMenuItem(String schemeTitle, int alias, String systemLookAndFeelClassName) {
        super(schemeTitle, alias);
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
