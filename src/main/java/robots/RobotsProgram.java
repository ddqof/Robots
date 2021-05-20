package robots;

import robots.model.game.GameModel;
import robots.model.log.LogWindowSource;
import robots.model.log.Logger;
import robots.view.frame.JInternalGameFrame;
import robots.view.frame.JInternalLogFrame;
import robots.view.frame.JInternalRobotInfoFrame;
import robots.view.frame.MainApplicationClosingFrame;

import javax.swing.*;
import java.awt.*;

public class RobotsProgram {
    public static void main(String[] args) throws InterruptedException {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainApplicationClosingFrame mainFrame = new MainApplicationClosingFrame();
        GameModel gameModel = new GameModel();
        mainFrame.addFrame(new JInternalGameFrame(gameModel));
        mainFrame.addFrame(new JInternalLogFrame(Logger.getLogWindowSource()));
        mainFrame.addFrame(new JInternalRobotInfoFrame(gameModel));
        EventQueue.invokeLater(() -> {
            mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
            mainFrame.setVisible(true);
        });
        mainFrame.dumpAtClose();
        gameModel.start();
    }
}
