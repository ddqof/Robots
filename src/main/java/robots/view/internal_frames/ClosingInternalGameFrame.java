package robots.view.internal_frames;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import robots.model.game.GameModel;
import robots.serialize.JInternalFrameSerializer;
import robots.view.panels.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;

import static robots.serialize.SavesConfig.*;

@JsonSerialize(using = JInternalFrameSerializer.class)
public class ClosingInternalGameFrame extends JInternalFrameClosing {
    private static final String FIELD_TITLE = "Game field";
    private static final String CLOSING_CONFIRM_MESSAGE = "Do you want to exit game window?";
    private static final String CLOSING_DIALOG_TITLE = "Exit game window?";
    private static final boolean SET_RESIZABLE_WINDOW = true;
    private static final boolean SET_CLOSABLE_WINDOW = true;
    private static final boolean SET_MAXIMIZABLE_WINDOW = true;
    private static final boolean SET_ICONIFIABLE_WINDOW = true;

    public GameModel getGameModel() {
        return gameModel;
    }

    private final GameModel gameModel;

    public ClosingInternalGameFrame(
            @JsonProperty(GAME_MODEL_FIELD_NAME) GameModel gameModel,
            @JsonProperty(X_POS_FIELD_NAME) int locationX,
            @JsonProperty(Y_POS_FIELD_NAME) int locationY,
            @JsonProperty(HEIGHT_FIELD_NAME) int height,
            @JsonProperty(WIDTH_FIELD_NAME) int width,
            @JsonProperty(ICON_FIELD_NAME) boolean isIcon) {
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
        pack();
        setSize(width, height);
        try {
            setIcon(isIcon);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }
}
