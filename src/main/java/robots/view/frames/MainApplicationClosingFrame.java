package robots.view.frames;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import robots.model.log.Logger;
import robots.serialize.MySerializable;
import robots.view.menus.MainApplicationMenuBar;

import javax.swing.*;
import java.util.List;

import static robots.serialize.save.Saves.SAVES_PATH;

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

    public void addFrame(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    public void storeSerializableAtClose(List<MySerializable> objectsToSave) {
        setActionOnClose(
                () -> {
                    if (!SAVES_PATH.exists()) SAVES_PATH.mkdir();
                    ObjectWriter prettyPrinter = new ObjectMapper().writerWithDefaultPrettyPrinter();
                    objectsToSave.forEach(x -> x.serialize(prettyPrinter));
                }
        );
    }
}
