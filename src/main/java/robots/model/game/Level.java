package robots.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Level {
    private final List<Border> borders;
    private final Target finalTarget;
    private final List<Robot> robots;
    private final int turretsCount;


    public int getTurretsCount() {
        return turretsCount;
    }

    @JsonCreator
    public Level(
            @JsonProperty("borders") List<Border> borders,
            @JsonProperty("target") Target target,
            @JsonProperty("turretsCount") int turretsCount,
            @JsonProperty("robot") List<Robot> robots
    ) {
        this.borders = borders;
        this.finalTarget = target;
        this.robots = robots;
        this.turretsCount = turretsCount;
    }

    public List<Robot> getRobots() {
        return robots;
    }

    public List<Border> getBorders() {
        return borders;
    }

    public Target getFinalTarget() {
        return finalTarget;
    }


}
