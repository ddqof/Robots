package robots.view.frame.closing;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JFrameClosing extends JFrame implements CloseableFrame {
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
                handleClosing(
                        JFrameClosing.this,
                        title, actionOnClose, JFrame.EXIT_ON_CLOSE);
            }
        });
    }
}
