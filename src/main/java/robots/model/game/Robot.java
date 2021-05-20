package robots.model.game;

import robots.model.log.Logger;

import java.util.Objects;
import java.util.Stack;

public class Robot extends LiveEntity {
    public static final int DEFAULT = 0;//стандартный робот, который не умеет стрелять
    public static final int DAMAGE_DEALER = 1;//робот, который умеет стрелять
    public static final int HEAVY = 2;//робот, который имеет много хп, но идет медленно
    private static int count = 0;

    public static final double DEFAULT_DURATION = 7;
    public static final double MAX_VELOCITY = 0.1;

    private double direction;
    private final int type;
    private final int id;
    private final double duration;
    private Stack<Target> path;
    private Target currentTarget;

    public int getId() {
        return id;
    }

    public void setPath(Stack<Target> path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public double getDirection() {
        return direction;
    }

    public static Robot ofType(int type, double x, double y, Stack<Target> path) {
        switch (type) {
            case DEFAULT:
                return new Robot(x, y, 10, type, path);
            case DAMAGE_DEALER:
                return new Robot(x, y, 10, 0, 20, 20, 75, 1000, type, path);
            case HEAVY:
                return new Robot(x, y, 30, 5, type, path);
            default:
                throw new IllegalArgumentException("Illegal type of robot was passed");
        }
    }

    private Robot(double x, double y, double hp, int type, Stack<Target> path) {
        this(x, y, hp, DEFAULT_DURATION, type, path);
    }

    private Robot(double x, double y, double hp, double duration, int type, Stack<Target> path) {
        this(x, y, duration, 0, hp, 0, 0, Long.MAX_VALUE, type, path);
    }

    private Robot(
            double x,
            double y,
            double duration,
            double direction,
            double hp,
            double damage,
            double range,
            long timeout,
            int type,
            Stack<Target> path
    ) {
        super(x, y, damage, hp, range, timeout);
        this.duration = duration;
        this.direction = direction;
        this.type = type;
        this.path = path;
        this.currentTarget = this.path.pop();
        this.id = count++;
    }

    public boolean move() {
        boolean isTargetReached = false;
        if (getDistanceTo(currentTarget) < 1) {
            if (!path.empty()) {
                currentTarget = path.pop();
            } else {
                isTargetReached = true;
            }
        } else {
            double velocity = MAX_VELOCITY;
            double newX = getX() + velocity * duration * Math.cos(direction);
            double newY = getY() + velocity * duration * Math.sin(direction);
            setX(newX);
            setY(newY);
            direction = angleTo(currentTarget);
        }
        if (type == DAMAGE_DEALER) {
            Logger.debug(String.format("(%f, %f), dir: %f", getX(), getY(), direction));
        }
        return isTargetReached;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Robot robot = (Robot) o;
        return Double.compare(robot.direction, direction) == 0 && type == robot.type && Double.compare(robot.duration, duration) == 0 && path.equals(robot.path) && currentTarget.equals(robot.currentTarget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), direction, type, duration, path, currentTarget);
    }

    public String str(GameEntity entity) {
        return String.format(
                "Robot %d: hp=%f, pos=(%f, %f), dist=%f",
                id,
                getHp(),
                getX(),
                getY(),
                getDistanceTo(entity)
        );
    }
}
