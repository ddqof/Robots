package robots.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectWriter;
import robots.serialize.MySerializable;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameModel implements MySerializable {
    public static final File SAVES_FILE = new File(Saves.PATH, "gameModel" + Saves.JSON_EXTENSION);

    public static final int WIDTH = 550;
    public static final int HEIGHT = 550;
    public static final double DEFAULT_ROBOT_POSITION_X = 0;
    public static final double DEFAULT_ROBOT_POSITION_Y = 200;
    public static final double DEFAULT_ROBOT_DIRECTION = Math.PI;

    private final Robot robot;
    private Target target;
    private final List<Border> borders = Levels.getLevel(0).getBorders();


    public GameModel() {
        this(
                new Robot(DEFAULT_ROBOT_POSITION_X, DEFAULT_ROBOT_POSITION_Y, DEFAULT_ROBOT_DIRECTION),
                Levels.getLevel(0).getTARGET()
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
            for (Border border : borders) {
                if (border.getSide() == Side.LEFT
                        && robot.getPositionX() < border.getStartX()
                        && robot.getPositionY() <= border.getStartY()
                        && robot.getPositionY() >= border.getFinishY()) {
                    robot.setPositionX(border.getStartX());
                }
                if (border.getSide() == Side.RIGHT
                        && robot.getPositionX() > border.getStartX()
                        && robot.getPositionY() <= border.getStartY()
                        && robot.getPositionY() >= border.getFinishY()) {
                    robot.setPositionX(border.getFinishX());
                }
                if (border.getSide() == Side.BOTTOM
                        && robot.getPositionY() < border.getStartY()
                        && robot.getPositionX() <= border.getFinishX()
                        && robot.getPositionX() >= border.getStartX()) {
                    robot.setPositionY(border.getStartY());
                }
                if (border.getSide() == Side.TOP
                        && robot.getPositionY() > border.getStartY()
                        && robot.getPositionX() <= border.getFinishX()
                        && robot.getPositionX() >= border.getStartX()) {
                    robot.setPositionY(border.getFinishY());
                }
            }
            robot.move(target, spaceHeight, spaceWidth, borders);
        }
    }

    @Override
    public boolean serialize(ObjectWriter writer) {
        return Save.storeObject(SAVES_FILE, this, writer);
    }
}