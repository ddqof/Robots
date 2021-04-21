package robots.view.internal_frames;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import robots.model.log.LogChangeListener;
import robots.model.log.LogEntry;
import robots.model.log.LogWindowSource;
import robots.serialize.JInternalFrameDeserializer;
import robots.serialize.JInternalFrameSerializer;
import robots.serialize.MySerializable;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static robots.view.internal_frames.JInternalFrameUtils.getEmptyFrame;

@JsonSerialize(using = JInternalFrameSerializer.class)
@JsonDeserialize(using = JInternalFrameDeserializer.class)
public class ClosingInternalLogFrame extends JInternalFrameClosing implements LogChangeListener, MySerializable {
    private final LogWindowSource logSource;
    private final TextArea logContent;
    private static final String TITLE = "Work protocol";
    private static final String CLOSING_CONFIRM_MESSAGE = "Do you want to exit?";
    private static final String CLOSING_DIALOG_TITLE = "Exit log window";
    public static final File SAVES_FILE = new File(Saves.PATH, "logFrame" + Saves.JSON_EXTENSION);

    public static final int WIDTH = 1030;
    public static final int HEIGHT = 400;
    public static final int X = 10;
    public static final int Y = 10;

    public LogWindowSource getLogSource() {
        return logSource;
    }

    public ClosingInternalLogFrame(LogWindowSource logSource) {
        this(logSource, getEmptyFrame(WIDTH, HEIGHT, X, Y));
    }

    public ClosingInternalLogFrame(LogWindowSource logSource, JInternalFrame internalFrame) {
        super(internalFrame, TITLE, CLOSING_CONFIRM_MESSAGE, CLOSING_DIALOG_TITLE);
        setActionOnClose(() -> logSource.unregisterListener(this));
        this.logSource = logSource;
        this.logSource.registerListener(this);
        logContent = new TextArea("");
        logContent.setSize(WIDTH, HEIGHT);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        updateLogContent();
        pack();
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
        return Save.storeObject(SAVES_FILE, this, writer);
    }
}
