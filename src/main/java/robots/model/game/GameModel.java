package robots.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import robots.serialize.JsonSerializable;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;

import java.io.File;
import java.util.*;

public class GameModel implements JsonSerializable {
    public static final File SAVES_FILE = new File(Saves.PATH, "gameModel" + Saves.JSON_EXTENSION);

    public static final int WIDTH = 550;
    public static final int HEIGHT = 550;

    private static final int STEP = 5;
    private final Level level;
    private Target currentTarget;
    private final Stack<Target> path;
    private boolean isGameOver;


    public GameModel() {
        this(Levels.getLevel(1));
    }

    public GameModel(Level level) {
        this(level, false);
    }

    @JsonCreator
    public GameModel(
            @JsonProperty("level") Level level,
            @JsonProperty("gameOver") boolean isGameOver) {
        this.level = level;
        this.path = findPath(level.getFinalTarget());
        this.currentTarget = path.pop();
        this.isGameOver = isGameOver;
    }

    public Level getLevel() {
        return level;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    private boolean isNotNearBorders(double positionX, double positionY) {
        for (Border border : level.getBorders()) {
            if (((border.getSide() == Side.LEFT || border.getSide() == Side.RIGHT)
                    && Math.abs(positionX - border.getStartX()) <= (double) STEP
                    && positionY <= border.getStartY()
                    && positionY >= border.getFinishY())
                    || (border.getSide() == Side.TOP || border.getSide() == Side.BOTTOM)
                    && Math.abs(positionY - border.getStartY()) <= (double) STEP
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
        int step = STEP;
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

    private Stack<Target> findPath(Target finalTarget) {
        Stack<Target> path = new Stack<>();
        ArrayDeque<Target> q = new ArrayDeque<>();
        HashSet<Target> visited = new HashSet<>();
        HashMap<Target, Target> parent = new HashMap<>();
        Target startTarget = new Target((int) level.getRobot().getPositionX(), (int) level.getRobot().getPositionY());
        parent.put(startTarget, null);
        q.addLast(startTarget);
        visited.add(startTarget);
        label:
        while (!q.isEmpty()) {
            Target v = q.poll();
            for (Target neighbour : getNeighbours(v)) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    q.addLast(neighbour);
                    parent.put(neighbour, v);
                    if (getDistanceBetween(neighbour, finalTarget) <= STEP && !finalTarget.equals(neighbour)) {
                        parent.put(finalTarget, neighbour);
                        break label;
                    }
                }
            }
        }
        Target currentParent = parent.get(finalTarget);
        while (currentParent != null) {
            path.push(currentParent);
            currentParent = parent.get(currentParent);
        }
        return path;
    }

    public void moveRobot() {
        Robot robot = level.getRobot();
        if (robot.getDistanceTo(currentTarget.getPositionX(), currentTarget.getPositionY()) < 1) {
            if (!path.empty()) currentTarget = path.pop();
            else isGameOver = true;
        } else {
            robot.move(currentTarget);
        }
    }

    @Override
    public boolean serialize() {
        return Save.storeObject(SAVES_FILE, this);
    }
}