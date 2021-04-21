package robots.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import robots.view.internal_frames.JInternalFrameClosing;

import java.io.IOException;


public class JInternalFrameSerializer extends StdSerializer<JInternalFrameClosing> {

    public JInternalFrameSerializer() {
        super(JInternalFrameClosing.class);
    }

    @Override
    public void serialize(
            JInternalFrameClosing internalFrame, JsonGenerator jsonGenerator, SerializerProvider serializerProvider
    ) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeBooleanField(JsonFieldNames.IS_ICON, internalFrame.isIcon());
        jsonGenerator.writeNumberField(JsonFieldNames.X, internalFrame.getX());
        jsonGenerator.writeNumberField(JsonFieldNames.Y, internalFrame.getY());
        jsonGenerator.writeNumberField(JsonFieldNames.WIDTH, internalFrame.getWidth());
        jsonGenerator.writeNumberField(JsonFieldNames.HEIGHT, internalFrame.getHeight());
        jsonGenerator.writeBooleanField(JsonFieldNames.IS_VISIBLE, internalFrame.isVisible());
        jsonGenerator.writeEndObject();
    }
}
