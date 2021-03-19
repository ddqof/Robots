package robots.view.frames;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import robots.model.log.Logger;
import robots.view.menus.MainApplicationMenuBar;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается.
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 */
public class MainApplicationClosingFrame extends JFrameClosing {
    private final JDesktopPane desktopPane = new JDesktopPane();

    public MainApplicationClosingFrame(String logMessage) {
        super("Do you want to exit?", "Exit confirmation");
        setContentPane(desktopPane);
        Logger.debug(logMessage);
        setJMenuBar(new MainApplicationMenuBar(this));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
}
