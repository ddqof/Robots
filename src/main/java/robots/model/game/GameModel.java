package robots.model.game;

public class GameModel {
    private final Robot robot;
    private Target target;
    private static final double DEFAULT_VELOCITY = 0.1;
    private static final double DEFAULT_ANGULAR_VELOCITY = 0;
    private static final double DEFAULT_DURATION = 10;

    public GameModel(Robot robot, Target target) {
        this.robot = robot;
        this.target = target;
    }

    public Robot getRobot() {
        return robot;
    }

    public Target getTarget() {
        return target;
    }

    public void update(int targetPositionX, int targetPositionY) {
        target = new Target(targetPositionX, targetPositionY);
        robot.move(
                target,
                DEFAULT_VELOCITY,
                DEFAULT_ANGULAR_VELOCITY,
                DEFAULT_DURATION
        );
    }
}
