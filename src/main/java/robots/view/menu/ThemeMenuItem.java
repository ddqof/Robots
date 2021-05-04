package robots.view.menu;

import robots.BundleConfig;
import robots.locale.LocaleChangeListener;
import robots.locale.LocaleListenersHolder;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

class ThemeMenuItem extends JMenuItem implements LocaleChangeListener {
    private final String resourceKey;

    public ThemeMenuItem(ResourceBundle bundle, String resourceKey, String systemLookAndFeelClassName) {
        super(bundle.getString(resourceKey));
        this.resourceKey = resourceKey;
        addActionListener((event) -> {
            try {
                UIManager.setLookAndFeel(systemLookAndFeelClassName);
                SwingUtilities.updateComponentTreeUI(this);
            } catch (ClassNotFoundException | InstantiationException
                    | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
            }
            this.invalidate();
        });
        LocaleListenersHolder.register(this);
    }

    @Override
    public void onLanguageUpdate() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                BundleConfig.MENU_LABELS_BUNDLE_NAME, Locale.getDefault()
        );
        setText(resourceBundle.getString(resourceKey));
    }
}
