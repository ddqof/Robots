package robots.serialize.save;

import robots.model.game.GameModel;
import robots.model.log.Logger;
import robots.serialize.JsonSerializableLocale;
import robots.view.frame.closing.ClosingInternalGameFrame;
import robots.view.frame.closing.ClosingInternalLogFrame;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Saves {
    public static final File PATH = new File("saves");
    public static final String JSON_EXTENSION = "json";
    private static final String STATE_RESTORE_MSG = "Main application frame state restored";
    private static final String NEW_INSTANCES_CREATED = String.format(
            "New instances for: %s and %s was created",
            ClosingInternalLogFrame.class, ClosingInternalGameFrame.class
    );
    private final Map<Class<?>, Optional<Object>> retrievedSaves;

    public Saves(Save... saves) {
        this.retrievedSaves = Arrays.stream(saves)
                .collect(Collectors.toMap(Save::getSavedClass, Save::restore));
    }

    public Optional<ClosingInternalLogFrame> restoreLogFrame() {
        return retrievedSaves
                .get(ClosingInternalLogFrame.class)
                .map(logFrame -> new ClosingInternalLogFrame(
                        Logger.getLogWindowSource(),
                        (JInternalFrame) logFrame
                ));
    }

    public Optional<ClosingInternalGameFrame> restoreGameFrame() {
        return retrievedSaves
                .get(ClosingInternalGameFrame.class)
                .map(gameFrame -> new ClosingInternalGameFrame(
                        (GameModel) retrievedSaves.get(GameModel.class).orElse(new GameModel()),
                        (JInternalFrame) gameFrame)
                );
    }

    public Optional<Locale> restoreLocale() {
        return retrievedSaves
                .get(JsonSerializableLocale.class)
                .map(jsonLocale -> new Locale(
                        ((JsonSerializableLocale) jsonLocale).getLanguage(),
                        ((JsonSerializableLocale) jsonLocale).getCountry())
                );
    }
}
