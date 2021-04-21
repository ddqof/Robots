package robots.view.internal_frames;

import javax.swing.*;

public class JInternalFrameUtils {

    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 400;
    private static final int DEFAULT_X = 50;
    private static final int DEFAULT_Y = 50;

    public static JInternalFrame getDefaultEmptyFrame() {
        return getEmptyFrame(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_X, DEFAULT_Y);
    }

    public static JInternalFrame getEmptyFrame(int width, int height, int x, int y) {
        JInternalFrame frame = new JInternalFrame();
        frame.setSize(width, height);
        frame.setLocation(x, y);
        return frame;
    }
}
