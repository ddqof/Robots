package robots.model.log;

import robots.serialize.save.Save;

import javax.swing.*;

import static robots.model.log.LogWindowSource.LOG_SOURCE_SAVES_FILE;

public final class Logger {
    private static LogWindowSource logWindowSource;

    public static void init(int restoreOption) {
        if (logWindowSource == null) {
            if (restoreOption == JOptionPane.YES_OPTION) {
                logWindowSource = (LogWindowSource) new Save(LOG_SOURCE_SAVES_FILE, LogWindowSource.class)
                        .restore()
                        .orElse(LogWindowSource.getDefaultSource());
            } else {
                logWindowSource = LogWindowSource.getDefaultSource();
            }
        }
    }

    private Logger() {
    }

    public static void debug(String message) {
        logWindowSource.append(LogLevel.Debug, getMessageWithDate(message));
    }

    public static void error(String message) {
        logWindowSource.append(LogLevel.Error, getMessageWithDate(message));
    }

    public static LogWindowSource getLogWindowSource() {
        return logWindowSource;
    }

    private static String getMessageWithDate(String message) {
        return new java.util.Date(System.currentTimeMillis()) + "\t" + message;
    }
}
