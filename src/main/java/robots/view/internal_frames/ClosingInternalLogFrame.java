package robots.view.internal_frames;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import robots.controller.serialize.ClosingInternalLogFrameDeserializer;
import robots.controller.serialize.JInternalFrameSerializer;
import robots.model.log.LogChangeListener;
import robots.model.log.LogEntry;
import robots.model.log.LogWindowSource;
import robots.model.log.Logger;

import javax.swing.*;
import java.awt.*;

@JsonDeserialize(using = ClosingInternalLogFrameDeserializer.class)
@JsonSerialize(using = JInternalFrameSerializer.class)
public class ClosingInternalLogFrame extends JInternalFrameClosing implements LogChangeListener {
    private final LogWindowSource logSource;
    private final TextArea logContent;
    private static final String CLOSING_LOG_WINDOW_TITLE = "Work protocol";
    private static final String CLOSING_CONFIRM_MESSAGE = "Do you want to exit?";
    private static final String CLOSING_DIALOG_TITLE = "Exit log window";
    private static final boolean SET_RESIZABLE_WINDOW = true;
    private static final boolean SET_CLOSABLE_WINDOW = true;
    private static final boolean SET_MAXIMIZABLE_WINDOW = true;
    private static final boolean SET_ICONIFIABLE_WINDOW = true;

    private static final int DEFAULT_LOG_WINDOW_WIDTH = 300;
    private static final int DEFAULT_LOG_WINDOW_HEIGHT = 400;
    private static final int DEFAULT_LOG_WINDOW_POS_X = 500;
    private static final int DEFAULT_LOG_WINDOW_POS_Y = 250;

    public static ClosingInternalLogFrame getDefaultInstance() {
        return new ClosingInternalLogFrame(
                Logger.getLogWindowSource(),
                DEFAULT_LOG_WINDOW_WIDTH,
                DEFAULT_LOG_WINDOW_HEIGHT,
                DEFAULT_LOG_WINDOW_POS_X,
                DEFAULT_LOG_WINDOW_POS_Y
        );
    }

    public LogWindowSource getLogSource() {
        return logSource;
    }

    public ClosingInternalLogFrame(
            LogWindowSource logSource,
            int width,
            int height,
            int x,
            int y) {
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
}
