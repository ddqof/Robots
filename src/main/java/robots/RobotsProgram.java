package robots;

import robots.model.log.Logger;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;
import robots.view.frames.MainApplicationClosingFrame;
import robots.view.internal_frames.ClosingInternalGameFrame;
import robots.view.internal_frames.ClosingInternalLogFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;

import static robots.view.internal_frames.ClosingInternalGameFrame.GAME_FRAME_SAVES_FILE;
import static robots.view.internal_frames.ClosingInternalLogFrame.LOG_FRAME_SAVES_FILE;

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
            Saves saves = new Saves(
                    mainFrame,
                    List.of(new Save(GAME_FRAME_SAVES_FILE, ClosingInternalGameFrame.class),
                            new Save(LOG_FRAME_SAVES_FILE, ClosingInternalLogFrame.class))
            );
            List<Optional<Object>> savedFrames = saves.restore();
            ClosingInternalGameFrame gameFrame = (ClosingInternalGameFrame) savedFrames
                    .get(0)
                    .orElse(new ClosingInternalGameFrame());
            ClosingInternalLogFrame logFrame = (ClosingInternalLogFrame) savedFrames
                    .get(1)
                    .orElse(new ClosingInternalLogFrame());
            mainFrame.addFrame(gameFrame);
            mainFrame.addFrame(logFrame);
            mainFrame.pack();
            mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
            saves.storeAtExit(List.of(gameFrame, gameFrame.getGameModel(), logFrame, logFrame.getLogSource()));
        });
    }
}
