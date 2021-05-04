package robots.view.frame.closing;

import robots.locale.LocaleChangeListener;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class JFrameClosing extends JFrame implements CloseableComponent, LocaleChangeListener {
    private Runnable actionOnClose = () -> {
    };

    public void setActionOnClose(Runnable actionOnClose) {
        this.actionOnClose = actionOnClose;
    }

    public JFrameClosing(String title) {
        super(title);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JFrameClosing.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                handleClosing(JFrameClosing.this, actionOnClose, JFrame.EXIT_ON_CLOSE);
            }
        });
    }
}
