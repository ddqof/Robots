package robots.model.game;

import java.util.List;
import java.util.Map;

public class Levels {
    private static final Map<Integer, Level> levels = getLevels();
    private static final double SPACE = 50;

    private static Map<Integer, Level> getLevels() {
        return Map.of(
                0, getLevel0(),
                1, getLevel1(),
                2, getLevel2()
        );
    }

    private static Level getLevel0() {
        List<Border> defaultBorders = List.of(
                new Border(
                        0, GameModel.WIDTH, (double) GameModel.HEIGHT / 2 - SPACE / 2,
                        (double) GameModel.HEIGHT / 2 - SPACE / 2,
                        Side.TOP),
                new Border(
                        0, GameModel.WIDTH, (double) GameModel.HEIGHT / 2 + SPACE / 2,
                        (double) GameModel.HEIGHT / 2 + SPACE / 2,
                        Side.BOTTOM)

        );
        return new Level(
                defaultBorders,
                new Target(GameModel.WIDTH, GameModel.HEIGHT / 2),
                new Robot(0,
                        (double) GameModel.HEIGHT / 2,
                        0)
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
        return new Level(
                defaultBorders,
                new Target((int) (GameModel.WIDTH / 2 + SPACE / 2), 0),
                new Robot(0,
                        (double) GameModel.HEIGHT / 2, 0));
    }

    private static Level getLevel2(){
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
                        (double) GameModel.HEIGHT / 2 - SPACE / 2,
                        (double) GameModel.HEIGHT / 6,
                        Side.LEFT)
        );
        return new Level(
                defaultBorders,
                new Target(GameModel.WIDTH, GameModel.HEIGHT / 2),
                new Robot(0,
                        (double) GameModel.HEIGHT / 2,
                        0
                )
        );
    }

    public static Level getLevel(int num) {
        return levels.get(num);
    }
}
