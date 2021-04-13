package robots.view.frames;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import robots.model.log.Logger;
import robots.serialize.MySerializable;
import robots.serialize.save.Saves;
import robots.view.internal_frames.ClosingInternalGameFrame;
import robots.view.internal_frames.ClosingInternalLogFrame;
import robots.view.menus.MainApplicationMenuBar;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static robots.serialize.save.Saves.SAVES_PATH;

public class MainApplicationClosingFrame extends JFrameClosing {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private static final String CLOSING_CONFIRM_MESSAGE = "Do you want to exit?";
    private static final String CLOSING_DIALOG_TITLE = "Exit confirmation";
    private static final String SAVES_FOUND_MESSAGE = "Would you like to restore your local saves?";
    private static final String SAVES_FOUND_DIALOG_TITLE = "Saves found";
    private final ClosingInternalGameFrame gameFrame;
    private final ClosingInternalLogFrame logFrame;

    public MainApplicationClosingFrame(String logMessage, Saves saves) {
        super(CLOSING_CONFIRM_MESSAGE, CLOSING_DIALOG_TITLE);
        if (SAVES_PATH.exists() && askForSavesRestore() == JOptionPane.YES_OPTION) {
            Map<Class<?>, Optional<Object>> savedFrames = saves.restore();
            this.gameFrame = (ClosingInternalGameFrame) savedFrames
                    .get(ClosingInternalGameFrame.class)
                    .orElse(new ClosingInternalGameFrame());
            this.logFrame = (ClosingInternalLogFrame) savedFrames
                    .get(ClosingInternalLogFrame.class)
                    .orElse(new ClosingInternalLogFrame());
        } else {
            this.gameFrame = new ClosingInternalGameFrame();
            this.logFrame = new ClosingInternalLogFrame();
        }
        addFrame(gameFrame);
        addFrame(logFrame);
        setContentPane(desktopPane);
        Logger.debug(logMessage);
        setJMenuBar(new MainApplicationMenuBar(this));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void addFrame(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private int askForSavesRestore() {
        return JOptionPane.showConfirmDialog(
                this,
                SAVES_FOUND_MESSAGE,
                SAVES_FOUND_DIALOG_TITLE,
                JOptionPane.YES_NO_OPTION);
    }

    public void storeInternalFramesAtExit() {
        List<MySerializable> objectsToSave = List.of(
                gameFrame, logFrame, gameFrame.getGameModel(), logFrame.getLogSource());
        setActionOnClose(
                () -> {
                    if (!SAVES_PATH.exists()) SAVES_PATH.mkdir();
                    ObjectWriter prettyPrinter = new ObjectMapper().writerWithDefaultPrettyPrinter();
                    objectsToSave.forEach(x -> x.serialize(prettyPrinter));
                }
        );
    }
}
