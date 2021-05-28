package robots.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Target extends GameEntity {
    @JsonCreator
    public Target(
            @JsonProperty("x") double x,
            @JsonProperty("y") double y
    ) {
        super(x, y);
    }
}
