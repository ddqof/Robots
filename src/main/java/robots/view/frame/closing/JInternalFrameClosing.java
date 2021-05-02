package robots.view.frame.closing;

import robots.serialize.JsonSerializable;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class JInternalFrameClosing extends JInternalFrame implements JsonSerializable {
    private final boolean isIcon;
    private Runnable actionOnClose = () -> {
    };

    public boolean shouldBeRestoredAsIcon() {
        return isIcon;
    }

    public JInternalFrameClosing(JInternalFrame internalFrame, String title) {
        this(
                title,
                internalFrame.isIcon(),
                internalFrame.isVisible(),
                internalFrame.getSize(),
                internalFrame.getLocation()
        );
    }

    public JInternalFrameClosing(String title, boolean isIcon, boolean isVisible, Dimension size, Point location) {
        super(title, true, true, true, true);
        setSize(size);
        setLocation(location);
        setVisible(isVisible);
        this.isIcon = isIcon;
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                ResourceBundle bundle = ResourceBundle.getBundle("robots.bundle.frame.FrameLabelsBundle", Locale.getDefault());
                int result = JOptionPane.showConfirmDialog(
                        JInternalFrameClosing.this,
                        String.format(bundle.getString("closingConfirmMessage"), title),
                        String.format(bundle.getString("closingDialogTitle"), title),
                        JOptionPane.YES_NO_OPTION
                );
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
