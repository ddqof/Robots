package robots;

import robots.model.game.GameModel;
import robots.model.log.LogWindowSource;
import robots.model.log.Logger;
import robots.serialize.JsonSerializableLocale;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;
import robots.view.frame.closing.ClosingInternalGameFrame;
import robots.view.frame.closing.ClosingInternalLogFrame;
import robots.view.frame.closing.MainApplicationClosingFrame;
import robots.view.pane.Dialogs;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.Optional;

public class RobotsProgram {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            int userChoiceForRestore = JOptionPane.NO_OPTION;
            Saves saves = new Saves(
                    new Save(ClosingInternalGameFrame.SAVES_FILE, ClosingInternalGameFrame.class),
                    new Save(ClosingInternalLogFrame.SAVES_FILE, ClosingInternalLogFrame.class),
                    new Save(GameModel.SAVES_FILE, GameModel.class),
                    new Save(LogWindowSource.SAVES_FILE, LogWindowSource.class),
                    new Save(JsonSerializableLocale.SAVES_FILE, JsonSerializableLocale.class));
            saves.restoreLocale().ifPresent(Locale::setDefault);
            if (Saves.PATH.exists()) {
                userChoiceForRestore = Dialogs.showRestoreDialog();
            }
            Logger.init(userChoiceForRestore);
            MainApplicationClosingFrame mainFrame = new MainApplicationClosingFrame();
            ClosingInternalGameFrame gameFrame = new ClosingInternalGameFrame(new GameModel());
            ClosingInternalLogFrame logFrame = new ClosingInternalLogFrame(Logger.getLogWindowSource());
            if (userChoiceForRestore == JOptionPane.YES_OPTION) {
                Optional<ClosingInternalGameFrame> optGameFrame = saves.restoreGameFrame();
                if (optGameFrame.isPresent()) {
                    gameFrame = optGameFrame.get();
                }
                Optional<ClosingInternalLogFrame> optLogFrame = saves.restoreLogFrame();
                if (optLogFrame.isPresent()) {
                    logFrame = optLogFrame.get();
                }
            }
            mainFrame.addFrame(gameFrame);
            mainFrame.addFrame(logFrame);
            mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
            mainFrame.setVisible(true);
            mainFrame.dumpAtClose();
        });
    }
}
