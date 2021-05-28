package robots.model.game;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class GameEntity {

    private double x;
    private double y;

    @Builder(builderMethodName = "entityBuilder")
    public GameEntity(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getDistanceTo(GameEntity entity) {
        return getDistanceTo(entity.x, entity.y);
    }

    public double getDistanceTo(double x, double y) {
        double diffX = this.x - x;
        double diffY = this.y - y;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public double angleTo(GameEntity entity) {
        return angleTo(entity.x, entity.y);
    }

    public double angleTo(double x, double y) {
        double diffX = x - this.x;
        double diffY = y - this.y;
        return Math.atan2(diffY, diffX);
    }
}
