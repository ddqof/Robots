package robots.model.game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class PathFinder {
    private final List<Border> borders;
    private final Target target;
    public static final int STEP = 5;

    public Target getFinalTarget() {
        return target;
    }

    public PathFinder(List<Border> borders, Target finalTarget) {
        this.borders = borders;
        this.target = finalTarget;
    }

    public static boolean isNotNearBorders(List<Border> borders, double positionX, double positionY, int step) {
        for (Border border : borders) {
            if ((
                    (border.getSide() == Side.LEFT || border.getSide() == Side.RIGHT)
                            && Math.abs(positionX - border.getStartX()) <= (double) step
                            && positionY <= border.getStartY()
                            && positionY >= border.getFinishY()
            ) ||
                    (
                            (border.getSide() == Side.TOP || border.getSide() == Side.BOTTOM)
                                    && Math.abs(positionY - border.getStartY()) <= (double) step
                                    && positionX <= border.getFinishX()
                                    && positionX >= border.getStartX()
                    )
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
        if (isNotNearBorders(borders, target.getX() + STEP, target.getY(), STEP))
            neighbours.add(new Target(target.getX() + STEP, target.getY()));
        if (isNotNearBorders(borders, target.getX() - STEP, target.getY(), STEP))
            neighbours.add(new Target(target.getX() - STEP, target.getY()));
        if (isNotNearBorders(borders, target.getX(), target.getY() + STEP, STEP))
            neighbours.add(new Target(target.getX(), target.getY() + STEP));
        if (isNotNearBorders(borders, target.getX(), target.getY() - STEP, STEP))
            neighbours.add(new Target(target.getX(), target.getY() - STEP));
        return neighbours;
    }

    private double getDistanceBetween(Target t1, Target t2) {
        double diffX = t1.getX() - t2.getX();
        double diffY = t1.getY() - t2.getY();
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public Stack<Target> findPath(GameEntity entity) {
        return findPath((int) entity.getX(), (int) entity.getY());
    }

    public Stack<Target> findPath(int startX, int startY) {
        ArrayDeque<Target> q = new ArrayDeque<>();
        HashMap<Target, Target> parent = new HashMap<>();
        Target startTarget = new Target(startX, startY);
        parent.put(startTarget, null);
        q.addLast(startTarget);
        label:
        while (!q.isEmpty()) {
            Target v = q.poll();
            for (Target neighbour : getNeighbours(borders, v)) {
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
