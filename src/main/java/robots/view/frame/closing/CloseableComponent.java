package robots.view.frame.closing;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Component;

interface CloseableComponent {

    void setCloseOperation(int op);

    default void handleClosing(
            CloseableComponent component,
            String frameTitle,
            Runnable finalization,
            int actionOnCloseSubmit
    ) {
        int result = JOptionPane.showConfirmDialog(
                (Component) component,
                String.format("Are you sure you want to exit `%s`", frameTitle),
                String.format("Exit `%s`?", frameTitle),
                JOptionPane.YES_NO_OPTION
        );
        if (result == JOptionPane.YES_OPTION) {
            finalization.run();
            component.setCloseOperation(actionOnCloseSubmit);
        } else {
            component.setCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
    }
}
