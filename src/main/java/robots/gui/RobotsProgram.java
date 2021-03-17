package robots.gui;

import java.awt.Frame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class RobotsProgram {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            MainApplicationClosingFrame closingFrame = new MainApplicationClosingFrame(
                    "Do you want to exit?", "Exit confirmation"
            );
            closingFrame.pack();
            closingFrame.setVisible(true);
            closingFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }
}
