package robots.model.game;

import java.util.List;

public class Level {
    private final List<Border> borders;
    private final Target TARGET;
    private final double ROBOT_START_POSITION_X;
    private final double ROBOT_START_POSITION_Y;

    public Level(List<Border> borders, Target target, double defX, double defY) {
        this.borders = borders;
        TARGET = target;
        ROBOT_START_POSITION_X = defX;
        ROBOT_START_POSITION_Y = defY;
    }

    public double getROBOT_START_POSITION_X() {
        return ROBOT_START_POSITION_X;
    }

    public double getROBOT_START_POSITION_Y() {
        return ROBOT_START_POSITION_Y;
    }

    public List<Border> getBorders() {
        return borders;
    }

    public Target getTarget() {
        return TARGET;
    }


}
