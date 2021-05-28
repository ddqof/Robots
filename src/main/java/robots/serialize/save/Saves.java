package robots.serialize.save;

import robots.model.game.GameModel;
import robots.serialize.JsonSerializableLocale;
import robots.view.frame.JInternalGameFrame;
import robots.view.frame.JInternalLogFrame;
import robots.view.frame.JInternalRobotInfoFrame;
//import robots.view.frame.JInternalRobotInfoFrame;

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

    public GameModel restoreGameModel() {
        if (userChoice == JOptionPane.YES_OPTION) {
            return (GameModel) retrievedSaves
                    .get(GameModel.class)
                    .orElse(new GameModel());
        } else {
            return new GameModel();
        }
    }

    public JInternalFrame restoreGameEmptyFrame() {
        if (userChoice == JOptionPane.YES_OPTION) {
            return (JInternalFrame) retrievedSaves
                    .get(JInternalGameFrame.class)
                    .orElse(JInternalGameFrame.getDefaultFrame());
        } else {
            return JInternalGameFrame.getDefaultFrame();
        }
    }

    public JInternalFrame restoreLogEmptyFrame() {
        if (userChoice == JOptionPane.YES_OPTION) {
            return (JInternalFrame) retrievedSaves
                    .get(JInternalLogFrame.class)
                    .orElse(JInternalLogFrame.getDefaultEmptyFrame());
        } else {
            return JInternalLogFrame.getDefaultEmptyFrame();
        }
    }

    public JInternalFrame restoreCoordsFrame() {
        if (userChoice == JOptionPane.YES_OPTION) {
            return (JInternalFrame) retrievedSaves
                    .get(JInternalRobotInfoFrame.class)
                    .orElse(JInternalRobotInfoFrame.getDefaultEmptyFrame());
        } else {
            return JInternalRobotInfoFrame.getDefaultEmptyFrame();
        }
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
