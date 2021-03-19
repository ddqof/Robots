package robots.view.frames;

import robots.model.game.GameModel;
import robots.view.panels.GamePanel;

import java.awt.*;
import javax.swing.JPanel;

public class ClosingGameWindow extends JInternalFrameClosing {
    public ClosingGameWindow(GameModel gameModel, int width, int height) {
        super(
                "Game field",
                true,
                true,
                true,
                true,
                "Do you want to exit game window?",
                "Exit game window"
        );
        GamePanel gamePanel = new GamePanel(gameModel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gamePanel, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        setSize(width, height);
    }
}
