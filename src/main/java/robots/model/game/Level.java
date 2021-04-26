package robots.model.game;

import java.util.List;

public class Level {
    private final List<Border> borders;
    private final double DEFAULT_BORDER_SPACE;
    private final int NUMBER;
    private final Target TARGET;

    public Level(List<Border> borders, Target target, double space, int number) {
        this.borders = borders;
        DEFAULT_BORDER_SPACE = space;
        NUMBER = number;
        TARGET = target;
    }

    public double getDEFAULT_BORDER_SPACE() {
        return DEFAULT_BORDER_SPACE;
    }

    public List<Border> getBorders() {
        return borders;
    }

    public int getNUMBER() {
        return NUMBER;
    }

    public Target getTARGET() {
        return TARGET;
    }
}
