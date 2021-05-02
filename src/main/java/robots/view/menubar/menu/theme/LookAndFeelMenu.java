package robots.view.menubar.menu.theme;

import javax.swing.*;

public class LookAndFeelMenu extends JMenu {
    public static final String TITLE = "View mode";
    private static final String SYSTEM_THEME_TITLE = "System theme";
    private static final String CROSS_PLATFORM_THEME_TITLE = "Cross-platform theme";

    public LookAndFeelMenu(int alias) {
        super(TITLE);
        setMnemonic(alias);
        add(new ThemeMenuItem(
                SYSTEM_THEME_TITLE,
                UIManager.getSystemLookAndFeelClassName())
        );
        add(new ThemeMenuItem(
                CROSS_PLATFORM_THEME_TITLE,
                UIManager.getCrossPlatformLookAndFeelClassName())
        );
    }
}
