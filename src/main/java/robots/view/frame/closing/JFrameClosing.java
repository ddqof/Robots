package robots.view.frame.closing;

import robots.BundleUtils;
import robots.locale.LocaleChangeListener;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class JFrameClosing extends JFrame implements CloseableComponent, LocaleChangeListener {
    private Runnable actionOnClose = () -> {
    };
    private static final String BUNDLE_NAME = BundleUtils.FRAME_LABELS_BUNDLE_NAME;
    private final String resourceKey;

    public void setActionOnClose(Runnable actionOnClose) {
        this.actionOnClose = actionOnClose;
    }

    public JFrameClosing(String resourceKey) {
        super(BundleUtils.extractValue(BUNDLE_NAME, resourceKey));
        this.resourceKey = resourceKey;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JFrameClosing.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                handleClosing(JFrameClosing.this, actionOnClose, JFrame.EXIT_ON_CLOSE);
            }
        });
    }

    @Override
    public void onLanguageUpdate() {
        setTitle(BundleUtils.extractValue(BUNDLE_NAME, resourceKey));
    }
}
