package robots.view.frame;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import robots.model.game.GameModel;
import robots.model.game.Levels;
import robots.model.game.Robot;
import robots.serialize.JInternalFrameDeserializer;
import robots.serialize.JInternalFrameSerializer;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;
import robots.view.Observer;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public static final File SAVES_FILE = new File(Saves.PATH,
            String.format("robotInfoFrame.%s", Saves.JSON_EXTENSION));

    private final GameModel gameModel;
    private final Map<Integer, JLabel> robotInfoLabels = new HashMap<>();

    public static JInternalFrame getDefaultEmptyFrame() {
        return getEmptyFrame(WIDTH, HEIGHT, X, Y);
    }

    public JInternalRobotInfoFrame(JInternalFrame internalFrame, GameModel gameModel) {
        super(internalFrame, TITLE_RESOURCE_KEY);
        this.gameModel = gameModel;
        JPanel panel = new JPanel();
        for (Robot robot : Levels.ALL_ROBOTS) {
            JLabel label = new JLabel(robot.str(gameModel.getLevel().getFinalTarget()));
            if (!gameModel.getLevel().getRobots().contains(robot)) {
                label.setVisible(false);
            }
            robotInfoLabels.put(robot.getId(), label);
            panel.add(label);
        }
        add(panel);
        this.gameModel.registerObs(this);
        setActionOnClose(() -> this.gameModel.unregisterObs(JInternalRobotInfoFrame.this));
//        onModelUpdate();
    }

    public JInternalRobotInfoFrame(GameModel gameModel) {
        this(getDefaultEmptyFrame(), gameModel);
    }

    @Override
    public void onModelUpdate() {
        List<Integer> ids = new ArrayList<>();
        if (gameModel.getState() == GameModel.State.RUNNING) {
            for (Robot robot : gameModel.getAliveRobots()) {
                ids.add(robot.getId());
                JLabel label = robotInfoLabels.get(robot.getId());
                label.setText(robot.str(gameModel.getLevel().getFinalTarget()));
                label.setVisible(true);
            }
            for (Map.Entry<Integer, JLabel> entry : robotInfoLabels.entrySet()) {
                if (!ids.contains(entry.getKey())) {
                    entry.getValue().setVisible(false);
                }
            }
        } else {
            for (JLabel label : robotInfoLabels.values()) {
                label.setVisible(false);
            }
        }
        revalidate();
    }

    @Override
    public boolean serialize() {
        return Save.storeObject(SAVES_FILE, this);
    }
}
