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

    public void updateTarget(Target target) {
        this.target = target;
    }

    public void moveRobot(int spaceHeight, int spaceWidth) {
        if (robot.getDistanceTo(target.getPositionX(), target.getPositionY()) < 0.5) {
            return;
        }
        if (robot.getPositionX() > spaceWidth) {
            robot.setPositionX(spaceWidth);
        } else if(robot.getPositionX() < 0) {
            robot.setPositionX(0);
        } else if (robot.getPositionY() < 0) {
            robot.setPositionY(0);
        } else if (robot.getPositionY() > spaceHeight){
            robot.setPositionY(spaceHeight);
        } else {
            robot.move(target, spaceHeight, spaceWidth);
        }
    }
}
