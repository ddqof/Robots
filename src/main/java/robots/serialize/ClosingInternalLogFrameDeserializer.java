package robots.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import robots.model.log.Logger;
import robots.view.internal_frames.ClosingInternalLogFrame;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Map;

import static robots.view.internal_frames.ClosingInternalLogFrame.*;

public class ClosingInternalLogFrameDeserializer extends StdDeserializer<ClosingInternalLogFrame> {
    public ClosingInternalLogFrameDeserializer() {
        super(ClosingInternalLogFrame.class);
    }

    @Override
    public ClosingInternalLogFrame deserialize(
            JsonParser jsonParser, DeserializationContext deserializationContext
    ) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Map<String, Integer> jsonValues = DefaultDeserializer.getDefaultOrRead(
                node,
                DEFAULT_LOG_WINDOW_POS_X,
                DEFAULT_LOG_WINDOW_POS_Y,
                DEFAULT_LOG_WINDOW_HEIGHT,
                DEFAULT_LOG_WINDOW_WIDTH
        );
        ClosingInternalLogFrame logFrame = new ClosingInternalLogFrame(
                Logger.getLogWindowSource(),
                jsonValues.get(WIDTH_FIELD_NAME),
                jsonValues.get(HEIGHT_FIELD_NAME),
                jsonValues.get(X_POS_FIELD_NAME),
                jsonValues.get(Y_POS_FIELD_NAME)
        );
        try {
            logFrame.setIcon(node.get(ICON_FIELD_NAME).booleanValue());
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return logFrame;
    }
}
