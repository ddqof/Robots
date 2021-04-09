package robots.view.internal_frames;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class JInternalFrameClosing extends JInternalFrame {

    public static final String ICON_FIELD_NAME = "isIcon";
    public static final String X_POS_FIELD_NAME = "positionX";
    public static final String Y_POS_FIELD_NAME = "positionY";
    public static final String WIDTH_FIELD_NAME = "width";
    public static final String HEIGHT_FIELD_NAME = "height";

    private Runnable actionOnClose = () -> {
    };

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
                    actionOnClose.run();
                } else {
                    JInternalFrameClosing.this.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    public void setActionOnClose(Runnable action) {
        actionOnClose = action;
    }
}
