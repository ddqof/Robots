package robots.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import robots.view.frame.JInternalFrameUtils;

import javax.swing.*;
import java.io.IOException;

public class JInternalFrameDeserializer extends StdDeserializer<JInternalFrame> {
    public JInternalFrameDeserializer() {
        super(JInternalFrame.class);
    }

    @Override
    public JInternalFrame deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode iconNode = node.get(JsonFieldNames.IS_ICON);
        JsonNode xNode = node.get(JsonFieldNames.X);
        JsonNode yNode = node.get(JsonFieldNames.Y);
        JsonNode widthNode = node.get(JsonFieldNames.WIDTH);
        JsonNode heightNode = node.get(JsonFieldNames.HEIGHT);
        JsonNode isVisibleNode = node.get(JsonFieldNames.IS_VISIBLE);
        if (iconNode == null || xNode == null || yNode == null || widthNode == null || heightNode == null || isVisibleNode == null) {
            throw new IOException();
        } else {
            return JInternalFrameUtils.getEmptyFrame(
                    iconNode.booleanValue(),
                    isVisibleNode.booleanValue(),
                    xNode.intValue(),
                    yNode.intValue(),
                    widthNode.intValue(),
                    widthNode.intValue()
            );
        }
    }
}
