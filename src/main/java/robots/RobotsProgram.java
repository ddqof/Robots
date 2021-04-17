package robots;

import org.javatuples.Pair;
import robots.model.game.GameModel;
import robots.model.log.LogWindowSource;
import robots.model.log.Logger;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;
import robots.view.frames.MainApplicationClosingFrame;
import robots.view.internal_frames.ClosingInternalGameFrame;
import robots.view.internal_frames.ClosingInternalLogFrame;
import robots.view.panes.RestoreDialog;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RobotsProgram {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            int userChoiceForRestore = JOptionPane.NO_OPTION;
            if (Saves.SAVES_PATH.exists()) {
                userChoiceForRestore = RestoreDialog.show();
            }
            Logger.init(userChoiceForRestore);
            Saves saves = new Saves(
                    List.of(
                            new Save(ClosingInternalGameFrame.GAME_FRAME_SAVES_FILE, ClosingInternalGameFrame.class),
                            new Save(ClosingInternalLogFrame.LOG_FRAME_SAVES_FILE, ClosingInternalLogFrame.class),
                            new Save(GameModel.GAME_MODEL_SAVES_FILE, GameModel.class),
                            new Save(LogWindowSource.LOG_SOURCE_SAVES_FILE, LogWindowSource.class)
                    )
            );
            MainApplicationClosingFrame mainFrame = new MainApplicationClosingFrame();
            Pair<ClosingInternalGameFrame, ClosingInternalLogFrame> restored = saves.restoreOrGetDefaultValues(userChoiceForRestore);
            ClosingInternalGameFrame gameFrame = restored.getValue0();
            ClosingInternalLogFrame logFrame = restored.getValue1();
            mainFrame.addFrame(gameFrame);
            mainFrame.addFrame(logFrame);
            mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
            mainFrame.setVisible(true);
            mainFrame.storeSerializableAtClose(List.of(gameFrame, logFrame, gameFrame.getGameModel(), logFrame.getLogSource()));
        });
    }
}
