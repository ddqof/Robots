package robots.view.menu;

import robots.view.frame.MainApplicationClosingFrame;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MainApplicationMenuBar extends JMenuBar {
    public MainApplicationMenuBar(MainApplicationClosingFrame mainFrame) {
        add(new ExitMenu(mainFrame, KeyEvent.VK_E));
        add(new LoggingMenu(KeyEvent.VK_S));
        add(new LookAndFeelMenu(KeyEvent.VK_V));
        add(new LanguageMenu(KeyEvent.VK_L));
    }
}
