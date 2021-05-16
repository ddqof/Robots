package robots.model.game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class PathFinder {
    private final Level level;
    private final Target target;
    public static final int STEP = 5;

    public Target getFinalTarget() {
        return target;
    }

    public PathFinder(Level level) {
        this.level = level;
        this.target = level.getFinalTarget();
    }

    private boolean isNotNearBorders(List<Border> borders, double positionX, double positionY) {
        for (Border border : borders) {
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

    private ArrayList<Target> getNeighbours(List<Border> borders, Target target) {
        ArrayList<Target> neighbours = new ArrayList<>();
        if (isNotNearBorders(borders, target.getPositionX() + STEP, target.getPositionY()))
            neighbours.add(new Target(target.getPositionX() + STEP, target.getPositionY()));
        if (isNotNearBorders(borders, target.getPositionX() - STEP, target.getPositionY()))
            neighbours.add(new Target(target.getPositionX() - STEP, target.getPositionY()));
        if (isNotNearBorders(borders, target.getPositionX(), target.getPositionY() + STEP))
            neighbours.add(new Target(target.getPositionX(), target.getPositionY() + STEP));
        if (isNotNearBorders(borders, target.getPositionX(), target.getPositionY() - STEP))
            neighbours.add(new Target(target.getPositionX(), target.getPositionY() - STEP));
        return neighbours;
    }

    private double getDistanceBetween(Target t1, Target t2) {
        double diffX = t1.getPositionX() - t2.getPositionX();
        double diffY = t1.getPositionY() - t2.getPositionY();
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public Stack<Target> findPath() {
        ArrayDeque<Target> q = new ArrayDeque<>();
        HashMap<Target, Target> parent = new HashMap<>();
        Target startTarget = new Target((int) level.getRobot().getPositionX(), (int) level.getRobot().getPositionY());
        parent.put(startTarget, null);
        q.addLast(startTarget);
        label:
        while (!q.isEmpty()) {
            Target v = q.poll();
            for (Target neighbour : getNeighbours(level.getBorders(), v)) {
                if (!parent.containsKey(neighbour)) {
                    q.addLast(neighbour);
                    parent.put(neighbour, v);
                    if (getDistanceBetween(neighbour, target) <= STEP && !target.equals(neighbour)) {
                        parent.put(target, neighbour);
                        break label;
                    }
                }
            }
        }
        return getPath(target, parent);
    }

    private Stack<Target> getPath(Target finalTarget, HashMap<Target, Target> parent) {
        Stack<Target> path = new Stack<>();
        Target currentParent = finalTarget;
        while (currentParent != null) {
            path.push(currentParent);
            currentParent = parent.get(currentParent);
        }
        path.pop();
        return path;
    }
}
