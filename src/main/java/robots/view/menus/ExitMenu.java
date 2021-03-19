package robots.view.menus;

import robots.view.frames.MainApplicationClosingFrame;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class ExitMenu extends JMenu {

    public ExitMenu(String title, MainApplicationClosingFrame frame) {
        super(title);
        setMnemonic(KeyEvent.VK_Q);
        addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                frame.dispatchEvent(
                        new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)
                );
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });
    }
}
