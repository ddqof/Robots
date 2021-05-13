package robots.view.frame;

import robots.view.pane.Dialogs;

import javax.swing.*;

public interface CloseableComponent {

    void setDefaultCloseOperation(int op);

    String getTitle();

    default void handleClosing(
            CloseableComponent component,
            Runnable finalization,
            int actionOnCloseSubmit
    ) {
        if (Dialogs.showCloseDialog(component) == JOptionPane.YES_OPTION) {
            finalization.run();
            component.setDefaultCloseOperation(actionOnCloseSubmit);
        } else {
            component.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
    }
}
