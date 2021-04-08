package robots.model.log;

public final class Logger {
    private static LogWindowSource logWindowSource;

    public static void createDefaultInstance() {
        logWindowSource = new LogWindowSource(100);
    }

    public static void createInstance(LogWindowSource logWindowSource) {
        Logger.logWindowSource = logWindowSource;
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
