package robots.view.menu.localized;

import robots.BundleUtils;
import robots.locale.LocaleChangeListener;

import javax.swing.*;

public class LocalizedMenu extends JMenu implements LocaleChangeListener {
    private final static String BUNDLE_NAME = BundleUtils.MENU_LABELS_BUNDLE_NAME;
    private final String resourceKey;

    public LocalizedMenu(String resourceKey) {
        super(BundleUtils.extractValue(BUNDLE_NAME, resourceKey));
        this.resourceKey = resourceKey;
    }

    @Override
    public void onLanguageUpdate() {
        setText(BundleUtils.extractValue(BUNDLE_NAME, resourceKey));
        revalidate();
    }
}
