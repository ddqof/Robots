package robots.view.menubar.menu.exit;

import robots.view.frame.closing.MainApplicationClosingFrame;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.WindowEvent;

public class ExitMenu extends JMenu {
    public static String TITLE = "Exit";

    public ExitMenu(MainApplicationClosingFrame frame, int alias) {
        super(TITLE);
        setMnemonic(alias);
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
