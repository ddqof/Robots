package robots.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Border {
    private final double startX;
    private final double startY;
    private final double finishX;
    private final double finishY;
    private final Side side;

    @JsonCreator
    public Border(
            @JsonProperty("startX") double xLeft,
            @JsonProperty("finishX") double xRight,
            @JsonProperty("startY") double yBottom,
            @JsonProperty("finishY") double yTop,
            @JsonProperty("side") Side side) {
        this.startX = xLeft;
        this.startY = yBottom;
        this.finishX = xRight;
        this.finishY = yTop;
        this.side = side;
    }

    public Side getSide() {
        return side;
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public double getFinishX() {
        return finishX;
    }

    public double getFinishY() {
        return finishY;
    }
}
