package robots.view.pane;

import robots.BundleUtils;
import robots.view.frame.closing.CloseableComponent;

import javax.swing.*;
import java.awt.*;

public class Dialogs {
    private static final String YES_BUTTON_RESOURCE_KEY = "yesButtonText";
    private static final String NO_BUTTON_RESOURCE_KEY = "noButtonText";
    private static final String CLOSING_CONFIRM_MESSAGE_RESOURCE_KEY = "closingConfirmMessage";
    private static final String CLOSING_DIALOG_TITLE_RESOURCE_KEY = "closingDialogTitle";
    private static final String SAVES_FOUND_MESSAGE_RESOURCE_KEY = "savesFoundMessage";
    private static final String SAVES_FOUND_TITLE_RESOURCE_KEY = "savesFoundTitle";
    private static final String BUNDLE_NAME = BundleUtils.DIALOGS_BUNDLE_NAME;

    public static int showRestoreDialog() {
        return show(
                null,
                BundleUtils.extractValue(BUNDLE_NAME, SAVES_FOUND_MESSAGE_RESOURCE_KEY),
                BundleUtils.extractValue(BUNDLE_NAME, SAVES_FOUND_TITLE_RESOURCE_KEY));
    }

    public static int showCloseDialog(CloseableComponent component) {
        return show(
                (Component) component,
                String.format(BundleUtils.extractValue(
                        BUNDLE_NAME, CLOSING_CONFIRM_MESSAGE_RESOURCE_KEY),
                        component.getTitle()),
                String.format(BundleUtils.extractValue(
                        BUNDLE_NAME, CLOSING_DIALOG_TITLE_RESOURCE_KEY),
                        component.getTitle())
        );
    }

    private static int show(Component parentComponent, String message, String title) {
        String[] options = {
                BundleUtils.extractValue(BUNDLE_NAME, YES_BUTTON_RESOURCE_KEY),
                BundleUtils.extractValue(BUNDLE_NAME, NO_BUTTON_RESOURCE_KEY)
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
