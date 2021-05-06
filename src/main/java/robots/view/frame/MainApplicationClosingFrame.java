package robots.view.frame;

import robots.model.log.Logger;
import robots.serialize.JsonSerializable;
import robots.serialize.JsonSerializableLocale;
import robots.view.menu.MainApplicationMenuBar;

import javax.swing.*;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static robots.serialize.save.Saves.PATH;

public class MainApplicationClosingFrame extends AbstractJFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final List<JsonSerializable> attachedFrames = new ArrayList<>();
    private static final String MAIN_FRAME_CREATED = "Main window launched";
    private static final String RESOURCE_KEY = "programTitle";

    public MainApplicationClosingFrame() {
        super(RESOURCE_KEY);
        setContentPane(desktopPane);
        setJMenuBar(new MainApplicationMenuBar(this));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Logger.debug(MAIN_FRAME_CREATED);
    }

    public <T extends AbstractJInternalFrame> void addFrame(T frame) {
        desktopPane.add(frame);
        attachedFrames.add(frame);
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

    public void dumpAtClose() {
        setActionOnClose(
                () -> {
                    if (!PATH.exists()) {
                        PATH.mkdir();
                    }
                    attachedFrames.forEach(JsonSerializable::serialize);
                    new JsonSerializableLocale(Locale.getDefault()).serialize();
                }
        );
    }
}
