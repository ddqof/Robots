package robots.model.log;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.collections4.QueueUtils;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import robots.serialize.JsonSerializable;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

public class LogWindowSource implements JsonSerializable {
    public static final File SAVES_FILE = new File(Saves.PATH,
            String.format("logSource.%s", Saves.JSON_EXTENSION));
    public static final String MESSAGES_FIELD_NAME = "messages";

    private final Queue<LogEntry> messages;
    private final List<LogChangeListener> listeners;
    private volatile LogChangeListener[] activeListeners;

    public static LogWindowSource getDefaultSource() {
        return new LogWindowSource(100);
    }

    @JsonGetter(MESSAGES_FIELD_NAME)
    public Queue<LogEntry> getMessages() {
        return messages;
    }

    public LogWindowSource(int queueLength) {
        messages = QueueUtils.synchronizedQueue(new CircularFifoQueue<>(queueLength));
        listeners = new ArrayList<>();
    }

    @JsonCreator
    public LogWindowSource(@JsonProperty(MESSAGES_FIELD_NAME) CircularFifoQueue<LogEntry> messages) {
        this.messages = QueueUtils.synchronizedQueue(messages);
        listeners = new ArrayList<>();
    }

    public void registerListener(LogChangeListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
            activeListeners = null;
        }
    }

    public void unregisterListener(LogChangeListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
            activeListeners = null;
        }
    }

    public void append(LogLevel logLevel, String message) {
        LogEntry entry = new LogEntry(logLevel, message);
        messages.add(entry);
        if (activeListeners == null) {
            synchronized (listeners) {
                this.activeListeners = listeners.toArray(new LogChangeListener[0]);
            }
        }
        notifyListeners();
    }

    public void clear() {
        messages.clear();
        notifyListeners();
    }

    private void notifyListeners() {
        listeners.forEach(LogChangeListener::onLogChanged);
    }


    public Collection<LogEntry> all() {
        return messages;
    }

    @Override
    public boolean serialize() {
        return Save.storeObject(SAVES_FILE, this);
    }
}
