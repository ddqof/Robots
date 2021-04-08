package robots.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.javatuples.Pair;
import robots.view.frames.MainApplicationClosingFrame;
import robots.view.internal_frames.ClosingInternalGameFrame;
import robots.view.internal_frames.ClosingInternalLogFrame;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Saves {
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

    public static boolean exists() {
        boolean gameModelCanBeRestored = GAME_MODEL_SAVES_FILE.exists() && GAME_MODEL_SAVES_FILE.exists();
        boolean logModelCanBeRestored = LOG_FRAME_SAVES_FILE.exists() && LOG_SOURCE_SAVES_FILE.exists();
        return gameModelCanBeRestored || logModelCanBeRestored;
    }

    public static Optional<Pair<ClosingInternalGameFrame, ClosingInternalLogFrame>> restore(
            MainApplicationClosingFrame frame) throws IOException {
        if (frame.askForSavesRestore() == JOptionPane.YES_OPTION) {
            ObjectMapper objectMapper = new ObjectMapper();
            ClosingInternalGameFrame gameFrame = objectMapper
                    .readValue(GAME_FRAME_SAVES_FILE, ClosingInternalGameFrame.class);
            ClosingInternalLogFrame logFrame = objectMapper.
                    readValue(LOG_FRAME_SAVES_FILE, ClosingInternalLogFrame.class);
            return Optional.of(new Pair<>(gameFrame, logFrame));
        }
        return Optional.empty();
    }

    public static Runnable getSaveAction(
            ClosingInternalGameFrame gameFrame, ClosingInternalLogFrame logFrame) {
        return () -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                if (!SAVES_PATH.exists()) {
                    SAVES_PATH.mkdir();
                }
                ObjectWriter prettyPrinter = mapper.writerWithDefaultPrettyPrinter();
                prettyPrinter.writeValue(GAME_FRAME_SAVES_FILE, gameFrame);
                prettyPrinter.writeValue(GAME_MODEL_SAVES_FILE, gameFrame.getGameModel());
                prettyPrinter.writeValue(LOG_FRAME_SAVES_FILE, logFrame);
                prettyPrinter.writeValue(LOG_SOURCE_SAVES_FILE, logFrame.getLogSource());
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
