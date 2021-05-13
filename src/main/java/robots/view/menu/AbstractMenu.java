package robots.view.menu;

import robots.BundleUtils;
import robots.locale.LocaleChangeListener;
import robots.locale.LocaleListenersHolder;

import javax.swing.*;

public abstract class AbstractMenu extends JMenu implements LocaleChangeListener {
    private final static String BUNDLE_NAME = BundleUtils.MENU_LABELS_BUNDLE_NAME;
    private final String resourceKey;

    public AbstractMenu(String resourceKey) {
        super(BundleUtils.extractValue(BUNDLE_NAME, resourceKey));
        this.resourceKey = resourceKey;
        LocaleListenersHolder.register(this);
    }

    @Override
    public void onLanguageUpdate() {
        setText(BundleUtils.extractValue(BUNDLE_NAME, resourceKey));
        revalidate();
    }
}
