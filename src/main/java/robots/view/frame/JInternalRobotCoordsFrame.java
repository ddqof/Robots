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

import static robots.view.frame.JInternalFrameUtils.getEmptyFrame;

@JsonSerialize(using = JInternalFrameSerializer.class)
@JsonDeserialize(using = JInternalFrameDeserializer.class)
public class JInternalRobotCoordsFrame extends AbstractJInternalFrame implements Observer {
    private static final int X = 1100;
    private static final int Y = 200;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 90;
    private static final String TITLE_RESOURCE_KEY = "robotPositionLabel";
    private final GameModel gameModel;
    private final JLabel robotPosLabel;
    public static final File SAVES_FILE = new File(Saves.PATH,
            String.format("coordsFrame.%s", Saves.JSON_EXTENSION));

    public static JInternalFrame getDefaultEmptyFrame() {
        return getEmptyFrame(WIDTH, HEIGHT, X, Y);
    }

    public JInternalRobotCoordsFrame(JInternalFrame internalFrame, GameModel gameModel) {
        super(internalFrame, TITLE_RESOURCE_KEY);
        this.gameModel = gameModel;
        this.robotPosLabel = new JLabel(getRobotInfoString());
        JPanel panel = new JPanel();
        panel.add(robotPosLabel);
        this.add(robotPosLabel);
        this.gameModel.registerObs(this);
    }

    public JInternalRobotCoordsFrame(GameModel gameModel) {
        this(getDefaultEmptyFrame(), gameModel);
    }

    private String getRobotInfoString() {
        Robot robot = gameModel.getLevel().getRobot();
        return String.format("(%f, %f)", robot.getPositionX(), robot.getPositionY());
    }

    @Override
    public void onUpdate() {
        robotPosLabel.setText(getRobotInfoString());
        robotPosLabel.revalidate();
        revalidate();
    }

    @Override
    public boolean serialize() {
        return Save.storeObject(SAVES_FILE, this);
    }
}
