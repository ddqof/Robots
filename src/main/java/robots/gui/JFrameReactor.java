package robots.gui;

import javax.swing.*;

class JFrameReactor extends JFrame{

    public JFrameReactor() {
        super();
    }

    public void reactToWindowClosing(String confirmMessage, String confirmTitle) {
        int result = JOptionPane.showConfirmDialog(this,
                confirmMessage, confirmTitle, JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        else if (result == JOptionPane.NO_OPTION) {
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
    }
}
