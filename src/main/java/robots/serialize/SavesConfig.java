package robots.serialize;

import java.io.File;

public class SavesConfig {
    public static final File SAVES_PATH = new File("saves");
    private static final String JSON_EXTENSION = ".json";
    public static final File GAME_MODEL_SAVES_FILE = new File(SAVES_PATH, "gameModel" + JSON_EXTENSION);
    public static final File GAME_FRAME_SAVES_FILE = new File(SAVES_PATH, "gameFrame" + JSON_EXTENSION);
    public static final File LOG_FRAME_SAVES_FILE = new File(SAVES_PATH, "logFrame" + JSON_EXTENSION);
    public static final File LOG_SOURCE_SAVES_FILE = new File(SAVES_PATH, "logSource" + JSON_EXTENSION);

    public static final String ICON_FIELD_NAME = "isIcon";
    public static final String X_POS_FIELD_NAME = "positionX";
    public static final String Y_POS_FIELD_NAME = "positionY";
    public static final String WIDTH_FIELD_NAME = "width";
    public static final String HEIGHT_FIELD_NAME = "height";
    public static final String LOG_SOURCE_FIELD_NAME = "logSource";
    public static final String GAME_MODEL_FIELD_NAME = "gameModel";
    public static final String LOG_SOURCE_MESSAGES_FIELD_NAME = "messages";
}
