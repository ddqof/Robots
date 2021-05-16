package robots;

import robots.model.game.GameModel;
import robots.model.log.LogWindowSource;
import robots.model.log.Logger;
import robots.serialize.JsonSerializableLocale;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;
import robots.view.frame.JInternalGameFrame;
import robots.view.frame.JInternalLogFrame;
import robots.view.frame.JInternalRobotCoordsFrame;
import robots.view.frame.MainApplicationClosingFrame;
import robots.view.dialog.Dialogs;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class RobotsProgram {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int userChoiceForRestore = Saves.PATH.exists() ? Dialogs.showRestoreDialog() : JOptionPane.NO_OPTION;
        Saves saves = new Saves(
                userChoiceForRestore,
                new Save(JInternalGameFrame.SAVES_FILE, JInternalGameFrame.class),
                new Save(JInternalLogFrame.SAVES_FILE, JInternalLogFrame.class),
                new Save(GameModel.SAVES_FILE, GameModel.class),
                new Save(LogWindowSource.SAVES_FILE, LogWindowSource.class),
                new Save(JsonSerializableLocale.SAVES_FILE, JsonSerializableLocale.class),
                new Save(JInternalRobotCoordsFrame.SAVES_FILE, JInternalRobotCoordsFrame.class)
        );
        saves.restoreLocale().ifPresent(Locale::setDefault);
        MainApplicationClosingFrame mainFrame = new MainApplicationClosingFrame();
        GameModel gameModel = saves.restoreGameModel();
        JInternalGameFrame gameFrame = new JInternalGameFrame(gameModel, saves.restoreGameEmptyFrame());
        JInternalLogFrame logFrame = new JInternalLogFrame(Logger.getLogWindowSource(), saves.restoreLogEmptyFrame());
        JInternalRobotCoordsFrame infoFrame = new JInternalRobotCoordsFrame(saves.restoreCoordsFrame(), gameModel);
        mainFrame.addFrame(gameFrame);
        mainFrame.addFrame(logFrame);
        mainFrame.addFrame(infoFrame);
        EventQueue.invokeLater(() -> {
            mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
            mainFrame.setVisible(true);
        });
        mainFrame.dumpAtClose();
        gameModel.start();
    }
}
