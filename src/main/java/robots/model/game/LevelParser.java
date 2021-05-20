package robots.model.game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LevelParser {
    private static final int levelFileHeight = GameModel.HEIGHT / 10;
    private static final int levelFileWight = GameModel.WIDTH / 10;


    public static Tile[][] parse(int levelNumber) throws IOException {
        if (levelNumber < 0 || levelNumber > 3) {
            throw new IllegalArgumentException(
                    String.format("There are no level with number: %d", levelNumber)
            );
        }
        Path levelPath = Paths.get("levels", String.format("%d.txt", levelNumber));
        List<String> lines = Files.readAllLines(levelPath);
        if (lines.size() != levelFileHeight) {
            throw new IllegalStateException(
                    String.format("File height should be equals: %d", levelFileHeight)
            );
        }
        lines.forEach(x -> {
            if (x.length() != levelFileWight) {
                throw new IllegalStateException(
                        String.format("Each line of file should contains %d symbols", levelFileWight)
                );
            }
        });
        Tile[][] result = new Tile[GameModel.WIDTH][GameModel.HEIGHT];
        for (int i = 0; i < GameModel.HEIGHT; i++) {
            for (int j = 0; j < GameModel.WIDTH; j++) {
                char currentChar = lines.get(i / 10).charAt(j / 10);
                Tile value;
                if (currentChar == '1') {
                    value = Tile.ROBOT;
                } else if (currentChar == '0') {
                    value = Tile.TARGET;
                } else if (currentChar == '@') {
                    value = Tile.EMPTY;
                } else if (currentChar == '#') {
                    value = Tile.WALL;
                } else {
                    throw new IllegalStateException(
                            String.format("Illegal char was found in level file: %s", currentChar)
                    );
                }
                result[i][j] = value;
            }
        }
        return result;
    }
}
