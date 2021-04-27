package robots.model.game;

import java.util.List;

public class Level {
    private final List<Border> borders;
    private final Target TARGET;

    public Level(List<Border> borders, Target target) {
        this.borders = borders;
        TARGET = target;
    }

    public List<Border> getBorders() {
        return borders;
    }

    public Target getTarget() {
        return TARGET;
    }
}
