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
            MainApplicationClosingFrame mainFrame = new MainApplicationClosingFrame(
                    MAIN_APP_LOG_MESSAGE,
                    new Saves(List.of(
                            new Save(GAME_FRAME_SAVES_FILE, ClosingInternalGameFrame.class),
                            new Save(LOG_FRAME_SAVES_FILE, ClosingInternalLogFrame.class))
                    )
            );
            mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
            mainFrame.setVisible(true);
            mainFrame.storeInternalFramesAtExit();
        });
    }
}
