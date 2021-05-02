package robots.view.frame.closing;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import robots.model.game.GameModel;
import robots.serialize.JInternalFrameDeserializer;
import robots.serialize.JInternalFrameSerializer;
import robots.serialize.JsonSerializable;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;
import robots.view.panel.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static robots.view.frame.JInternalFrameUtils.getEmptyFrame;

@JsonSerialize(using = JInternalFrameSerializer.class)
@JsonDeserialize(using = JInternalFrameDeserializer.class)
public class ClosingInternalGameFrame extends JInternalFrameClosing implements JsonSerializable {
    private static final String TITLE = "Game field";
    public static final File SAVES_FILE = new File(Saves.PATH, "gameFrame" + Saves.JSON_EXTENSION);

    private static final int X = 1000;
    private static final int Y = 450;

    private final GameModel gameModel;

    public GameModel getGameModel() {
        return gameModel;
    }

    public ClosingInternalGameFrame(GameModel gameModel) {
        this(gameModel, getEmptyFrame(GameModel.WIDTH, GameModel.HEIGHT, X, Y));
    }

    public ClosingInternalGameFrame(GameModel gameModel, JInternalFrame internalFrame) {
        super(internalFrame, TITLE);
        this.gameModel = gameModel;
        GamePanel gamePanel = new GamePanel(gameModel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gamePanel, BorderLayout.CENTER);
        getContentPane().add(panel);
    }

    @Override
    public boolean serialize() {
        return Save.storeObject(SAVES_FILE, this);
    }
}
