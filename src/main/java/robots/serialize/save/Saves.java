package robots.serialize.save;

import org.javatuples.Pair;
import robots.model.game.GameModel;
import robots.model.log.Logger;
import robots.serialize.JsonSerializableLocale;
import robots.view.frame.AbstractJInternalFrame;
import robots.view.frame.JInternalGameFrame;
import robots.view.frame.JInternalLogFrame;

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
    public static final String NEW_INSTANCE_CREATED = "New instance was created: %s";
    public static final String INSTANCE_RESTORED = "Instance was restored: %s";
    private final Map<Class<?>, Optional<Object>> retrievedSaves;
    private final int userChoice;

    public Saves(int userChoice, Save... saves) {
        this.retrievedSaves = Arrays.stream(saves)
                .collect(Collectors.toMap(Save::getSavedClass, Save::restore));
        this.userChoice = userChoice;
    }

    private Optional<JInternalLogFrame> restoreLogFrame() {
        return retrievedSaves
                .get(JInternalLogFrame.class)
                .map(logFrame -> new JInternalLogFrame(
                        Logger.getLogWindowSource(),
                        (JInternalFrame) logFrame
                ));
    }

    private Optional<JInternalGameFrame> restoreGameFrame() {
        return retrievedSaves
                .get(JInternalGameFrame.class)
                .map(gameFrame -> new JInternalGameFrame(
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

    public Pair<JInternalGameFrame, JInternalLogFrame> restoreInternalFrames() {
        return new Pair<>(
                getOrDefault(restoreGameFrame(), new JInternalGameFrame(new GameModel())),
                getOrDefault(restoreLogFrame(), new JInternalLogFrame(Logger.getLogWindowSource()))
        );
    }

    private <T extends AbstractJInternalFrame> T getOrDefault(Optional<T> opt, T initialValue) {
        T result = userChoice == JOptionPane.YES_OPTION ? opt.orElse(initialValue) : initialValue;
        String logMsg = result == initialValue ? NEW_INSTANCE_CREATED : INSTANCE_RESTORED;
        Logger.debug(String.format(logMsg, initialValue.getClass().getName()));
        return result;
    }
}
