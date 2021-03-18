package robots.view;

import robots.model.game.GameModel;

import java.awt.*;
import javax.swing.JPanel;

public class ClosingGameWindow extends JInternalFrameClosing {
    public ClosingGameWindow(GameModel gameModel) {
        super(
                "Игровое поле",
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
    }
}
