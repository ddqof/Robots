package robots.view.pane;

import javax.swing.*;

public class RestoreDialog {
    private static final String SAVES_FOUND_MESSAGE = "Would you like to restore your local saves?";
    private static final String SAVES_FOUND_DIALOG_TITLE = "Saves found";

    public static int show() {
        return JOptionPane.showConfirmDialog(
                null,
                SAVES_FOUND_MESSAGE,
                SAVES_FOUND_DIALOG_TITLE,
                JOptionPane.YES_NO_OPTION);
    }
}
