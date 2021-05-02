package robots.view.frame;

import robots.model.log.Logger;

import javax.swing.*;
import java.beans.PropertyVetoException;

public class JInternalFrameUtils {

    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 400;
    private static final int DEFAULT_X = 50;
    private static final int DEFAULT_Y = 50;

    public static JInternalFrame getDefaultEmptyFrame() {
        return getEmptyFrame(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_X, DEFAULT_Y);
    }

    public static JInternalFrame getEmptyFrame(boolean isIcon, boolean isVisible, int x, int y, int width, int height) {
        JInternalFrame frame = new JInternalFrame();
        try {
            frame.setIcon(isIcon);
        } catch (PropertyVetoException e) {
            Logger.error(e.getMessage());
        }
        frame.setVisible(isVisible);
        frame.setSize(width, height);
        frame.setLocation(x, y);
        return frame;
    }

    public static JInternalFrame getEmptyFrame(int width, int height, int x, int y) {
        return getEmptyFrame(false, true, x, y, width, height);
    }
}
