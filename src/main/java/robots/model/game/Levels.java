package robots.model.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Levels {
    private static final Map<Integer, Level> levels = getLevels();
    private static final double SPACE = 50;

    private static HashMap<Integer, Level> getLevels() {
        var levels = new HashMap<Integer, Level>();
        levels.put(0, getLevel0());
        levels.put(1, getLevel1());
        return levels;
    }

    private static Level getLevel0() {
        ArrayList<Border> defaultBorders = new ArrayList<>();
        defaultBorders.add(new Border(
                0, (double) GameModel.HEIGHT / 2 - SPACE / 2,
                GameModel.WIDTH, (double) GameModel.HEIGHT / 2 - SPACE / 2,
                Side.TOP));
        defaultBorders.add(new Border(
                0, (double) GameModel.HEIGHT / 2 + SPACE / 2,
                GameModel.WIDTH, (double) GameModel.HEIGHT / 2 + SPACE / 2,
                Side.BOTTOM));
        return new Level(
                defaultBorders,
                new Target(GameModel.WIDTH, GameModel.HEIGHT / 2),
                new Robot(0,
                        (double) GameModel.HEIGHT / 2,
                        0)
        );
    }

    private static Level getLevel1() {
        ArrayList<Border> defaultBorders = new ArrayList<>();
        defaultBorders.add(new Border(
                0, (double) GameModel.HEIGHT / 2 - SPACE / 2,
                (double) GameModel.WIDTH / 2, (double) GameModel.HEIGHT / 2 - SPACE / 2,
                Side.TOP));
        defaultBorders.add(new Border(
                0, (double) GameModel.HEIGHT / 2 + SPACE / 2,
                (double) GameModel.WIDTH / 2 + SPACE, (double) GameModel.HEIGHT / 2 + SPACE / 2,
                Side.BOTTOM));
        defaultBorders.add(new Border(
                (double) GameModel.WIDTH / 2,
                (double) GameModel.HEIGHT / 2 - SPACE / 2,
                (double) GameModel.WIDTH / 2,
                -GameModel.HEIGHT,
                Side.LEFT));
        defaultBorders.add(new Border(
                (double) GameModel.WIDTH / 2 + SPACE,
                (double) GameModel.HEIGHT / 2 + SPACE / 2,
                (double) GameModel.WIDTH / 2 + SPACE,
                -GameModel.HEIGHT,
                Side.RIGHT));
        return new Level(
                defaultBorders,
                new Target((int) (GameModel.WIDTH / 2 + SPACE / 2), 0),
                new Robot(0,
                        (double) GameModel.HEIGHT / 2, 0));
    }

    public static Level getLevel(int num) {
        return levels.get(num);
    }
}
