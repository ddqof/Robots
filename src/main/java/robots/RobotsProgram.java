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
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            MainApplicationClosingFrame closingFrame = new MainApplicationClosingFrame("Protocol is working");
            closingFrame.addWindow(
                    new ClosingLogWindow(
                            Logger.getDefaultLogSource(), 300, 800, 10, 10
                    )
            );
            closingFrame.addWindow(
                    new ClosingGameWindow(
                            new GameModel(
                                    new Robot(100, 100, 0),
                                    new Target(150, 100)
                            ), 400, 400
                    )
            );
            closingFrame.pack();
            closingFrame.setVisible(true);
            closingFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }
}
