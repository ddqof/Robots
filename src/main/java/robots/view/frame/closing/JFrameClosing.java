package robots.view.frame.closing;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JFrameClosing extends JFrame {
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
                int result = JOptionPane.showConfirmDialog(
                        JFrameClosing.this,
                        String.format(ClosingFrameUtils.CLOSING_CONFIRM_MESSAGE, title),
                        String.format(ClosingFrameUtils.CLOSING_DIALOG_TITLE, title),
                        JOptionPane.YES_NO_OPTION
                );
                if (result == JOptionPane.YES_OPTION) {
                    actionOnClose.run();
                    JFrameClosing.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    JFrameClosing.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }
}
