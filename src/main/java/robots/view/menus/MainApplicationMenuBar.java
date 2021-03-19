package robots.view.menus;

import robots.view.frames.MainApplicationClosingFrame;
import robots.view.menus.menu_items.LogMenuItem;
import robots.view.menus.menu_items.ThemeMenuItem;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MainApplicationMenuBar extends JMenuBar {
    private static final String EXIT_MENU_TITLE = "Exit";
    private static final String LOGGING_MENU_TITLE = "Logging";
    private static final String LOG_MENU_ITEM_TITLE = "Create a log message";
    private static final String LOG_MESSAGE = "New line";
    private static final String LOOK_AND_FEEL_MENU_TITLE = "View mode";
    private static final String SYSTEM_THEME_TITLE = "System theme";
    private static final String CROSS_PLATFORM_THEME_TITLE = "Cross-platform theme";

    public MainApplicationMenuBar(MainApplicationClosingFrame mainFrame) {
        add(new ExitMenu(EXIT_MENU_TITLE, mainFrame));
        LoggingMenu loggingMenu = new LoggingMenu(LOGGING_MENU_TITLE, KeyEvent.VK_L);
        loggingMenu.add(
                new LogMenuItem(
                        LOG_MENU_ITEM_TITLE,
                        KeyEvent.VK_C,
                        LOG_MESSAGE
                )
        );
        add(loggingMenu);
        LookAndFeelMenu lookAndFeelMenu = new LookAndFeelMenu(LOOK_AND_FEEL_MENU_TITLE);
        lookAndFeelMenu.add(
                new ThemeMenuItem(
                        SYSTEM_THEME_TITLE,
                        KeyEvent.VK_S,
                        UIManager.getSystemLookAndFeelClassName()
                )
        );
        lookAndFeelMenu.add(
                new ThemeMenuItem(
                        CROSS_PLATFORM_THEME_TITLE,
                        KeyEvent.VK_C,
                        UIManager.getCrossPlatformLookAndFeelClassName()
                )
        );
        add(lookAndFeelMenu);
    }
}
