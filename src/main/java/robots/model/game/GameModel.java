package robots.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectWriter;
import robots.serialize.MySerializable;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;

import java.io.File;
import java.util.ArrayList;

public class GameModel implements MySerializable {
    public static final File SAVES_FILE = new File(Saves.PATH, "gameModel" + Saves.JSON_EXTENSION);

    public static final double DEFAULT_ROBOT_POSITION_X = 0;
    public static final double DEFAULT_ROBOT_POSITION_Y = 180;
    public static final double DEFAULT_ROBOT_DIRECTION = Math.PI;
    public static final int DEFAULT_TARGET_POSITION_X = 600;
    public static final int DEFAULT_TARGET_POSITION_Y = 180;
    public static final double DEFAULT_BORDER_SPACE = 30;


    private final Robot robot;
    private Target target;
    private ArrayList<Border> borders;

    public void setDefaultBorders(int height, int weight) {
        this.borders = getDefaultBorders(height, weight);
    }

    private static ArrayList<Border> getDefaultBorders(double height, double weight) {
        ArrayList<Border> defaultBorders = new ArrayList<>();
        defaultBorders.add(new Border(
                0, height / 2 - DEFAULT_BORDER_SPACE / 2,
                weight, height / 2 - DEFAULT_BORDER_SPACE / 2,
                Side.TOP));
        defaultBorders.add(new Border(
                0, height / 2 + DEFAULT_BORDER_SPACE / 2,
                weight, height / 2 + DEFAULT_BORDER_SPACE / 2,
                Side.BOTTOM));
        return defaultBorders;
    }

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

    public ArrayList<Border> getBorders() {
        return new ArrayList<>(borders);
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
            robot.move(target, spaceHeight, spaceWidth, borders);
        }
    }

    @Override
    public boolean serialize(ObjectWriter writer) {
        return Save.storeObject(SAVES_FILE, this, writer);
    }
}
