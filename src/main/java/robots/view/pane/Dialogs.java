package robots.view.pane;

import robots.BundleUtils;
import robots.view.frame.closing.CloseableComponent;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Dialogs {
    private static final String YES_BUTTON_RESOURCE_KEY = "yesButtonText";
    private static final String NO_BUTTON_RESOURCE_KEY = "noButtonText";
    private static final String CLOSING_CONFIRM_MESSAGE_RESOURCE_KEY = "closingConfirmMessage";
    private static final String CLOSING_DIALOG_TITLE_RESOURCE_KEY = "closingDialogTitle";
    private static final String SAVES_FOUND_MESSAGE_RESOURCE_KEY = "savesFoundMessage";
    private static final String SAVES_FOUND_TITLE_RESOURCE_KEY = "savesFoundTitle";

    public static int showRestoreDialog() {
        ResourceBundle bundle = ResourceBundle.getBundle(
                BundleUtils.DIALOGS_BUNDLE_NAME, Locale.getDefault());
        return show(
                null,
                bundle.getString(SAVES_FOUND_MESSAGE_RESOURCE_KEY),
                bundle.getString(SAVES_FOUND_TITLE_RESOURCE_KEY),
                bundle);
    }

    public static int showCloseDialog(CloseableComponent component) {
        ResourceBundle bundle = ResourceBundle.getBundle(
                BundleUtils.DIALOGS_BUNDLE_NAME, Locale.getDefault());
        return show(
                (Component) component,
                String.format(bundle.getString(CLOSING_CONFIRM_MESSAGE_RESOURCE_KEY), component.getTitle()),
                String.format(bundle.getString(CLOSING_DIALOG_TITLE_RESOURCE_KEY), component.getTitle()),
                bundle
        );
    }

    private static int show(
            Component parentComponent,
            String message,
            String title,
            ResourceBundle bundle
    ) {
        String[] options = {
                bundle.getString(YES_BUTTON_RESOURCE_KEY),
                bundle.getString(NO_BUTTON_RESOURCE_KEY)
        };
        return JOptionPane.showOptionDialog(
                parentComponent,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
    }
}
