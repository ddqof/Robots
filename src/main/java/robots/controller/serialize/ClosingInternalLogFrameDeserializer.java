package robots.controller.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import robots.model.log.LogWindowSource;
import robots.model.log.Logger;
import robots.view.internal_frames.ClosingInternalLogFrame;

import java.beans.PropertyVetoException;
import java.io.IOException;

import static robots.controller.Saves.*;

public class ClosingInternalLogFrameDeserializer extends StdDeserializer<ClosingInternalLogFrame> {
    public ClosingInternalLogFrameDeserializer() {
        super(ClosingInternalLogFrame.class);
    }

    @Override
    public ClosingInternalLogFrame deserialize(
            JsonParser jsonParser, DeserializationContext deserializationContext
    ) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ClosingInternalLogFrame logFrame = new ClosingInternalLogFrame(
                Logger.getLogWindowSource(),
                node.get(WIDTH_FIELD_NAME).intValue(),
                node.get(HEIGHT_FIELD_NAME).intValue(),
                node.get(X_POS_FIELD_NAME).intValue(),
                node.get(Y_POS_FIELD_NAME).intValue()
        );
        try {
            logFrame.setIcon(node.get(ICON_FIELD_NAME).booleanValue());
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return logFrame;
    }
}
