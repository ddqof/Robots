package robots.view.frame.closing;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Component;

public interface CloseableFrame {

    default void handleClosing(
            Component component,
            String frameTitle,
            Runnable finalization,
            int actionOnFrameClose
    ) {
        int result = JOptionPane.showConfirmDialog(
                component,
                String.format("Are you sure you want to exit `%s`", frameTitle),
                String.format("Exit `%s`?", frameTitle),
                JOptionPane.YES_NO_OPTION
        );
        if (result == JOptionPane.YES_OPTION) {
            finalization.run();
            if (actionOnFrameClose == JFrame.HIDE_ON_CLOSE) {
                component.setVisible(false);
            } else if (actionOnFrameClose == JFrameClosing.EXIT_ON_CLOSE) {
                component.setEnabled(false);
            }
        }
    }
}
