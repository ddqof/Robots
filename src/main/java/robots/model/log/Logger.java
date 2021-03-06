package robots.model.log;

import robots.serialize.save.Save;

import javax.swing.*;

import static robots.model.log.LogWindowSource.SAVES_FILE;

public final class Logger {
    private static LogWindowSource logWindowSource = LogWindowSource.getDefaultSource();

    public static void restore(int restoreOption) {
        if (logWindowSource == null) {
            if (restoreOption == JOptionPane.YES_OPTION) {
                logWindowSource = (LogWindowSource) new Save(SAVES_FILE, LogWindowSource.class)
                        .restore()
                        .orElse(LogWindowSource.getDefaultSource());
            }
        }
    }

    public static boolean exists() {
        return logWindowSource != null;
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
