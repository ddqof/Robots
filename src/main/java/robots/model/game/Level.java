package robots.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Level {
    private final List<Border> borders;
    private final Target finalTarget;
    private final Robot robot;

    @JsonCreator
    public Level(
            @JsonProperty("borders") List<Border> borders,
            @JsonProperty("target") Target target,
            @JsonProperty("robot") Robot robot) {
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
