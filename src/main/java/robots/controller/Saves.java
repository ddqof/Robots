package robots.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.javatuples.Pair;
import robots.controller.serialize.MySerializable;
import robots.view.frames.MainApplicationClosingFrame;
import robots.view.internal_frames.ClosingInternalGameFrame;
import robots.view.internal_frames.ClosingInternalLogFrame;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
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
    public static final String LOG_SOURCE_MESSAGES_FIELD_NAME = "messages";

    public static final String LOG_WINDOW_RESTORE_FAILED_MSG = "Log window restoring failed due to corrupted save files.";
    public static final String GAME_WINDOW_RESTORE_FAILED_MSG = "Game window restoring failed due to corrupted files.";

    private final MainApplicationClosingFrame frame;

    public Saves(MainApplicationClosingFrame frameToSave) {
        this.frame = frameToSave;
    }

    public Pair<Optional<ClosingInternalGameFrame>, Optional<ClosingInternalLogFrame>> restore() {
        Optional<ClosingInternalGameFrame> optionalGameFrame = Optional.empty();
        Optional<ClosingInternalLogFrame> optionalLogFrame = Optional.empty();
        if (SAVES_PATH.exists() && frame.askForSavesRestore() == JOptionPane.YES_OPTION) {
            optionalGameFrame = loadSave(
                    GAME_FRAME_SAVES_FILE, ClosingInternalGameFrame.class, Saves.GAME_WINDOW_RESTORE_FAILED_MSG);
            optionalLogFrame = loadSave(
                    LOG_FRAME_SAVES_FILE, ClosingInternalLogFrame.class, Saves.LOG_WINDOW_RESTORE_FAILED_MSG);
        }
        return new Pair<>(optionalGameFrame, optionalLogFrame);
    }

    private <T> Optional<T> loadSave(File saveFile, Class<T> className, String messageOnFail) {
        try {
            return Optional.of(new ObjectMapper().readValue(saveFile, className));
        } catch (IOException e) {
            frame.showSavesRestoreFailedDialog(messageOnFail);
            return Optional.empty();
        }
    }

    public void storeAtExit(List<MySerializable> objectsToSave) {
        frame.setActionOnClose(
                () -> {
                    if (!SAVES_PATH.exists()) SAVES_PATH.mkdir();
                    ObjectWriter prettyPrinter = new ObjectMapper().writerWithDefaultPrettyPrinter();
                    objectsToSave.forEach(x -> {
                        try {
                            x.serialize(prettyPrinter);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
        );
    }
}
