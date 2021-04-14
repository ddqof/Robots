package robots.view.internal_frames;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import robots.model.log.LogChangeListener;
import robots.model.log.LogEntry;
import robots.model.log.LogWindowSource;
import robots.model.log.Logger;
import robots.serialize.JInternalFrameDeserializer;
import robots.serialize.JInternalFrameSerializer;
import robots.serialize.MySerializable;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.File;

@JsonSerialize(using = JInternalFrameSerializer.class)
@JsonDeserialize(using = JInternalFrameDeserializer.class)
public class ClosingInternalLogFrame extends JInternalFrameClosing implements LogChangeListener, MySerializable {
    private final LogWindowSource logSource;
    private final TextArea logContent;
    private static final String CLOSING_LOG_WINDOW_TITLE = "Work protocol";
    private static final String CLOSING_CONFIRM_MESSAGE = "Do you want to exit?";
    private static final String CLOSING_DIALOG_TITLE = "Exit log window";
    private static final boolean SET_RESIZABLE_WINDOW = true;
    private static final boolean SET_CLOSABLE_WINDOW = true;
    private static final boolean SET_MAXIMIZABLE_WINDOW = true;
    private static final boolean SET_ICONIFIABLE_WINDOW = true;
    private static final boolean SET_ICON = false;
    public static final File LOG_FRAME_SAVES_FILE = new File(Saves.SAVES_PATH, "logFrame" + Saves.JSON_EXTENSION);

    public static final int DEFAULT_LOG_WINDOW_WIDTH = 1030;
    public static final int DEFAULT_LOG_WINDOW_HEIGHT = 400;
    public static final int DEFAULT_LOG_WINDOW_POS_X = 10;
    public static final int DEFAULT_LOG_WINDOW_POS_Y = 10;

    public LogWindowSource getLogSource() {
        return logSource;
    }

    public static JInternalFrame getDefaultEmptyFrame() {
        JInternalFrame internalFrame = new JInternalFrame();
        internalFrame.setSize(DEFAULT_LOG_WINDOW_WIDTH, DEFAULT_LOG_WINDOW_HEIGHT);
        internalFrame.setLocation(DEFAULT_LOG_WINDOW_POS_X, DEFAULT_LOG_WINDOW_POS_Y);
        return internalFrame;
    }

    public ClosingInternalLogFrame(LogWindowSource logSource, JInternalFrame internalFrame) {
        this(
                logSource,
                internalFrame.getWidth(),
                internalFrame.getHeight(),
                internalFrame.getX(),
                internalFrame.getY(),
                internalFrame.isIcon()
        );
    }

    public ClosingInternalLogFrame(
            LogWindowSource logSource,
            int width,
            int height,
            int x,
            int y,
            boolean isIcon) {
        super(
                CLOSING_LOG_WINDOW_TITLE,
                SET_RESIZABLE_WINDOW,
                SET_CLOSABLE_WINDOW,
                SET_MAXIMIZABLE_WINDOW,
                SET_ICONIFIABLE_WINDOW,
                CLOSING_CONFIRM_MESSAGE,
                CLOSING_DIALOG_TITLE
        );
        setActionOnClose(() -> logSource.unregisterListener(this));
        this.logSource = logSource;
        this.logSource.registerListener(this);
        logContent = new TextArea("");
        logContent.setSize(DEFAULT_LOG_WINDOW_WIDTH, DEFAULT_LOG_WINDOW_HEIGHT);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        updateLogContent();
        setLocation(x, y);
        setSize(width, height);
        try {
            setIcon(isIcon);
        } catch (PropertyVetoException e) {
            Logger.debug(ClosingInternalGameFrame.FAILED_TO_SET_ICON_MSG);
        }
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : logSource.all()) {
            content.append(entry.getMessage()).append("\n");
        }
        logContent.setText(content.toString());
        logContent.invalidate();
    }

    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }

    @Override
    public boolean serialize(ObjectWriter writer) {
        return Save.storeObject(LOG_FRAME_SAVES_FILE, this, writer);
    }
}
