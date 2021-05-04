package robots.view.menu;

import robots.BundleConfig;
import robots.view.frame.closing.MainApplicationClosingFrame;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

public class MainApplicationMenuBar extends JMenuBar {
    public MainApplicationMenuBar(MainApplicationClosingFrame mainFrame) {
        ResourceBundle bundle = ResourceBundle.getBundle(BundleConfig.MENU_LABELS_BUNDLE_NAME);
        add(new ExitMenu(mainFrame, bundle, KeyEvent.VK_E));
        add(new LoggingMenu(bundle, KeyEvent.VK_S));
        add(new LookAndFeelMenu(bundle, KeyEvent.VK_V));
        add(new LanguageMenu(bundle, KeyEvent.VK_L));
    }
}
