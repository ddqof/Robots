package robots.view.menu;

import robots.view.frame.MainApplicationClosingFrame;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.WindowEvent;

class ExitMenu extends AbstractMenu {
    private static final String RESOURCE_KEY = "exitMenuTitle";

    public ExitMenu(MainApplicationClosingFrame frame, int alias) {
        super(RESOURCE_KEY);
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
