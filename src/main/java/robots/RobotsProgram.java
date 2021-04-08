package robots;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javatuples.Pair;
import robots.controller.Saves;
import robots.model.log.LogWindowSource;
import robots.model.log.Logger;
import robots.view.frames.MainApplicationClosingFrame;
import robots.view.internal_frames.ClosingInternalGameFrame;
import robots.view.internal_frames.ClosingInternalLogFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Optional;

import static robots.controller.Saves.LOG_SOURCE_SAVES_FILE;


public class RobotsProgram {
    private static final String MAIN_APP_LOG_MESSAGE = "Protocol is working";

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            try {
                if (LOG_SOURCE_SAVES_FILE.exists()) {
                    Logger.createInstance(new ObjectMapper().readValue(LOG_SOURCE_SAVES_FILE, LogWindowSource.class));
                }
            } catch (IOException e) {
                Logger.createDefaultInstance();
            }
            MainApplicationClosingFrame mainFrame = new MainApplicationClosingFrame(MAIN_APP_LOG_MESSAGE);
            mainFrame.setVisible(true);
            mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
            ClosingInternalGameFrame gameFrame = ClosingInternalGameFrame.getDefaultInstance();
            ClosingInternalLogFrame logFrame = ClosingInternalLogFrame.getDefaultInstance();
            if (Saves.exists()) {
                try {
                    Optional<Pair<ClosingInternalGameFrame, ClosingInternalLogFrame>> optionalSavedFrames = Saves.restore(mainFrame);
                    if (optionalSavedFrames.isPresent()) {
                        gameFrame = optionalSavedFrames.get().getValue0();
                        logFrame = optionalSavedFrames.get().getValue1();
                    }
                } catch (IOException e) {
                    mainFrame.showSavesRestoreFailedDialog();
                }
            }
            mainFrame.addFrame(gameFrame);
            mainFrame.addFrame(logFrame);
            mainFrame.pack();
            mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
            mainFrame.setActionOnClose(Saves.getSaveAction(gameFrame, logFrame));
        });
    }
}
