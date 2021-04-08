package robots.controller.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import robots.model.log.LogWindowSource;

import java.io.IOException;

import static robots.controller.Saves.*;

public class LogWindowSourceSerializer extends StdSerializer<LogWindowSource> {
    public LogWindowSourceSerializer() {
        super(LogWindowSource.class);
    }

    @Override
    public void serialize(
            LogWindowSource logWindowSource, JsonGenerator jsonGenerator, SerializerProvider serializerProvider
    ) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField(LOG_SOURCE_MESSAGES_FIELD_NAME, logWindowSource.getMessages());
        jsonGenerator.writeEndObject();
    }
}
