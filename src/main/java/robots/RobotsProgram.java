package robots;

import org.javatuples.Pair;
import robots.controller.Saves;
import robots.model.log.Logger;
import robots.view.frames.MainApplicationClosingFrame;
import robots.view.internal_frames.ClosingInternalGameFrame;
import robots.view.internal_frames.ClosingInternalLogFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;

public class RobotsProgram {
    private static final String MAIN_APP_LOG_MESSAGE = "Protocol is working";

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            Logger.init();
            MainApplicationClosingFrame mainFrame = new MainApplicationClosingFrame(MAIN_APP_LOG_MESSAGE);
            mainFrame.setVisible(true);
            mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
            Saves saves = new Saves(mainFrame);
            Pair<Optional<ClosingInternalGameFrame>, Optional<ClosingInternalLogFrame>> savedFrames = saves.restore();
            ClosingInternalGameFrame gameFrame = savedFrames.getValue0().orElse(new ClosingInternalGameFrame());
            ClosingInternalLogFrame logFrame = savedFrames.getValue1().orElse(new ClosingInternalLogFrame());
            mainFrame.addFrame(gameFrame);
            mainFrame.addFrame(logFrame);
            mainFrame.pack();
            mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
            saves.storeAtExit(List.of(gameFrame, gameFrame.getGameModel(), logFrame, logFrame.getLogSource()));
        });
    }
}
