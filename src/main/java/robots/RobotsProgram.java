package robots;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import robots.model.game.GameModel;
import robots.model.game.Robot;
import robots.model.game.Target;
import robots.model.log.Logger;
import robots.view.frames.MainApplicationClosingFrame;
import robots.view.internal_frames.ClosingInternalGameFrame;
import robots.view.internal_frames.ClosingInternalLogFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static robots.serialize.SavesConfig.*;

public class RobotsProgram {
    private static final int LOG_WINDOW_WIDTH = 300;
    private static final int LOG_WINDOW_HEIGHT = 800;
    private static final int LOG_WINDOW_POS_X = 500;
    private static final int LOG_WINDOW_POS_Y = 250;
    private static final double ROBOT_POSITION_X = 50;
    private static final double ROBOT_POSITION_Y = 50;
    private static final double ROBOT_DIRECTION = Math.PI;
    private static final int TARGET_POSITION_X = 50;
    private static final int TARGET_POSITION_Y = 50;
    private static final int GAME_WINDOW_HEIGHT = 200;
    private static final int GAME_WINDOW_WIDTH = 200;
    private static final int GAME_WINDOW_POS_X = 800;
    private static final int GAME_WINDOW_POS_Y = 250;
    private static final boolean SET_LOG_FRAME_AS_ICON = false;
    private static final boolean SET_GAME_FRAME_AS_ICON = false;
    private static final String MAIN_APP_LOG_MESSAGE = "Protocol is working";

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            MainApplicationClosingFrame mainFrame = new MainApplicationClosingFrame(MAIN_APP_LOG_MESSAGE);
            ClosingInternalLogFrame logFrame = new ClosingInternalLogFrame(
                    Logger.getDefaultLogSource(),
                    LOG_WINDOW_WIDTH,
                    LOG_WINDOW_HEIGHT,
                    LOG_WINDOW_POS_X,
                    LOG_WINDOW_POS_Y,
                    SET_LOG_FRAME_AS_ICON
            );
            mainFrame.addFrame(logFrame);
            ClosingInternalGameFrame gameFrame = new ClosingInternalGameFrame(
                    new GameModel(
                            new Robot(ROBOT_POSITION_X, ROBOT_POSITION_Y, ROBOT_DIRECTION),
                            new Target(TARGET_POSITION_X, TARGET_POSITION_Y)
                    ),
                    GAME_WINDOW_POS_X,
                    GAME_WINDOW_POS_Y,
                    GAME_WINDOW_HEIGHT,
                    GAME_WINDOW_WIDTH,
                    SET_GAME_FRAME_AS_ICON
            );
            mainFrame.setActionOnClose(getSerializeAction(gameFrame, logFrame));
            mainFrame.addFrame(gameFrame);
            mainFrame.pack();
            mainFrame.setVisible(true);
            mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
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
