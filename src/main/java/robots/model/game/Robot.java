package robots.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Stack;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Robot extends LiveEntity {
    public static final int DEFAULT = 0;//стандартный робот, который не умеет стрелять
    public static final int DAMAGE_DEALER = 1;//робот, который умеет стрелять
    public static final int HEAVY = 2;//робот, который имеет много хп, но идет медленно
    private static int count = 0;

    public static final double MAX_VELOCITY = 0.1;

    private double direction;
    private final int type;
    private final int id;
    private final double duration;
    private @Getter
    final Stack<Target> path;
    private @Setter
    Target currentTarget;

    public static Robot ofType(int type, double x, double y, Stack<Target> path) {
        RobotBuilder result;
        if (type == DEFAULT) {
            result = Robot.robotBuilder().hp(10).duration(7);
        } else if (type == DAMAGE_DEALER) {
            result = Robot.robotBuilder().
                    duration(10).
                    hp(20).
                    damage(20).
                    range(75).
                    timeout(1000);
        } else if (type == HEAVY) {
            result = Robot.robotBuilder().
                    hp(30).
                    duration(5);
        } else {
            throw new IllegalArgumentException("Illegal type of robot was passed");
        }
        return result.x(x).y(y).path(path).type(type).build();
    }

    @lombok.Builder(builderMethodName = "robotBuilder")
    @JsonCreator
    private Robot(
            @JsonProperty("x") double x,
            @JsonProperty("y") double y,
            @JsonProperty("duration") double duration,
            @JsonProperty("direction") double direction,
            @JsonProperty("hp") double hp,
            @JsonProperty("damage") double damage,
            @JsonProperty("viewRange") double range,
            @JsonProperty("restTime") long timeout,
            @JsonProperty("type") int type,
            @JsonProperty("path") Stack<Target> path
    ) {
        super(x, y, damage, hp, range, timeout);
        this.duration = duration;
        this.direction = direction;
        this.type = type;
        this.path = path;
        if (!path.isEmpty()) {
            this.currentTarget = this.path.pop();
        }
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
        return isTargetReached;
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
