package robots.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Robot {
    private double positionX;
    private double positionY;
    private double direction;
    private double hp;

    public static final double DEFAULT_DURATION = 10;
    public static final double MAX_VELOCITY = 0.1;
    public static final double MAX_ANGULAR_VELOCITY = 0.001;

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public double getDirection() {
        return direction;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    @JsonCreator
    public Robot(
            @JsonProperty("positionX") double startPositionX,
            @JsonProperty("positionY") double startPositionY,
            @JsonProperty("direction") double direction,
            @JsonProperty("hp") int hp
    ) {
        this.positionX = startPositionX;
        this.positionY = startPositionY;
        this.direction = direction;
        this.hp = hp;
    }

    public Robot(double x, double y, double direction) {
        this(x, y, direction, 100);
    }

    private double getAngularVelocity(Target target) {
        double angularVelocity = 0;
        double angleToTarget = angleTo(target.getPositionX(), target.getPositionY());
        if (angleToTarget > direction) {
            angularVelocity = MAX_ANGULAR_VELOCITY;
        }
        if (angleToTarget < direction) {
            angularVelocity = -MAX_ANGULAR_VELOCITY;
        }
        return angularVelocity;
    }

    public void move(Target target) {
        double velocity = MAX_VELOCITY;
        double angularVelocity = getAngularVelocity(target);
        double duration = DEFAULT_DURATION;
        double newDirection = direction + angularVelocity * duration;
        double newX = positionX + velocity / angularVelocity *
                (Math.sin(newDirection) - Math.sin(direction));
        if (!Double.isFinite(newX)) {
            newX = positionX + velocity * duration * Math.cos(direction);
        }
        double newY = positionY - velocity / angularVelocity *
                (Math.cos(newDirection) - Math.cos(direction));
        if (!Double.isFinite(newY)) {
            newY = positionY + velocity * duration * Math.sin(direction);
        }
        positionX = newX;
        positionY = newY;
        direction = angleTo(target.getPositionX(), target.getPositionY());
    }

    public double getDistanceTo(double targetPositionX, double targetPositionY) {
        double diffX = positionX - targetPositionX;
        double diffY = positionY - targetPositionY;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public double angleTo(double targetPositionX, double targetPositionY) {
        double diffX = targetPositionX - positionX;
        double diffY = targetPositionY - positionY;
        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    private static double asNormalizedRadians(double angle) {
        while (angle < 0)
            angle += 2 * Math.PI;
        while (angle >= 2 * Math.PI)
            angle -= 2 * Math.PI;
        return angle;
    }
}
