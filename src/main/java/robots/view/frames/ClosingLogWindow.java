package robots.view.frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;

import javax.swing.JPanel;

import robots.model.log.LogChangeListener;
import robots.model.log.LogEntry;
import robots.model.log.LogWindowSource;

public class ClosingLogWindow extends JInternalFrameClosing implements LogChangeListener {
    private LogWindowSource logSource;
    private TextArea logContent;
    private static final String CLOSING_LOG_WINDOW_TITLE = "Work protocol";
    private static final String CLOSING_CONFIRM_MESSAGE = "Do you want to exit?";
    private static final String CLOSING_DIALOG_TITLE = "Exit log window";

    public ClosingLogWindow(LogWindowSource logSource, int width, int height, int x, int y) {
        super(
                CLOSING_LOG_WINDOW_TITLE,
                true,
                true,
                true,
                true,
                CLOSING_CONFIRM_MESSAGE,
                CLOSING_DIALOG_TITLE
        );
        this.logSource = logSource;
        this.logSource.registerListener(this);
        logContent = new TextArea("");
        logContent.setSize(200, 500);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        updateLogContent();
        setLocation(x, y);
        setSize(width, height);
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
}
