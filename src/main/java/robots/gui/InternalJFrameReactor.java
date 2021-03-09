package robots.gui;

import javax.swing.*;

class InternalJFrameReactor extends JInternalFrame {

    public InternalJFrameReactor(
            String title, Boolean resizable, Boolean closable, Boolean maximizable, Boolean iconifiable) {
        super(title, resizable, closable, maximizable, iconifiable);
    }

    public void reactToInternalFrameClosing(String confirmMessage, String confirmTitle) {
        int result = JOptionPane.showConfirmDialog(this,
                confirmMessage, confirmTitle, JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        } else if (result == JOptionPane.NO_OPTION) {
            this.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
        }
    }
}
