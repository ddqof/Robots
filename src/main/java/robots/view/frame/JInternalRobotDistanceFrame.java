package robots.view.frame;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import robots.model.game.GameModel;
import robots.model.game.Robot;
import robots.model.game.Target;
import robots.serialize.JInternalFrameDeserializer;
import robots.serialize.JInternalFrameSerializer;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;
import robots.view.Observer;

import javax.swing.*;
import java.io.File;

import static robots.view.frame.JInternalFrameUtils.getEmptyFrame;

@JsonSerialize(using = JInternalFrameSerializer.class)
@JsonDeserialize(using = JInternalFrameDeserializer.class)
public class JInternalRobotDistanceFrame extends AbstractJInternalFrame implements Observer {
    private static final int X = 1340;
    private static final int Y = 200;
    private static final int WIDTH = 230;
    private static final int HEIGHT = 90;
    private static final String TITLE_RESOURCE_KEY = "robotDistanceToFinalLabel";
    private final GameModel gameModel;
    private final JLabel robotDistLabel;
    public static final File SAVES_FILE = new File(Saves.PATH,
            String.format("distanceFrame.%s", Saves.JSON_EXTENSION));

    public static JInternalFrame getDefaultEmptyFrame() {
        return getEmptyFrame(WIDTH, HEIGHT, X, Y);
    }

    public JInternalRobotDistanceFrame(JInternalFrame internalFrame, GameModel gameModel) {
        super(internalFrame, TITLE_RESOURCE_KEY);
        this.gameModel = gameModel;
        this.gameModel.registerObs(this);
        robotDistLabel = new JLabel(getRobotDistanceToFinal());
        JPanel panel = new JPanel();
        panel.add(robotDistLabel);
        add(panel);
        setActionOnClose(() -> this.gameModel.unregisterObs(JInternalRobotDistanceFrame.this));
    }


    private String getRobotDistanceToFinal() {
        Robot robot = gameModel.getLevel().getRobot();
        Target finalTarget = gameModel.getLevel().getFinalTarget();
        return Double.toString(robot.getDistanceTo(finalTarget.getPositionX(), finalTarget.getPositionY()));
    }

    public JInternalRobotDistanceFrame(GameModel gameModel) {
        this(getDefaultEmptyFrame(), gameModel);
    }

    @Override
    public boolean serialize() {
        return Save.storeObject(SAVES_FILE, this);
    }

    @Override
    public void onUpdate() {
        robotDistLabel.setText(getRobotDistanceToFinal());
        revalidate();
    }
}
