package robots.view.internal_frames;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;

public class JInternalFrameClosing extends JInternalFrame {
    private final boolean isIcon;
    private Runnable actionOnClose = () -> {
    };

    public boolean shouldBeRestoredAsIcon() {
        return isIcon;
    }

    public JInternalFrameClosing(
            JInternalFrame internalFrame,
            String title,
            String closingConfirmMessage,
            String closingDialogTitle) {
        this(
                title,
                internalFrame.isIcon(),
                internalFrame.isVisible(),
                internalFrame.getSize(),
                internalFrame.getLocation(),
                closingConfirmMessage,
                closingDialogTitle
        );
    }

    public JInternalFrameClosing(
            String title,
            boolean isIcon,
            boolean isVisible,
            Dimension size,
            Point location,
            String closingConfirmMessage,
            String closingDialogTitle) {
        super(title, true, true, true, true);
        setSize(size);
        setLocation(location);
        setVisible(isVisible);
        this.isIcon = isIcon;
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                int result = JOptionPane.showConfirmDialog(JInternalFrameClosing.this,
                        closingConfirmMessage, closingDialogTitle, JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    JInternalFrameClosing.this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
                    actionOnClose.run();
                } else {
                    JInternalFrameClosing.this.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    public void setActionOnClose(Runnable action) {
        actionOnClose = action;
    }
}
