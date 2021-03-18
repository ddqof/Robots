package robots;

//import robots.controller.Controller;
import robots.view.MainApplicationClosingFrame;

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
            MainApplicationClosingFrame closingFrame = new MainApplicationClosingFrame();
            closingFrame.pack();
            closingFrame.setVisible(true);
            closingFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }
}
