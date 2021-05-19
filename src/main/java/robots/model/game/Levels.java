package robots.model.game;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Levels {
    private static final Map<Integer, Level> levels = getLevels();
    public static final double SPACE = 50;

    private static Map<Integer, Level> getLevels() {
        return Map.of(
                0, getLevel0(),
                1, getLevel1(),
                2, getLevel2()
        );
    }

    private static List<Robot> getDefaultRobotsPack(List<Border> borders, Target target) {
        double x = 0;
        double y = (double) GameModel.HEIGHT / 2;
        PathFinder pathFinder = new PathFinder(borders, target);
        Stack<Target> path = pathFinder.findPath(new GameEntity(x, y));
        return List.of(
                Robot.ofType(Robot.DEFAULT, x, y, path),
                Robot.ofType(Robot.HEAVY, x, y, path),
                Robot.ofType(Robot.DAMAGE_DEALER, x, y, path)
        );
    }

    private static Level getLevel0() {
        List<Border> defaultBorders = List.of(
                new Border(
                        0, GameModel.WIDTH, (double) GameModel.HEIGHT / 2 - SPACE / 2,
                        (double) GameModel.HEIGHT / 2 - SPACE / 2,
                        Side.TOP),
                new Border(
                        0,
                        GameModel.WIDTH,
                        (double) GameModel.HEIGHT / 2 + SPACE / 2,
                        (double) GameModel.HEIGHT / 2 + SPACE / 2,
                        Side.BOTTOM)

        );
        Target target = new Target(GameModel.WIDTH, GameModel.HEIGHT / 2);
        return new Level(
                defaultBorders,
                target,
                2,
                getDefaultRobotsPack(defaultBorders, target)
        );
    }

    private static Level getLevel1() {
        List<Border> defaultBorders = List.of(
                new Border(
                        0, (double) GameModel.WIDTH / 2, (double) GameModel.HEIGHT / 2 - SPACE / 2,
                        (double) GameModel.HEIGHT / 2 - SPACE / 2,
                        Side.TOP),
                new Border(
                        0,
                        (double) GameModel.WIDTH / 2 + SPACE,
                        (double) GameModel.HEIGHT / 2 + SPACE / 2,
                        (double) GameModel.HEIGHT / 2 + SPACE / 2,
                        Side.BOTTOM),
                new Border(
                        (double) GameModel.WIDTH / 2,
                        (double) GameModel.WIDTH / 2,
                        (double) GameModel.HEIGHT / 2 - SPACE / 2,
                        0,
                        Side.LEFT),
                new Border(
                        (double) GameModel.WIDTH / 2 + SPACE,
                        (double) GameModel.WIDTH / 2 + SPACE, (double) GameModel.HEIGHT / 2 + SPACE / 2,
                        0,
                        Side.RIGHT)
        );
        Target target = new Target((int) (GameModel.WIDTH / 2 + SPACE / 2), 0);
        return new Level(
                defaultBorders,
                target,
                2,
                getDefaultRobotsPack(defaultBorders, target)
        );
    }

    private static Level getLevel2() {
        List<Border> defaultBorders = List.of(
                new Border(
                        0,
                        (double) GameModel.WIDTH / 2,
                        (double) GameModel.HEIGHT / 2 - SPACE / 2,
                        (double) GameModel.HEIGHT / 2 - SPACE / 2,
                        Side.TOP),
                new Border(
                        0,
                        (double) GameModel.WIDTH / 2,
                        (double) GameModel.HEIGHT / 2 + SPACE / 2,
                        (double) GameModel.HEIGHT / 2 + SPACE / 2,
                        Side.BOTTOM),
                new Border(
                        (double) GameModel.WIDTH / 2,
                        (double) GameModel.WIDTH / 2,
                        (double) GameModel.HEIGHT / 1.7 + 3 * SPACE / 2,
                        (double) GameModel.HEIGHT / 2 + SPACE / 2,
                        Side.LEFT),
                new Border(
                        (double) GameModel.WIDTH / 2,
                        (double) GameModel.WIDTH / 2,
                        (double) GameModel.HEIGHT / 2 - SPACE / 2,
                        (double) GameModel.HEIGHT / 6 - SPACE,
                        Side.LEFT),
                new Border(
                        (double) GameModel.WIDTH / 2 + SPACE,
                        (double) GameModel.WIDTH / 2 + SPACE,
                        (double) GameModel.HEIGHT / 1.7 + SPACE / 2,
                        (double) GameModel.HEIGHT / 6,
                        Side.RIGHT),
                new Border(
                        (double) GameModel.WIDTH / 2 + SPACE,
                        (double) GameModel.WIDTH - SPACE,
                        (double) GameModel.HEIGHT / 6,
                        (double) GameModel.HEIGHT / 6,
                        Side.BOTTOM),
                new Border(
                        (double) GameModel.WIDTH / 2 + SPACE,
                        (double) GameModel.WIDTH - SPACE,
                        (double) GameModel.HEIGHT / 1.7 + SPACE / 2,
                        (double) GameModel.HEIGHT / 1.7 + SPACE / 2,
                        Side.TOP),
                new Border(
                        (double) GameModel.WIDTH / 2,
                        GameModel.WIDTH,
                        (double) GameModel.HEIGHT / 6 - SPACE,
                        (double) GameModel.HEIGHT / 6 - SPACE,
                        Side.TOP),
                new Border(
                        (double) GameModel.WIDTH / 2,
                        GameModel.WIDTH,
                        (double) GameModel.HEIGHT / 1.7 + 3 * SPACE / 2,
                        (double) GameModel.HEIGHT / 1.7 + 3 * SPACE / 2,
                        Side.BOTTOM),
                new Border(
                        (double) GameModel.WIDTH - SPACE,
                        (double) GameModel.WIDTH - SPACE,
                        (double) GameModel.HEIGHT / 1.7 + SPACE / 2,
                        (double) GameModel.HEIGHT / 6,
                        Side.LEFT),
                new Border(
                        GameModel.WIDTH,
                        GameModel.WIDTH,
                        (double) GameModel.HEIGHT / 1.7 + 3 * SPACE / 2,
                        (double) GameModel.HEIGHT / 6 - SPACE,
                        Side.RIGHT)
        );
        Target target = new Target((int) (GameModel.WIDTH - SPACE / 2), GameModel.HEIGHT / 2);
        return new Level(
                defaultBorders,
                target,
                2,
                getDefaultRobotsPack(defaultBorders, target)
        );
    }

    public static Level getLevel(int num) {
        return levels.get(num);
    }

    public static int levelsCount() {
        return levels.size();
    }
}
