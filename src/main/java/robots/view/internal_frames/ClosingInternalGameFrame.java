package robots.view.internal_frames;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import robots.model.game.GameModel;
import robots.serialize.JInternalFrameDeserializer;
import robots.serialize.JInternalFrameSerializer;
import robots.serialize.MySerializable;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;
import robots.view.panels.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static robots.view.internal_frames.JInternalFrameUtils.getEmptyFrame;

@JsonSerialize(using = JInternalFrameSerializer.class)
@JsonDeserialize(using = JInternalFrameDeserializer.class)
public class ClosingInternalGameFrame extends JInternalFrameClosing implements MySerializable {
    private static final String TITLE = "Game field";
    private static final String CLOSING_CONFIRM_MESSAGE = "Do you want to exit game window?";
    private static final String CLOSING_DIALOG_TITLE = "Exit game window?";
    public static final File GAME_FRAME_SAVES_FILE = new File(Saves.SAVES_PATH, "gameFrame" + Saves.JSON_EXTENSION);

    public static final int HEIGHT = 400;
    public static final int WIDTH = 400;
    public static final int X = 1000;
    public static final int Y = 450;

    private final GameModel gameModel;

    public GameModel getGameModel() {
        return gameModel;
    }


    public ClosingInternalGameFrame(GameModel gameModel) {
        this(gameModel, getEmptyFrame(WIDTH, HEIGHT, X, Y));
    }

    public ClosingInternalGameFrame(GameModel gameModel, JInternalFrame internalFrame) {
        super(internalFrame, TITLE, CLOSING_CONFIRM_MESSAGE, CLOSING_DIALOG_TITLE);
        this.gameModel = gameModel;
        GamePanel gamePanel = new GamePanel(gameModel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gamePanel, BorderLayout.CENTER);
        getContentPane().add(panel);
    }

    @Override
    public boolean serialize(ObjectWriter writer) {
        return Save.storeObject(GAME_FRAME_SAVES_FILE, this, writer);
    }
}
