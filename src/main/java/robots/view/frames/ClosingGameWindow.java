package robots.view.frames;

import robots.model.game.GameModel;
import robots.view.panels.GamePanel;

import java.awt.*;
import javax.swing.JPanel;

public class ClosingGameWindow extends JInternalFrameClosing {
    private static final String FIELD_TITLE = "Game field";
    private static final String CLOSING_CONFIRM_MESSAGE = "Do you want to exit game window?";
    private static final String CLOSING_DIALOG_TITLE = "Exit game window?";

    public ClosingGameWindow(GameModel gameModel, int width, int height, int x, int y) {
        super(
                FIELD_TITLE,
                true,
                true,
                true,
                true,
                CLOSING_CONFIRM_MESSAGE,
                CLOSING_DIALOG_TITLE
        );
        GamePanel gamePanel = new GamePanel(gameModel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gamePanel, BorderLayout.CENTER);
        getContentPane().add(panel);
        setLocation(x, y);
        pack();
        setSize(width, height);
    }
}
