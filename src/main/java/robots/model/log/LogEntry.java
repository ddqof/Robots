package robots.model.log;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LogEntry {
    private final LogLevel logLevel;
    private final String message;

    public LogEntry(
            @JsonProperty("level") LogLevel logLevel,
            @JsonProperty("message") String message) {
        this.message = message;
        this.logLevel = logLevel;
    }

    public String getMessage() {
        return message;
    }

    public LogLevel getLevel() {
        return logLevel;
    }
}

