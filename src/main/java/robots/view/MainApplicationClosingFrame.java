package robots.view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import robots.model.game.GameModel;
import robots.model.game.Robot;
import robots.model.game.Target;
import robots.model.log.Logger;


/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается.
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 */
public class MainApplicationClosingFrame extends JFrameClosing {
    private final JDesktopPane desktopPane = new JDesktopPane();

    public MainApplicationClosingFrame() {
        super("Do you want to exit?", "Exit confirmation");
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);
        setContentPane(desktopPane);
        ClosingLogWindow logWindow = createLogWindow();
        addWindow(logWindow);
        ClosingGameWindow gameWindow = createGameWindow();
        addWindow(gameWindow);
        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    protected ClosingGameWindow createGameWindow() {
        ClosingGameWindow closingGameWindow = new ClosingGameWindow(
                new GameModel(
                    new Robot(100, 100, 0),
                    new Target(150, 100)
                )
        );
        closingGameWindow.setSize(400, 400);
        return closingGameWindow;
    }

    protected ClosingLogWindow createLogWindow() {
        ClosingLogWindow closingLogWindow = new ClosingLogWindow(Logger.getDefaultLogSource());
        closingLogWindow.setLocation(10, 10);
        closingLogWindow.setSize(300, 800);
        setMinimumSize(closingLogWindow.getSize());
        closingLogWindow.pack();
        Logger.debug("Протокол работает");
        return closingLogWindow;
    }


    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(generateLookAndFeelMenu());
        menuBar.add(generateTestMenu());
        menuBar.add(generateExitMenu());
        return menuBar;
    }

    private JMenu generateExitMenu() {
        JMenu exitMenu = new JMenu("Выйти");
        exitMenu.setMnemonic(KeyEvent.VK_Q);
        exitMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                MainApplicationClosingFrame.this.dispatchEvent(
                        new WindowEvent(MainApplicationClosingFrame.this,
                                WindowEvent.WINDOW_CLOSING));
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });
        return exitMenu;
    }

    private JMenu generateTestMenu() {
        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription("Тестовые команды");
        addTestField(testMenu, "Сообщение в лог", "Новая строка");
        return testMenu;
    }

    private void addTestField(JMenu testMenu, String name, String message) {
        JMenuItem addLogMessageItem = new JMenuItem(name, KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> Logger.debug(message));
        testMenu.add(addLogMessageItem);
    }

    private JMenu generateLookAndFeelMenu() {
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");

        addScheme(lookAndFeelMenu, "Системная схема", UIManager.getSystemLookAndFeelClassName());
        addScheme(lookAndFeelMenu, "Универсальная схема", UIManager.getCrossPlatformLookAndFeelClassName());
        return lookAndFeelMenu;
    }

    private void addScheme(JMenu lookAndFeelMenu, String theme, String systemLookAndFeelClassName) {
        JMenuItem systemLookAndFeel = new JMenuItem(theme, KeyEvent.VK_S);
        systemLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(systemLookAndFeelClassName);
            this.invalidate();
        });
        lookAndFeelMenu.add(systemLookAndFeel);
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
        }
    }
}
