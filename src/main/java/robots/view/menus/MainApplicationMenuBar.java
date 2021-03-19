package robots.view.menus;

import robots.view.frames.MainApplicationClosingFrame;
import robots.view.menus.menu_items.LogMenuItem;
import robots.view.menus.menu_items.ThemeMenuItem;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MainApplicationMenuBar extends JMenuBar {

    public MainApplicationMenuBar(MainApplicationClosingFrame mainFrame) {
        add(new ExitMenu("Exit", mainFrame));
        LoggingMenu loggingMenu = new LoggingMenu("Logging", KeyEvent.VK_L);
        loggingMenu.add(new LogMenuItem("Create a log message", KeyEvent.VK_C, "New line"));
        add(loggingMenu);
        LookAndFeelMenu lookAndFeelMenu = new LookAndFeelMenu("View mode");
        lookAndFeelMenu.add(
                new ThemeMenuItem(
                        "System theme",
                        KeyEvent.VK_S,
                        UIManager.getSystemLookAndFeelClassName()
                )
        );
        lookAndFeelMenu.add(
                new ThemeMenuItem(
                        "Cross-platform theme",
                        KeyEvent.VK_C,
                        UIManager.getCrossPlatformLookAndFeelClassName()
                )
        );
        add(lookAndFeelMenu);
    }
}
