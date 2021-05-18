package robots.model.game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LevelParser {
    public static Tile[][] parse(int levelNumber) throws IOException {
        if (levelNumber < 0 || levelNumber > 3) {
            throw new IllegalArgumentException(
                    String.format("There are no level with number: %d", levelNumber)
            );
        }
        Path levelPath = Paths.get("levels", String.format("%d.txt", levelNumber));
        System.out.println(levelPath.toAbsolutePath());
        List<String> lines = Files.readAllLines(levelPath);
        if (lines.size() != GameModel.HEIGHT) {
            throw new IllegalStateException(
                    String.format("File height should be equals: %d", GameModel.HEIGHT)
            );
        }
        lines.forEach(x -> {
            if (x.length() != GameModel.WIDTH) {
                throw new IllegalStateException(
                        String.format("Each line of file should contains %d symbols", GameModel.WIDTH)
                );
            }
        });
        Tile[][] result = new Tile[GameModel.WIDTH][GameModel.HEIGHT];
        for (int i = 0; i < GameModel.HEIGHT; i++) {
            for (int j = 0; j < GameModel.WIDTH; j++) {
                char currentChar = lines.get(i).charAt(j);
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

    public static void main(String[] args) throws IOException {
        var res = parse(0);
        var s = "a";
    }
}
