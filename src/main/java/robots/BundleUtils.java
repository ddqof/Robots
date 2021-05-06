package robots;

import java.util.ResourceBundle;

public class BundleUtils {
    public static final String MENU_LABELS_BUNDLE_NAME = "MenuLabelsBundle";
    public static final String FRAME_LABELS_BUNDLE_NAME = "FrameLabelsBundle";
    public static final String DIALOGS_BUNDLE_NAME = "DialogsBundle";

    public static String extractValue(String bundleName, String resourceKey) {
        return ResourceBundle.getBundle(bundleName).getString(resourceKey);
    }
}
