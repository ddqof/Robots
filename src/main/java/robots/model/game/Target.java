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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Target)) {
            return false;
        }
        return ((Target) obj).positionX == this.positionX && ((Target) obj).positionY == this.positionY;
    }

    @Override
    public int hashCode() {
        int res = 17;
        res = res * 31 + Math.min(positionX, positionY);
        res = res * 31 + Math.max(positionX, positionY);
        return res;
    }
}
