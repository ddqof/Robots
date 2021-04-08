package robots.controller.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import robots.model.log.LogWindowSource;
import robots.view.internal_frames.ClosingInternalLogFrame;
import static robots.controller.Saves.*;

import java.beans.PropertyVetoException;
import java.io.IOException;

public class ClosingInternalLogFrameDeserializer extends StdDeserializer<ClosingInternalLogFrame> {
    public ClosingInternalLogFrameDeserializer() {
        super(ClosingInternalLogFrame.class);
    }

    @Override
    public ClosingInternalLogFrame deserialize(
            JsonParser jsonParser, DeserializationContext deserializationContext
    ) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ClosingInternalLogFrame logFrame = new ClosingInternalLogFrame(
                mapper.readValue(LOG_SOURCE_SAVES_FILE, LogWindowSource.class),
                (int) node.get(WIDTH_FIELD_NAME).numberValue(),
                (int) node.get(HEIGHT_FIELD_NAME).numberValue(),
                (int) node.get(X_POS_FIELD_NAME).numberValue(),
                (int) node.get(Y_POS_FIELD_NAME).numberValue()
        );
        try {
            logFrame.setIcon(node.get(ICON_FIELD_NAME).booleanValue());
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return logFrame;
    }
}
