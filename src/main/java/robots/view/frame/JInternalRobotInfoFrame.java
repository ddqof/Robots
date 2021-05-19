//package robots.view.frame;
//
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import robots.model.game.GameModel;
//import robots.model.game.Robot;
//import robots.model.game.Target;
//import robots.serialize.JInternalFrameDeserializer;
//import robots.serialize.JInternalFrameSerializer;
//import robots.serialize.save.Save;
//import robots.serialize.save.Saves;
//import robots.view.Observer;
//
//import javax.swing.*;
//import java.io.File;
//
//import static robots.view.frame.JInternalFrameUtils.getEmptyFrame;
//
//@JsonSerialize(using = JInternalFrameSerializer.class)
//@JsonDeserialize(using = JInternalFrameDeserializer.class)
//public class JInternalRobotInfoFrame extends AbstractJInternalFrame implements Observer {
//    private static final int X = 1100;
//    private static final int Y = 200;
//    private static final int WIDTH = 300;
//    private static final int HEIGHT = 130;
//    private static final String TITLE_RESOURCE_KEY = "robotInfoLabel";
//    private final GameModel gameModel;
//    private final JLabel robotPosLabel;
//    private final JLabel robotHpLabel;
//    private final JLabel robotDistanceToTargetLabel;
//    public static final File SAVES_FILE = new File(Saves.PATH,
//            String.format("robotInfoFrame.%s", Saves.JSON_EXTENSION));
//
//    public static JInternalFrame getDefaultEmptyFrame() {
//        return getEmptyFrame(WIDTH, HEIGHT, X, Y);
//    }
//
//    public JInternalRobotInfoFrame(JInternalFrame internalFrame, GameModel gameModel) {
//        super(internalFrame, TITLE_RESOURCE_KEY);
//        this.gameModel = gameModel;
//        this.robotPosLabel = new JLabel(getRobotInfo());
//        this.robotHpLabel = new JLabel(getRobotHp());
//        this.robotDistanceToTargetLabel = new JLabel(getRobotDistanceToTarget());
//        JPanel panel = new JPanel();
//        panel.add(robotPosLabel);
//        panel.add(robotHpLabel);
//        panel.add(robotDistanceToTargetLabel);
//        add(panel);
//        this.gameModel.registerObs(this);
//        setActionOnClose(() -> this.gameModel.unregisterObs(JInternalRobotInfoFrame.this));
//    }
//
//    public JInternalRobotInfoFrame(GameModel gameModel) {
//        this(getDefaultEmptyFrame(), gameModel);
//    }
//
//    private String getRobotDistanceToTarget() {
//        Robot robot = gameModel.getLevel().getRobots();
//        Target finalTarget = gameModel.getLevel().getFinalTarget();
//        double distance = robot.getDistanceTo(finalTarget.getPositionX(), finalTarget.getPositionY());
//        return String.format("Distance to target: %f", distance);
//    }
//
//    private String getRobotHp() {
//        return String.format("Robot hp: %f", gameModel.getLevel().getRobots().getHp());
//    }
//
//    private String getRobotInfo() {
//        Robot robot = gameModel.getLevel().getRobots();
//        return String.format("Robot position: (%f, %f)", robot.getX(), robot.getY());
//    }
//
//    @Override
//    public void onUpdate() {
//        robotPosLabel.setText(getRobotInfo());
//        robotHpLabel.setText(getRobotHp());
//        robotDistanceToTargetLabel.setText(getRobotDistanceToTarget());
//        revalidate();
//    }
//
//    @Override
//    public boolean serialize() {
//        return Save.storeObject(SAVES_FILE, this);
//    }
//}
