package robots.model.log;

import robots.serialize.save.Save;

public final class Logger {
    private static final LogWindowSource logWindowSource;

    static {
        logWindowSource = (LogWindowSource) new Save(LogWindowSource.SAVES_FILE, LogWindowSource.class)
                .restore()
                .orElse(LogWindowSource.getDefaultSource());
    }

    public static void reset() {
        logWindowSource.clear();
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
