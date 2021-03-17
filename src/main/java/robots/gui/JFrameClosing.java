package robots.gui;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class JFrameClosing extends JFrame {

    public JFrameClosing(String closingConfirmMessage, String closingDialogTitle) {
        super();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(JFrameClosing.this,
                        closingConfirmMessage, closingDialogTitle, JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    JFrameClosing.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    JFrameClosing.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }
}
