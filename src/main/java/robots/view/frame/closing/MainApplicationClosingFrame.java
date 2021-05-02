package robots.view.frame.closing;

import robots.model.log.Logger;
import robots.serialize.JsonSerializable;
import robots.view.menubar.MainApplicationMenuBar;

import javax.swing.*;
import java.beans.PropertyVetoException;
import java.util.List;

import static robots.serialize.save.Saves.PATH;

public class MainApplicationClosingFrame extends JFrameClosing {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private static final String MAIN_FRAME_CREATED = "Main window launched";
    private static final String TITLE = "Robots Program";

    public MainApplicationClosingFrame() {
        super(TITLE);
        setContentPane(desktopPane);
        setJMenuBar(new MainApplicationMenuBar(this));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Logger.debug(MAIN_FRAME_CREATED);
    }

    public <T extends JInternalFrameClosing> void addFrame(T frame) {
        desktopPane.add(frame);
        if (frame.shouldBeRestoredAsIcon()) {
            try {
                frame.setIcon(true);
            } catch (PropertyVetoException e) {
                Logger.error(getFailedToSetIconMsg(frame.getClass()));
            }
        }
    }

    private String getFailedToSetIconMsg(Class<?> targetClass) {
        return String.format("Failed to set Icon status on %s", targetClass.getName());
    }

    public void storeSerializableAtClose(List<JsonSerializable> objectsToSave) {
        setActionOnClose(
                () -> {
                    if (!PATH.exists()) PATH.mkdir();
                    objectsToSave.forEach(JsonSerializable::serialize);
                }
        );
    }
}
