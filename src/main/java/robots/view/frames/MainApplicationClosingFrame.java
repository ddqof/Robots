package robots.view.frames;

import robots.model.log.Logger;
import robots.view.menus.MainApplicationMenuBar;

import javax.swing.*;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается.
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 */
public class MainApplicationClosingFrame extends JFrameClosing {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private static final String CLOSING_CONFIRM_MESSAGE = "Do you want to exit?";
    private static final String CLOSING_DIALOG_TITLE = "Exit confirmation";

    public MainApplicationClosingFrame(String logMessage) {
        super(CLOSING_CONFIRM_MESSAGE, CLOSING_DIALOG_TITLE);
        setContentPane(desktopPane);
        Logger.debug(logMessage);
        setJMenuBar(new MainApplicationMenuBar(this));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void addFrame(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
}
