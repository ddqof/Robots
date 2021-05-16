package robots.view.frame;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import robots.model.game.GameModel;
import robots.serialize.JInternalFrameDeserializer;
import robots.serialize.JInternalFrameSerializer;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;
import robots.view.panel.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static robots.view.frame.JInternalFrameUtils.getEmptyFrame;

@JsonSerialize(using = JInternalFrameSerializer.class)
@JsonDeserialize(using = JInternalFrameDeserializer.class)
public class JInternalGameFrame extends AbstractJInternalFrame {
    public static final File SAVES_FILE = new File(Saves.PATH,
            String.format("gameFrame.%s", Saves.JSON_EXTENSION));
    private static final String RESOURCE_KEY = "gameFrameTitle";
    private static final int X = 1000;
    private static final int Y = 450;
    private final GameModel gameModel;

    public static JInternalFrame getDefaultFrame() {
        return getEmptyFrame(GameModel.WIDTH, GameModel.HEIGHT, X, Y);
    }

    public JInternalGameFrame(GameModel gameModel) {
        this(gameModel, getDefaultFrame());
    }

    public JInternalGameFrame(GameModel gameModel, JInternalFrame internalFrame) {
        super(internalFrame, RESOURCE_KEY);
        this.gameModel = gameModel;
        GamePanel gamePanel = new GamePanel(gameModel);
        this.gameModel.registerObs(gamePanel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gamePanel, BorderLayout.CENTER);
        getContentPane().add(panel);
    }

    @Override
    public boolean serialize() {
        return Save.storeObject(SAVES_FILE, this) && gameModel.serialize();
    }
}
