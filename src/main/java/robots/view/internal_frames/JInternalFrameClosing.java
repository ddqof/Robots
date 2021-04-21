package robots.view.internal_frames;

import robots.model.log.Logger;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.beans.PropertyVetoException;

public class JInternalFrameClosing extends JInternalFrame {
    public final String FAILED_TO_SET_ICON_MSG = String.format(
            "Failed to set Icon status on %s", this);

    private Runnable actionOnClose = () -> {
    };

    public JInternalFrameClosing(
            JInternalFrame internalFrame,
            String title,
            String closingConfirmMessage,
            String closingDialogTitle) {
        this(
                title,
                internalFrame.isIcon(),
                internalFrame.getSize(),
                internalFrame.getLocation(),
                closingConfirmMessage,
                closingDialogTitle
        );
    }

    public JInternalFrameClosing(
            String title,
            Boolean isIcon,
            Dimension size,
            Point location,
            String closingConfirmMessage,
            String closingDialogTitle) {
        super(title, true, true, true, true);
        setSize(size);
        setLocation(location);
        try {
            setIcon(isIcon);
        } catch (PropertyVetoException e) {
            Logger.error(FAILED_TO_SET_ICON_MSG);
        }
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
