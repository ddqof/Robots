package robots.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectWriter;
import robots.serialize.MySerializable;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;

import java.io.File;

public class GameModel implements MySerializable {
    public static final File GAME_MODEL_SAVES_FILE = new File(Saves.SAVES_PATH, "gameModel" + Saves.JSON_EXTENSION);

    public static final double DEFAULT_ROBOT_POSITION_X = 50;
    public static final double DEFAULT_ROBOT_POSITION_Y = 50;
    public static final double DEFAULT_ROBOT_DIRECTION = Math.PI;
    public static final int DEFAULT_TARGET_POSITION_X = 50;
    public static final int DEFAULT_TARGET_POSITION_Y = 50;

    private final Robot robot;
    private Target target;

    public GameModel() {
        this(
                new Robot(DEFAULT_ROBOT_POSITION_X, DEFAULT_ROBOT_POSITION_Y, DEFAULT_ROBOT_DIRECTION),
                new Target(DEFAULT_TARGET_POSITION_X, DEFAULT_TARGET_POSITION_Y)
        );
    }

    @JsonCreator
    public GameModel(
            @JsonProperty("robot") Robot robot,
            @JsonProperty("target") Target target) {
        this.robot = robot;
        this.target = target;
    }

    public Robot getRobot() {
        return robot;
    }

    public Target getTarget() {
        return target;
    }

    public void updateTarget(Target target) {
        this.target = target;
    }

    public void moveRobot(int spaceHeight, int spaceWidth) {
        if (robot.getDistanceTo(target.getPositionX(), target.getPositionY()) < 0.5) {
            return;
        }
        if (robot.getPositionX() > spaceWidth) {
            robot.setPositionX(spaceWidth);
        } else if (robot.getPositionX() < 0) {
            robot.setPositionX(0);
        } else if (robot.getPositionY() < 0) {
            robot.setPositionY(0);
        } else if (robot.getPositionY() > spaceHeight) {
            robot.setPositionY(spaceHeight);
        } else {
            robot.move(target, spaceHeight, spaceWidth);
        }
    }

    @Override
    public boolean serialize(ObjectWriter writer) {
        return Save.storeObject(GAME_MODEL_SAVES_FILE, this, writer);
    }
}
