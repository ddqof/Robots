package robots;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import robots.view.frames.MainApplicationClosingFrame;
import robots.view.internal_frames.ClosingInternalGameFrame;
import robots.view.internal_frames.ClosingInternalLogFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static robots.serialize.SavesConfig.*;

public class RobotsProgram {
    private static final String MAIN_APP_LOG_MESSAGE = "Protocol is working";

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            MainApplicationClosingFrame mainFrame = new MainApplicationClosingFrame(MAIN_APP_LOG_MESSAGE);
            mainFrame.setVisible(true);
            mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
            ClosingInternalGameFrame gameFrame = ClosingInternalGameFrame.getDefaultInstance();
            ClosingInternalLogFrame logFrame = ClosingInternalLogFrame.getDefaultInstance();
            if (SAVES_PATH.exists()) {
                try {
                    if (mainFrame.askForSavesRestore() == JOptionPane.YES_OPTION) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        gameFrame = objectMapper.readValue(GAME_FRAME_SAVES_FILE, ClosingInternalGameFrame.class);
                        logFrame = objectMapper.readValue(LOG_FRAME_SAVES_FILE, ClosingInternalLogFrame.class);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mainFrame.addFrame(logFrame);
            mainFrame.addFrame(gameFrame);
            mainFrame.pack();
            mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
            mainFrame.setActionOnClose(getSerializeAction(gameFrame, logFrame));
        });
    }

    private static Runnable getSerializeAction(
            ClosingInternalGameFrame gameFrame, ClosingInternalLogFrame logFrame) {
        return () -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                if (!SAVES_PATH.exists()) {
                    SAVES_PATH.mkdir();
                }
                ObjectWriter prettyPrinter = mapper.writerWithDefaultPrettyPrinter();
                prettyPrinter.writeValue(GAME_FRAME_SAVES_FILE, gameFrame);
                prettyPrinter.writeValue(GAME_MODEL_SAVES_FILE, gameFrame.getGameModel());
                prettyPrinter.writeValue(LOG_FRAME_SAVES_FILE, logFrame);
                prettyPrinter.writeValue(LOG_SOURCE_SAVES_FILE, logFrame.getLogSource());
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
