package robots.model.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Levels {
    private static final Map<Integer, Level> levels = getLevels();
    private static final double WIDTH = GameModel.WIDTH;
    private static final double HEIGHT = GameModel.HEIGHT;
    private static final double SPACE = 50;

    private static HashMap<Integer, Level> getLevels(){
        var levels = new HashMap<Integer, Level>();
        levels.put(0, getLevel0());
        return levels;
    }

    private static Level getLevel0(){
        ArrayList<Border> defaultBorders = new ArrayList<>();
        defaultBorders.add(new Border(
                0, HEIGHT / 2 - SPACE / 2,
                WIDTH, HEIGHT / 2 - SPACE / 2,
                Side.TOP));
        defaultBorders.add(new Border(
                0, HEIGHT / 2 + SPACE / 2,
                WIDTH, HEIGHT / 2 + SPACE / 2,
                Side.BOTTOM));
        return new Level(defaultBorders, new Target((int) WIDTH, (int) HEIGHT / 2), SPACE, 0);
    }

    public static Level getLevel(int num){
        return levels.get(num);
    }
}
