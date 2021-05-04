package robots.view.frame.closing;

import robots.locale.LocaleChangeListener;
import robots.serialize.JsonSerializable;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;

public abstract class JInternalFrameClosing extends JInternalFrame implements
        JsonSerializable, CloseableComponent, LocaleChangeListener {
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
                handleClosing(JInternalFrameClosing.this, actionOnClose, JFrame.HIDE_ON_CLOSE);
            }
        });
    }

    public void setActionOnClose(Runnable action) {
        actionOnClose = action;
    }
}