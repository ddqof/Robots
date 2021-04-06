package robots;

import robots.model.game.GameModel;
import robots.model.game.Robot;
import robots.model.game.Target;
import robots.model.log.Logger;
import robots.view.frames.ClosingGameWindow;
import robots.view.frames.ClosingLogWindow;
import robots.view.frames.MainApplicationClosingFrame;

import javax.swing.*;
import java.awt.*;

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
    private static final String MAIN_APP_LOG_MESSAGE = "Protocol is working";

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            MainApplicationClosingFrame closingFrame = new MainApplicationClosingFrame(MAIN_APP_LOG_MESSAGE);
            closingFrame.addWindow(
                    new ClosingLogWindow(
                            Logger.getDefaultLogSource(),
                            LOG_WINDOW_WIDTH,
                            LOG_WINDOW_HEIGHT,
                            LOG_WINDOW_POS_X,
                            LOG_WINDOW_POS_Y
                    )
            );
            closingFrame.addWindow(
                    new ClosingGameWindow(
                            new GameModel(
                                    new Robot(ROBOT_POSITION_X, ROBOT_POSITION_Y, ROBOT_DIRECTION),
                                    new Target(TARGET_POSITION_X, TARGET_POSITION_Y)
                            ),
                            GAME_WINDOW_POS_X,
                            GAME_WINDOW_POS_Y,
                            GAME_WINDOW_HEIGHT,
                            GAME_WINDOW_WIDTH
                    )
            );
            closingFrame.pack();
            closingFrame.setVisible(true);
            closingFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }
}
