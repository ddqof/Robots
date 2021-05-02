package robots.model.game;

import java.util.List;

public class Level {
    private final List<Border> borders;
    private final Target finalTarget;
    private final Robot robot;

    public Level(List<Border> borders, Target target, Robot robot) {
        this.borders = borders;
        this.finalTarget = target;
        this.robot = robot;
    }

    public Robot getRobot() {
        return robot;
    }

    public List<Border> getBorders() {
        return borders;
    }

    public Target getFinalTarget() {
        return finalTarget;
    }


}
