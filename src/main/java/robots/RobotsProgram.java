package robots;

import org.javatuples.Pair;
import robots.model.game.GameModel;
import robots.model.log.LogWindowSource;
import robots.model.log.Logger;
import robots.serialize.JsonSerializableLocale;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;
import robots.view.frame.JInternalGameFrame;
import robots.view.frame.JInternalLogFrame;
import robots.view.frame.MainApplicationClosingFrame;
import robots.view.pane.Dialogs;

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
        SwingUtilities.invokeLater(() -> {
            int userChoiceForRestore = JOptionPane.NO_OPTION;
            Saves saves = new Saves(
                    new Save(JInternalGameFrame.SAVES_FILE, JInternalGameFrame.class),
                    new Save(JInternalLogFrame.SAVES_FILE, JInternalLogFrame.class),
                    new Save(GameModel.SAVES_FILE, GameModel.class),
                    new Save(LogWindowSource.SAVES_FILE, LogWindowSource.class),
                    new Save(JsonSerializableLocale.SAVES_FILE, JsonSerializableLocale.class));
            saves.restoreLocale().ifPresent(Locale::setDefault);
            if (Saves.PATH.exists()) {
                userChoiceForRestore = Dialogs.showRestoreDialog();
            }
            Logger.restore(userChoiceForRestore);
            MainApplicationClosingFrame mainFrame = new MainApplicationClosingFrame();
            Pair<JInternalGameFrame, JInternalLogFrame> restored =
                    saves.restoreInternalFrames(userChoiceForRestore);
            mainFrame.addFrame(restored.getValue0());
            mainFrame.addFrame(restored.getValue1());
            mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
            mainFrame.setVisible(true);
            mainFrame.dumpAtClose();
        });
    }
}
