package robots.view.frames;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import robots.model.log.Logger;
import robots.serialize.MySerializable;
import robots.view.internal_frames.JInternalFrameClosing;
import robots.view.menus.MainApplicationMenuBar;

import javax.swing.*;
import java.beans.PropertyVetoException;
import java.util.List;

import static robots.serialize.save.Saves.PATH;

public class MainApplicationClosingFrame extends JFrameClosing {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private static final String CLOSING_CONFIRM_MESSAGE = "Do you want to exit?";
    private static final String CLOSING_DIALOG_TITLE = "Exit confirmation";
    private static final String MAIN_FRAME_CREATED = "Main window launched";

    public MainApplicationClosingFrame() {
        super(CLOSING_CONFIRM_MESSAGE, CLOSING_DIALOG_TITLE);
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

    public void storeSerializableAtClose(List<MySerializable> objectsToSave) {
        setActionOnClose(
                () -> {
                    if (!PATH.exists()) PATH.mkdir();
                    ObjectWriter prettyPrinter = new ObjectMapper().writerWithDefaultPrettyPrinter();
                    objectsToSave.forEach(x -> x.serialize(prettyPrinter));
                }
        );
    }
}
