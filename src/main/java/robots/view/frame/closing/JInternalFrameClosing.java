package robots.view.frame.closing;

import robots.BundleUtils;
import robots.locale.LocaleChangeListener;
import robots.serialize.JsonSerializable;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class JInternalFrameClosing extends JInternalFrame implements
        JsonSerializable, CloseableComponent, LocaleChangeListener {
    private final boolean isIcon;
    private Runnable actionOnClose = () -> {
    };
    private static final String BUNDLE_NAME = BundleUtils.FRAME_LABELS_BUNDLE_NAME;
    private final String resourceKey;

    public void setActionOnClose(Runnable action) {
        actionOnClose = action;
    }

    public boolean shouldBeRestoredAsIcon() {
        return isIcon;
    }

    public JInternalFrameClosing(JInternalFrame internalFrame, String resourceKey) {
        this(
                BundleUtils.extractValue(BUNDLE_NAME, resourceKey),
                internalFrame.isIcon(),
                internalFrame.isVisible(),
                internalFrame.getSize(),
                internalFrame.getLocation(),
                resourceKey
        );
    }

    public JInternalFrameClosing(String title, boolean isIcon, boolean isVisible, Dimension size, Point location, String resourceKey) {
        super(title, true, true, true, true);
        setSize(size);
        setLocation(location);
        setVisible(isVisible);
        this.isIcon = isIcon;
        this.resourceKey = resourceKey;
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                handleClosing(JInternalFrameClosing.this, actionOnClose, JFrame.HIDE_ON_CLOSE);
            }
        });
    }


    @Override
    public void onLanguageUpdate() {
        setTitle(BundleUtils.extractValue(BUNDLE_NAME, resourceKey));
        revalidate();
    }
}
