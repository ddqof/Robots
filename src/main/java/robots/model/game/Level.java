package robots.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Level {
    private final List<Border> borders;
    private final Target finalTarget;
    private final Robot robot;
    private final int turretsCount;


    public int getTurretsCount() {
        return turretsCount;
    }

    @JsonCreator
    public Level(
            @JsonProperty("borders") List<Border> borders,
            @JsonProperty("target") Target target,
            @JsonProperty("robot") Robot robot,
            @JsonProperty("turretsCount") int turretsCount) {
        this.borders = borders;
        this.finalTarget = target;
        this.robot = robot;
        this.turretsCount = turretsCount;
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
