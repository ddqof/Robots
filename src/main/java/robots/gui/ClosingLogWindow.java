package robots.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;

import javax.swing.JPanel;

import robots.log.LogChangeListener;
import robots.log.LogEntry;
import robots.log.LogWindowSource;

public class ClosingLogWindow extends JInternalFrameClosing implements LogChangeListener {
    private LogWindowSource logSource;
    private TextArea logContent;

    public ClosingLogWindow(LogWindowSource logSource) {
        super(
                "Протокол работы",
                true,
                true,
                true,
                true,
                "Do you want to exit?",
                "Exit log window"
        );
        this.logSource = logSource;
        this.logSource.registerListener(this);
        logContent = new TextArea("");
        logContent.setSize(200, 500);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
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
