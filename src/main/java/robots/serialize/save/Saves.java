package robots.serialize.save;

import org.javatuples.Pair;
import robots.model.game.GameModel;
import robots.model.log.Logger;
import robots.view.frame.closing.ClosingInternalGameFrame;
import robots.view.frame.closing.ClosingInternalLogFrame;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Saves {
    public static final File PATH = new File("saves");
    public static final String JSON_EXTENSION = ".json";
    private static final String STATE_RESTORE_MSG = "Main application frame state restored";
    private static final String NEW_INSTANCES_CREATED = String.format(
            "New instances for: %s and %s was created",
            ClosingInternalLogFrame.class, ClosingInternalGameFrame.class
    );
    private final Save[] saves;

    public Saves(Save... saves) {
        this.saves = saves;
    }

    private Map<Class<?>, Optional<Object>> restore() {
        return Arrays.stream(saves)
                .collect(Collectors.toMap(Save::getSavedClass, Save::restore));
    }


    public Pair<ClosingInternalGameFrame, ClosingInternalLogFrame> restoreOrGetDefaultValues(int userChoice) {
        ClosingInternalGameFrame gameFrame = new ClosingInternalGameFrame(new GameModel());
        ClosingInternalLogFrame logFrame = new ClosingInternalLogFrame(Logger.getLogWindowSource());
        if (PATH.exists() && userChoice == JOptionPane.YES_OPTION) {
            Map<Class<?>, Optional<Object>> savedFrames = restore();
            Optional<?> restoredLogFrame = savedFrames.get(ClosingInternalLogFrame.class);
            Optional<?> restoredGameFrame = savedFrames.get(ClosingInternalGameFrame.class);
            if (restoredLogFrame.isPresent()) {
                logFrame = new ClosingInternalLogFrame(
                        Logger.getLogWindowSource(),
                        (JInternalFrame) restoredLogFrame.get()
                );
            }
            if (restoredGameFrame.isPresent()) {
                gameFrame = new ClosingInternalGameFrame(
                        (GameModel) savedFrames.get(GameModel.class).orElse(new GameModel()),
                        (JInternalFrame) restoredGameFrame.get()
                );
            }
            Logger.debug(STATE_RESTORE_MSG);
        } else {
            Logger.debug(NEW_INSTANCES_CREATED);
        }
        return new Pair<>(gameFrame, logFrame);
    }
}
