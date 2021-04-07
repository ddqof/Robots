package robots.model.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Robot {
    private volatile double positionX;
    private volatile double positionY;
    private volatile double direction;
    public static final double DEFAULT_DURATION = 10;
    public static final double MAX_VELOCITY = 0.1;
    public static final double MAX_ANGULAR_VELOCITY = 0.001;

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getDirection() {
        return direction;
    }


    public Robot(
            @JsonProperty("positionX") double startPositionX,
            @JsonProperty("positionY") double startPositionY,
            @JsonProperty("direction") double direction) {
        positionX = startPositionX;
        positionY = startPositionY;
        this.direction = direction;
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

    public void move(Target target, int spaceHeight, int spaceWidth) {
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
        newDirection = asNormalizedRadians(resolveBorders(newDirection, spaceWidth, spaceHeight));
        direction = newDirection;
    }

    private double resolveBorders(double direction, int width, int height) {
        double resolvedDirection = direction;
        if ((positionX <= 0) || (positionY >= height)) {
            resolvedDirection += Math.PI;
        }
        if ((positionX >= width) || (positionY <= 0)) {
            resolvedDirection -= Math.PI;
        }
        return resolvedDirection;
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
