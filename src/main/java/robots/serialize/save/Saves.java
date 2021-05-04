package robots.serialize.save;

import org.javatuples.Pair;
import robots.model.game.GameModel;
import robots.model.log.Logger;
import robots.serialize.JsonSerializableLocale;
import robots.view.frame.closing.ClosingInternalGameFrame;
import robots.view.frame.closing.ClosingInternalLogFrame;
import robots.view.frame.closing.JInternalFrameClosing;

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

    public Pair<ClosingInternalGameFrame, ClosingInternalLogFrame> restoreInternalFrames(int userOption) {
        return new Pair<>(
                extractFromOptional(
                        restoreGameFrame(),
                        new ClosingInternalGameFrame(new GameModel()),
                        ClosingInternalGameFrame.class,
                        userOption),
                extractFromOptional(
                        restoreLogFrame(),
                        new ClosingInternalLogFrame(Logger.getLogWindowSource()),
                        ClosingInternalLogFrame.class,
                        userOption)
        );
    }

    private static <T extends JInternalFrameClosing> T extractFromOptional(
            Optional<T> opt, T initValue, Class<T> tClass, int userOption) {
        T result = initValue;
        if (userOption == JOptionPane.YES_OPTION && opt.isPresent()) {
            result = opt.get();
        }
        if (result == initValue) {
            Logger.debug(String.format(NEW_INSTANCE_CREATED, tClass.getName()));
        } else {
            Logger.debug(String.format(INSTANCE_RESTORED, tClass.getName()));
        }
        return result;
    }
}
