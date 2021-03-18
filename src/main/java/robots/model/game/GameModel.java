package robots.model.game;

public class GameModel {
    private final Robot robot;
    private Target target;

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
        robot.move(target, 0.1, 0, 10);
    }
}
