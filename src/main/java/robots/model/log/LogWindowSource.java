package robots.model.log;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.collections4.QueueUtils;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import robots.serialize.LogWindowSourceSerializer;
import robots.serialize.MySerializable;
import robots.serialize.save.Saves;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

/**
 * Что починить:
 * 1. Этот класс порождает утечку ресурсов (связанные слушатели оказываются
 * удерживаемыми в памяти)
 * 2. Этот класс хранит активные сообщения лога, но в такой реализации он
 * их лишь накапливает. Надо же, чтобы количество сообщений в логе было ограничено
 * величиной m_iQueueLength (т.е. реально нужна очередь сообщений
 * ограниченного размера)
 */
@JsonSerialize(using = LogWindowSourceSerializer.class)
public class LogWindowSource implements MySerializable {
    public static final File LOG_SOURCE_SAVES_FILE = new File(Saves.SAVES_PATH, "logSource" + Saves.JSON_EXTENSION);
    public static final String LOG_SOURCE_MESSAGES_FIELD_NAME = "messages";

    private final Queue<LogEntry> messages;
    private final List<LogChangeListener> listeners;
    private volatile LogChangeListener[] activeListeners;

    public Queue<LogEntry> getMessages() {
        return messages;
    }

    public LogWindowSource(int queueLength) {
        messages = QueueUtils.synchronizedQueue(new CircularFifoQueue<>(queueLength));
        listeners = new ArrayList<>();
    }

    @JsonCreator
    public LogWindowSource(@JsonProperty("messages") CircularFifoQueue<LogEntry> messages) {
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
        for (LogChangeListener listener : activeListeners) {
            listener.onLogChanged();
        }
    }

    public int size() {
        return messages.size();
    }

    public Iterable<LogEntry> range(int startFrom, int count) {
        if (startFrom < 0 || startFrom >= messages.size()) {
            return Collections.emptyList();
        }
        int indexTo = Math.min(startFrom + count, messages.size());
        return new ArrayList<>(messages).subList(startFrom, indexTo);
    }

    public Iterable<LogEntry> all() {
        return messages;
    }

    @Override
    public void serialize(ObjectWriter writer) throws IOException {
        writer.writeValue(LOG_SOURCE_SAVES_FILE, this);
    }
}
