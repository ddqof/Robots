package robots.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Target {
    private final int positionX;
    private final int positionY;

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    @JsonCreator
    public Target(
            @JsonProperty("positionX") int startPositionX,
            @JsonProperty("positionY") int startPositionY) {
        positionX = startPositionX;
        positionY = startPositionY;
    }

}
