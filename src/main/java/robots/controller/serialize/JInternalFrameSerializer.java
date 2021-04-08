package robots.controller.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import javax.swing.*;
import java.io.IOException;

import static robots.controller.Saves.*;

public class JInternalFrameSerializer extends StdSerializer<JInternalFrame> {

    public JInternalFrameSerializer() {
        super(JInternalFrame.class);
    }

    @Override
    public void serialize(
            JInternalFrame internalFrame, JsonGenerator jsonGenerator, SerializerProvider serializerProvider
    ) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeBooleanField(ICON_FIELD_NAME, internalFrame.isIcon());
        jsonGenerator.writeNumberField(X_POS_FIELD_NAME, internalFrame.getX());
        jsonGenerator.writeNumberField(Y_POS_FIELD_NAME, internalFrame.getY());
        jsonGenerator.writeNumberField(WIDTH_FIELD_NAME, internalFrame.getWidth());
        jsonGenerator.writeNumberField(HEIGHT_FIELD_NAME, internalFrame.getHeight());
        jsonGenerator.writeEndObject();
    }
}
