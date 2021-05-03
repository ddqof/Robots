package robots.view.menubar.menu.exit;

import com.google.common.eventbus.Subscribe;
import robots.BundleConfig;
import robots.EventBusHolder;
import robots.view.frame.closing.MainApplicationClosingFrame;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class ExitMenu extends JMenu {
    public static String TITLE = "Exit";

    public ExitMenu(MainApplicationClosingFrame frame, int alias) {
        super(TITLE);
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
        EventBusHolder.get().register(this);
    }

    @Subscribe
    private void onLanguageUpdate(ActionEvent e) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                BundleConfig.MENU_LABELS_BUNDLE_NAME, Locale.getDefault()
        );
        setText(resourceBundle.getString("exitMenuTitle"));
    }
}
