package robots.view.frames;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class JInternalFrameClosing extends JInternalFrame {

    public JInternalFrameClosing(
            String title,
            Boolean resizable,
            Boolean closable,
            Boolean maximizable,
            Boolean iconifiable,
            String closingConfirmMessage,
            String closingDialogTitle) {
        super(title, resizable, closable, maximizable, iconifiable);
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                int result = JOptionPane.showConfirmDialog(JInternalFrameClosing.this,
                        closingConfirmMessage, closingDialogTitle, JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    JInternalFrameClosing.this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
                } else {
                    JInternalFrameClosing.this.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }
}
