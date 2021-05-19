package robots.view.frame;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import robots.model.game.GameModel;
import robots.model.game.Robot;
import robots.serialize.JInternalFrameDeserializer;
import robots.serialize.JInternalFrameSerializer;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;
import robots.view.Observer;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static robots.view.frame.JInternalFrameUtils.getEmptyFrame;

@JsonSerialize(using = JInternalFrameSerializer.class)
@JsonDeserialize(using = JInternalFrameDeserializer.class)
public class JInternalRobotInfoFrame extends AbstractJInternalFrame implements Observer {
    private static final int X = 1100;
    private static final int Y = 200;
    private static final int WIDTH = 550;
    private static final int HEIGHT = 130;
    private static final String TITLE_RESOURCE_KEY = "robotInfoLabel";
    private final GameModel gameModel;
    private final Map<Integer, JLabel> robotInfoLabels = new HashMap<>();
    public static final File SAVES_FILE = new File(Saves.PATH,
            String.format("robotInfoFrame.%s", Saves.JSON_EXTENSION));

    public static JInternalFrame getDefaultEmptyFrame() {
        return getEmptyFrame(WIDTH, HEIGHT, X, Y);
    }

    public JInternalRobotInfoFrame(JInternalFrame internalFrame, GameModel gameModel) {
        super(internalFrame, TITLE_RESOURCE_KEY);
        this.gameModel = gameModel;
        JPanel panel = new JPanel();
        for (Robot robot : gameModel.getLevel().getRobots()) {
            JLabel label = new JLabel(robot.str(gameModel.getLevel().getFinalTarget()));
            robotInfoLabels.put(robot.getId(), label);
            panel.add(label);
        }
        add(panel);
        this.gameModel.registerObs(this);
        setActionOnClose(() -> this.gameModel.unregisterObs(JInternalRobotInfoFrame.this));
        onUpdate();
    }

    public JInternalRobotInfoFrame(GameModel gameModel) {
        this(getDefaultEmptyFrame(), gameModel);
    }

    @Override
    public void onUpdate() {
//        for (Robot robot: gameModel.getLevel().getRobots()) {
//            JLabel label = robotInfoLabels.get(robot.getId());
//            label.setText(robot.str(gameModel.getLevel().getFinalTarget()));
//        }
        revalidate();
    }

    //
    @Override
    public boolean serialize() {
        return Save.storeObject(SAVES_FILE, this);
    }
}
