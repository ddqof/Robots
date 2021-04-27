package robots.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectWriter;
import robots.serialize.MySerializable;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;

import java.io.File;
import java.util.*;

public class GameModel implements MySerializable {
    public static final File SAVES_FILE = new File(Saves.PATH, "gameModel" + Saves.JSON_EXTENSION);

    public static final int WIDTH = 550;
    public static final int HEIGHT = 550;

    public static final double DEFAULT_ROBOT_DIRECTION = Math.PI;

    private final Robot robot;
    private final Level level;
    private Target target;
    private final Stack<Target> path;


    public GameModel() {
        this(Levels.getLevel(1));
    }

    @JsonCreator
    public GameModel(Level level) {
        this.robot = new Robot(
                level.getROBOT_START_POSITION_X(),
                level.getROBOT_START_POSITION_Y(),
                DEFAULT_ROBOT_DIRECTION);
        this.level = level;
        this.path = findPath(level.getTarget());
        this.target = path.pop();
    }

    public Robot getRobot() {
        return robot;
    }

    public Target getTarget() {
        return target;
    }

    public ArrayList<Border> getBorders() {
        return new ArrayList<>(level.getBorders());
    }

    private boolean isNotNearBorders(double positionX, double positionY) {
        for (Border border : level.getBorders()) {
            if (((border.getSide() == Side.LEFT || border.getSide() == Side.RIGHT)
                    && Math.abs(positionX - border.getStartX()) <= 10
                    && positionY <= border.getStartY()
                    && positionY >= border.getFinishY())
                    || (border.getSide() == Side.TOP || border.getSide() == Side.BOTTOM)
                    && Math.abs(positionY - border.getStartY()) <= 10
                    && positionX <= border.getFinishX()
                    && positionX >= border.getStartX()
                    || positionX < 0
                    || positionY < 0
                    || positionX > GameModel.WIDTH
                    || positionY > GameModel.HEIGHT) {
                return false;
            }
        }
        return true;
    }

    private ArrayList<Target> getNeighbours(Target target) {
        ArrayList<Target> neighbours = new ArrayList<>();
        int step = 10;
        if (isNotNearBorders(target.getPositionX() + step, target.getPositionY()))
            neighbours.add(new Target(target.getPositionX() + step, target.getPositionY()));
        if (isNotNearBorders(target.getPositionX() - step, target.getPositionY()))
            neighbours.add(new Target(target.getPositionX() - step, target.getPositionY()));
        if (isNotNearBorders(target.getPositionX(), target.getPositionY() + step))
            neighbours.add(new Target(target.getPositionX(), target.getPositionY() + step));
        if (isNotNearBorders(target.getPositionX(), target.getPositionY() - step))
            neighbours.add(new Target(target.getPositionX(), target.getPositionY() - step));
        return neighbours;
    }

    private double getDistanceBetween(Target t1, Target t2) {
        double diffX = t1.getPositionX() - t2.getPositionX();
        double diffY = t1.getPositionY() - t2.getPositionY();
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private Stack<Target> findPath(Target finish) {
        Stack<Target> path = new Stack<>();
        ArrayDeque<Target> q = new ArrayDeque<>();
        HashSet<Target> visited = new HashSet<>();
        HashMap<Target, Target> parent = new HashMap<>();
        Target startTarget = new Target((int) level.getROBOT_START_POSITION_X(), (int) level.getROBOT_START_POSITION_Y());
        parent.put(startTarget, null);
        q.addLast(startTarget);
        visited.add(startTarget);
        label: while (!q.isEmpty()) {
            Target v = q.poll();
            for (Target neighbour : getNeighbours(v)) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    q.addLast(neighbour);
                    parent.put(neighbour, v);
                    if (getDistanceBetween(neighbour, finish) <= 10 && !finish.equals(neighbour)) {
                        parent.put(finish, neighbour);
                        break label;
                    }
                }
            }
        }
        Target currentParent = parent.get(finish);
        while (currentParent != null) {
            path.push(currentParent);
            currentParent = parent.get(currentParent);
        }
        return path;
    }

    public void moveRobot(int spaceHeight, int spaceWidth) {
        if (robot.getDistanceTo(target.getPositionX(), target.getPositionY()) < 1) {
            if (!path.empty())
                target = path.pop();
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
            robot.move(target, level.getBorders());
        }
    }

    @Override
    public boolean serialize(ObjectWriter writer) {
        return Save.storeObject(SAVES_FILE, this, writer);
    }
}