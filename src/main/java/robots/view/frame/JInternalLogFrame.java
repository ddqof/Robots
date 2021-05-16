package robots.view.frame;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import robots.model.log.LogChangeListener;
import robots.model.log.LogEntry;
import robots.model.log.LogWindowSource;
import robots.serialize.JInternalFrameDeserializer;
import robots.serialize.JInternalFrameSerializer;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static robots.view.frame.JInternalFrameUtils.getEmptyFrame;

@JsonSerialize(using = JInternalFrameSerializer.class)
@JsonDeserialize(using = JInternalFrameDeserializer.class)
public class JInternalLogFrame extends AbstractJInternalFrame implements LogChangeListener {
    private final LogWindowSource logSource;
    private final TextArea logContent;
    public static final File SAVES_FILE = new File(
            Saves.PATH, String.format("logFrame.%s", Saves.JSON_EXTENSION));
    private static final String RESOURCE_KEY = "logFrameTitle";

    private static final int WIDTH = 1030;
    private static final int HEIGHT = 400;
    private static final int X = 10;
    private static final int Y = 10;

    public static JInternalFrame getDefaultEmptyFrame() {
        return getEmptyFrame(WIDTH, HEIGHT, X, Y);
    }

    public JInternalLogFrame(LogWindowSource logSource) {
        this(logSource, getDefaultEmptyFrame());
    }

    public JInternalLogFrame(LogWindowSource logSource, JInternalFrame internalFrame) {
        super(internalFrame, RESOURCE_KEY);
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
    public boolean serialize() {
        return Save.storeObject(SAVES_FILE, this) && logSource.serialize();
    }
}
