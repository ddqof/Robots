package robots.view.menu.localized;

import robots.BundleUtils;
import robots.locale.LocaleChangeListener;

import javax.swing.*;

public class LocalizedMenuItem extends JMenuItem implements LocaleChangeListener {
    private static final String BUNDLE_NAME = BundleUtils.MENU_LABELS_BUNDLE_NAME;
    private final String resourceKey;

    public LocalizedMenuItem(String resourceKey) {
        super(BundleUtils.extractValue(BUNDLE_NAME, resourceKey));
        this.resourceKey = resourceKey;
    }

    @Override
    public void onLanguageUpdate() {
        setText(BundleUtils.extractValue(BUNDLE_NAME, resourceKey));
        revalidate();
    }
}
