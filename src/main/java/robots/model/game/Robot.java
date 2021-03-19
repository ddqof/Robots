package robots.model.game;


public class Robot {
    private volatile double positionX;
    private volatile double positionY;
    private volatile double direction;
    private final double maxVelocity;
    private final double maxAngularVelocity;

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public double getDirection() {
        return direction;
    }


    public Robot(double startPositionX, double startPositionY, double direction) {
        positionX = startPositionX;
        positionY = startPositionY;
        this.direction = direction;
        maxVelocity = 0.1;
        maxAngularVelocity = 0.001;
    }

    public void move(
            Target target,
            double velocity,
            double angularVelocity,
            double duration
    ) {
        double distance = getDistanceTo(target.getPositionX(), target.getPositionY());
        if (distance < 0.5) return;
        updateDirection(target.getPositionX(), target.getPositionY());
        var angle = direction + angularVelocity * duration;
        velocity = applyLimits(velocity, -1, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);

        double newX = positionX + velocity / angularVelocity * (Math.sin(angle) - Math.sin(direction));
        if (!Double.isFinite(newX))
            newX = positionX + velocity * duration * Math.cos(direction);

        double newY = positionY - velocity / angularVelocity * (Math.cos(angle) - Math.cos(direction));
        if (!Double.isFinite(newY))
            newY = positionY + velocity * duration * Math.sin(direction);

        positionX = newX;
        positionY = newY;
        direction = asNormalizedRadians(angle);
    }

    private double getDistanceTo(double targetPositionX, double targetPositionY) {
        double diffX = positionX - targetPositionX;
        double diffY = positionY - targetPositionY;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private void updateDirection(double targetPositionX, double targetPositionY) {
        double diffX = targetPositionX - positionX;
        double diffY = targetPositionY - positionY;
        direction = asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    private static double applyLimits(double value, double min, double max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    private static double asNormalizedRadians(double angle) {
        while (angle < 0)
            angle += 2 * Math.PI;
        while (angle >= 2 * Math.PI)
            angle -= 2 * Math.PI;
        return angle;
    }
}
