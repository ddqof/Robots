package robots.view.internal_frames;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import robots.controller.serialize.ClosingInternalGameFrameDeserializer;
import robots.controller.serialize.JInternalFrameSerializer;
import robots.model.game.GameModel;
import robots.model.game.Robot;
import robots.model.game.Target;
import robots.view.panels.GamePanel;

import javax.swing.*;
import java.awt.*;

@JsonSerialize(using = JInternalFrameSerializer.class)
@JsonDeserialize(using = ClosingInternalGameFrameDeserializer.class)
public class ClosingInternalGameFrame extends JInternalFrameClosing {
    private static final String FIELD_TITLE = "Game field";
    private static final String CLOSING_CONFIRM_MESSAGE = "Do you want to exit game window?";
    private static final String CLOSING_DIALOG_TITLE = "Exit game window?";
    private static final boolean SET_RESIZABLE_WINDOW = true;
    private static final boolean SET_CLOSABLE_WINDOW = true;
    private static final boolean SET_MAXIMIZABLE_WINDOW = true;
    private static final boolean SET_ICONIFIABLE_WINDOW = true;

    private static final double DEFAULT_ROBOT_POSITION_X = 50;
    private static final double DEFAULT_ROBOT_POSITION_Y = 50;
    private static final double DEFAULT_ROBOT_DIRECTION = Math.PI;
    private static final int DEFAULT_TARGET_POSITION_X = 50;
    private static final int DEFAULT_TARGET_POSITION_Y = 50;
    private static final int DEFAULT_GAME_WINDOW_HEIGHT = 200;
    private static final int DEFAULT_GAME_WINDOW_WIDTH = 200;
    private static final int DEFAULT_GAME_WINDOW_POS_X = 800;
    private static final int DEFAULT_GAME_WINDOW_POS_Y = 250;

    public GameModel getGameModel() {
        return gameModel;
    }

    private final GameModel gameModel;

    public static ClosingInternalGameFrame getDefaultInstance() {
        return new ClosingInternalGameFrame(
                new GameModel(
                        new Robot(DEFAULT_ROBOT_POSITION_X, DEFAULT_ROBOT_POSITION_Y, DEFAULT_ROBOT_DIRECTION),
                        new Target(DEFAULT_TARGET_POSITION_X, DEFAULT_TARGET_POSITION_Y)
                ),
                DEFAULT_GAME_WINDOW_POS_X,
                DEFAULT_GAME_WINDOW_POS_Y,
                DEFAULT_GAME_WINDOW_HEIGHT,
                DEFAULT_GAME_WINDOW_WIDTH
        );
    }

    public ClosingInternalGameFrame(
            GameModel gameModel,
            int locationX,
            int locationY,
            int height,
            int width) {
        super(
                FIELD_TITLE,
                SET_RESIZABLE_WINDOW,
                SET_CLOSABLE_WINDOW,
                SET_MAXIMIZABLE_WINDOW,
                SET_ICONIFIABLE_WINDOW,
                CLOSING_CONFIRM_MESSAGE,
                CLOSING_DIALOG_TITLE
        );
        this.gameModel = gameModel;
        GamePanel gamePanel = new GamePanel(gameModel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gamePanel, BorderLayout.CENTER);
        getContentPane().add(panel);
        setLocation(locationX, locationY);
        setSize(width, height);
    }
}
