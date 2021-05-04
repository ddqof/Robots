package robots.view.frame.closing;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import robots.BundleConfig;
import robots.locale.LocaleListenersHolder;
import robots.model.game.GameModel;
import robots.serialize.JInternalFrameDeserializer;
import robots.serialize.JInternalFrameSerializer;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;
import robots.view.panel.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

import static robots.view.frame.JInternalFrameUtils.getEmptyFrame;

@JsonSerialize(using = JInternalFrameSerializer.class)
@JsonDeserialize(using = JInternalFrameDeserializer.class)
public class ClosingInternalGameFrame extends JInternalFrameClosing {
    public static final File SAVES_FILE = new File(Saves.PATH,
            String.format("gameFrame.%s", Saves.JSON_EXTENSION));
    private static final String RESOURCE_KEY = "gameFrameTitle";

    private static final int X = 1000;
    private static final int Y = 450;

    private final GameModel gameModel;

    public ClosingInternalGameFrame(GameModel gameModel) {
        this(gameModel, getEmptyFrame(GameModel.WIDTH, GameModel.HEIGHT, X, Y));
    }

    public ClosingInternalGameFrame(GameModel gameModel, JInternalFrame internalFrame) {
        super(
                internalFrame,
                ResourceBundle.getBundle(
                        BundleConfig.FRAME_LABELS_BUNDLE_NAME).getString(RESOURCE_KEY));
        this.gameModel = gameModel;
        GamePanel gamePanel = new GamePanel(gameModel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gamePanel, BorderLayout.CENTER);
        getContentPane().add(panel);
        LocaleListenersHolder.register(this);
    }

    @Override
    public boolean serialize() {
        return Save.storeObject(SAVES_FILE, this) && gameModel.serialize();
    }

    @Override
    public void onLanguageUpdate() {
        ResourceBundle labels = ResourceBundle.getBundle(
                BundleConfig.FRAME_LABELS_BUNDLE_NAME, Locale.getDefault());
        setTitle(labels.getString(RESOURCE_KEY));
        revalidate();
    }
}
