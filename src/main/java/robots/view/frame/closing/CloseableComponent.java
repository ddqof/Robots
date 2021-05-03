package robots.view.frame.closing;

import robots.BundleConfig;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Component;
import java.util.Locale;
import java.util.ResourceBundle;

interface CloseableComponent {

    void setDefaultCloseOperation(int op);

    String getTitle();

    default void handleClosing(
            CloseableComponent component,
            Runnable finalization,
            int actionOnCloseSubmit
    ) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                BundleConfig.DIALOGS_BUNDLE_NAME, Locale.getDefault());
        int result = JOptionPane.showConfirmDialog(
                (Component) component,
                String.format(resourceBundle.getString("closingConfirmMessage"), getTitle()),
                String.format(resourceBundle.getString("closingDialogTitle"), getTitle()),
                JOptionPane.YES_NO_OPTION
        );
        if (result == JOptionPane.YES_OPTION) {
            finalization.run();
            component.setDefaultCloseOperation(actionOnCloseSubmit);
        } else {
            component.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
    }
}
