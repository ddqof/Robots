package robots.view.menu;

import robots.BundleConfig;
import robots.locale.LocaleChangeListener;
import robots.locale.LocaleListenersHolder;
import robots.view.frame.closing.MainApplicationClosingFrame;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

class ExitMenu extends JMenu implements LocaleChangeListener {
    private static final String RESOURCE_KEY = "exitMenuTitle";

    public ExitMenu(MainApplicationClosingFrame frame, ResourceBundle bundle, int alias) {
        super(bundle.getString(RESOURCE_KEY));
        setMnemonic(alias);
        addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                frame.dispatchEvent(
                        new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)
                );
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });
        LocaleListenersHolder.register(this);
    }

    @Override
    public void onLanguageUpdate() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                BundleConfig.MENU_LABELS_BUNDLE_NAME, Locale.getDefault()
        );
        setText(resourceBundle.getString(RESOURCE_KEY));
    }
}
