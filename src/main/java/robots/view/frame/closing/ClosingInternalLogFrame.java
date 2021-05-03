package robots.view.frame.closing;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.eventbus.Subscribe;
import robots.BundleConfig;
import robots.EventBusHolder;
import robots.model.log.LogChangeListener;
import robots.model.log.LogEntry;
import robots.model.log.LogWindowSource;
import robots.serialize.JInternalFrameDeserializer;
import robots.serialize.JInternalFrameSerializer;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

import static robots.view.frame.JInternalFrameUtils.getEmptyFrame;

@JsonSerialize(using = JInternalFrameSerializer.class)
@JsonDeserialize(using = JInternalFrameDeserializer.class)
public class ClosingInternalLogFrame extends JInternalFrameClosing implements LogChangeListener {
    private final LogWindowSource logSource;
    private final TextArea logContent;
    public static final File SAVES_FILE = new File(
            Saves.PATH, String.format("logFrame.%s", Saves.JSON_EXTENSION));
    private static final String RESOURCE_KEY = "logFrameTitle";

    public static final int WIDTH = 1030;
    public static final int HEIGHT = 400;
    public static final int X = 10;
    public static final int Y = 10;

    public ClosingInternalLogFrame(LogWindowSource logSource) {
        this(logSource, getEmptyFrame(WIDTH, HEIGHT, X, Y));
    }

    public ClosingInternalLogFrame(LogWindowSource logSource, JInternalFrame internalFrame) {
        super(internalFrame,
                ResourceBundle.getBundle(
                        BundleConfig.FRAME_LABELS_BUNDLE_NAME).getString(RESOURCE_KEY));
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
        EventBusHolder.get().register(this);
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


    @Subscribe
    public void onLanguageUpdate(ActionEvent e) {
        ResourceBundle labels = ResourceBundle.getBundle(
                BundleConfig.FRAME_LABELS_BUNDLE_NAME, Locale.getDefault());
        setTitle(labels.getString(RESOURCE_KEY));
        revalidate();
    }
}
