package robots.model.game;

public class GameModel {
    private final Robot robot;
    private Target target;
    private int spaceHeight;
    private int spaceWidth;

    public GameModel(Robot robot, Target target, int spaceHeight, int spaceWidth) {
        this.robot = robot;
        this.target = target;
        this.spaceHeight = spaceHeight;
        this.spaceWidth = spaceWidth;
    }

    public int getSpaceHeight() {
        return spaceHeight;
    }

    public int getSpaceWidth() {
        return spaceWidth;
    }

    public Robot getRobot() {
        return robot;
    }

    public Target getTarget() {
        return target;
    }

    public void update(int targetPositionX, int targetPositionY, int height, int width) {
        spaceHeight = height;
        spaceWidth = width;
        target = new Target(targetPositionX, targetPositionY);
        if (robot.getDistanceTo(target.getPositionX(), target.getPositionY()) < 0.5) {
            return;
        }
        robot.move(target, spaceHeight, spaceWidth);
    }
}
