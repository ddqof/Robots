package robots.model.log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import robots.controller.Saves;

public final class Logger {
    private static LogWindowSource logWindowSource;

    public static void init() {
        if (logWindowSource == null) {
            try {
                logWindowSource = new ObjectMapper().readValue(Saves.LOG_SOURCE_SAVES_FILE, LogWindowSource.class);
            } catch (IOException e) {
                logWindowSource = new LogWindowSource(100);
            }
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
