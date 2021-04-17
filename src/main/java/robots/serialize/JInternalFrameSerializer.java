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
        jsonGenerator.writeBooleanField(JsonFieldNames.ICON_FIELD_NAME, internalFrame.isIcon());
        jsonGenerator.writeNumberField(JsonFieldNames.X_POS_FIELD_NAME, internalFrame.getX());
        jsonGenerator.writeNumberField(JsonFieldNames.Y_POS_FIELD_NAME, internalFrame.getY());
        jsonGenerator.writeNumberField(JsonFieldNames.WIDTH_FIELD_NAME, internalFrame.getWidth());
        jsonGenerator.writeNumberField(JsonFieldNames.HEIGHT_FIELD_NAME, internalFrame.getHeight());
        jsonGenerator.writeEndObject();
    }
}
