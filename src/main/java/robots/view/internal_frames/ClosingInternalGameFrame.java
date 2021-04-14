package robots.view.internal_frames;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import robots.model.game.GameModel;
import robots.model.log.Logger;
import robots.serialize.JInternalFrameDeserializer;
import robots.serialize.JInternalFrameSerializer;
import robots.serialize.MySerializable;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;
import robots.view.panels.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.File;

@JsonSerialize(using = JInternalFrameSerializer.class)
@JsonDeserialize(using = JInternalFrameDeserializer.class)
public class ClosingInternalGameFrame extends JInternalFrameClosing implements MySerializable {
    private static final String FIELD_TITLE = "Game field";
    private static final String CLOSING_CONFIRM_MESSAGE = "Do you want to exit game window?";
    private static final String CLOSING_DIALOG_TITLE = "Exit game window?";
    private static final boolean SET_RESIZABLE_WINDOW = true;
    private static final boolean SET_CLOSABLE_WINDOW = true;
    private static final boolean SET_MAXIMIZABLE_WINDOW = true;
    private static final boolean SET_ICONIFIABLE_WINDOW = true;
    public static final File GAME_FRAME_SAVES_FILE = new File(Saves.SAVES_PATH, "gameFrame" + Saves.JSON_EXTENSION);
    public static final String FAILED_TO_SET_ICON_MSG = String.format(
            "Failed to set Icon status on %s", ClosingInternalGameFrame.class
    );

    public static final int DEFAULT_HEIGHT = 400;
    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_POS_X = 1000;
    public static final int DEFAULT_POS_Y = 450;

    public GameModel getGameModel() {
        return gameModel;
    }

    private final GameModel gameModel;

    public ClosingInternalGameFrame(GameModel gameModel, JInternalFrame internalFrame) {
        this(
                gameModel,
                internalFrame.getX(),
                internalFrame.getY(),
                internalFrame.getHeight(),
                internalFrame.getWidth(),
                internalFrame.isIcon()
        );
    }

    public ClosingInternalGameFrame(
            GameModel gameModel,
            int locationX,
            int locationY,
            int height,
            int width,
            boolean isIcon) {
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
        try {
            setIcon(isIcon);
        } catch (PropertyVetoException e) {
            Logger.error(FAILED_TO_SET_ICON_MSG);
        }
    }

    public static JInternalFrame getDefaultEmptyFrame() {
        JInternalFrame internalFrame = new JInternalFrame();
        internalFrame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        internalFrame.setLocation(DEFAULT_POS_X, DEFAULT_POS_Y);
        return internalFrame;
    }

    @Override
    public boolean serialize(ObjectWriter writer) {
        return Save.storeObject(GAME_FRAME_SAVES_FILE, this, writer);
    }
}
