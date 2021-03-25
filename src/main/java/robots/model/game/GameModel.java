package robots.model.game;

public class GameModel {
    private final Robot robot;
    private final Target target;
    private final int spaceHeight;
    private final int spaceWidth;

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

    public void moveRobot() {
        if (robot.getDistanceTo(target.getPositionX(), target.getPositionY()) < 0.5) {
            return;
        }
        if (robot.getPositionX() > spaceWidth) {
            robot.setPositionX(spaceWidth);
        } else if (robot.getPositionY() > spaceHeight){
            robot.setPositionY(spaceHeight);
        } else {
            robot.move(target, spaceHeight, spaceWidth);
        }
    }
}
