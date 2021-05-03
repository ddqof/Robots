package robots.view.pane;

import robots.BundleConfig;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class RestoreDialog {
    public static int show() {
        ResourceBundle dialogsBundle = ResourceBundle.getBundle(
                BundleConfig.DIALOGS_BUNDLE_NAME, Locale.getDefault());
        return JOptionPane.showConfirmDialog(
                null,
                dialogsBundle.getString("savesFoundMessage"),
                dialogsBundle.getString("savesFoundTitle"),
                JOptionPane.YES_NO_OPTION);
    }
}
