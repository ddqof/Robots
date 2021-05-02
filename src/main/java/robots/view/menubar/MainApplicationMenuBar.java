package robots.view.menubar;

import robots.view.menubar.menu.logging.LoggingMenu;
import robots.view.menubar.menu.theme.LookAndFeelMenu;
import robots.view.frame.closing.MainApplicationClosingFrame;
import robots.view.menubar.menu.exit.ExitMenu;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MainApplicationMenuBar extends JMenuBar {
    public MainApplicationMenuBar(MainApplicationClosingFrame mainFrame) {
        add(new ExitMenu(mainFrame, KeyEvent.VK_E));
        add(new LoggingMenu(KeyEvent.VK_S));
        add(new LookAndFeelMenu(KeyEvent.VK_V));
    }
}
