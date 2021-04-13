package robots.model.log;

import robots.serialize.save.Save;

import static robots.model.log.LogWindowSource.LOG_SOURCE_SAVES_FILE;

public final class Logger {
    private static LogWindowSource logWindowSource;

    public static void init() {
        if (logWindowSource == null) {
            logWindowSource = (LogWindowSource) new Save(LOG_SOURCE_SAVES_FILE, LogWindowSource.class)
                    .restore()
                    .orElse(new LogWindowSource(100));
        }
    }

    private Logger() {
    }

    public static void debug(String message) {
        logWindowSource.append(LogLevel.Debug, message);
    }

    public static void error(String message) {
        logWindowSource.append(LogLevel.Error, message);
    }

    public static LogWindowSource getLogWindowSource() {
        return logWindowSource;
    }
}
