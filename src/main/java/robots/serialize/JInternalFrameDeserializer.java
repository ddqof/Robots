package robots.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import javax.swing.*;
import java.beans.PropertyVetoException;
import java.io.IOException;

public class JInternalFrameDeserializer extends StdDeserializer<JInternalFrame> {

    public JInternalFrameDeserializer() {
        super(JInternalFrame.class);
    }

    @Override
    public JInternalFrame deserialize(
            JsonParser jsonParser, DeserializationContext deserializationContext
    ) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode iconNode = node.get(JsonFieldNames.ICON_FIELD_NAME);
        JsonNode xNode = node.get(JsonFieldNames.X_POS_FIELD_NAME);
        JsonNode yNode = node.get(JsonFieldNames.Y_POS_FIELD_NAME);
        JsonNode widthNode = node.get(JsonFieldNames.WIDTH_FIELD_NAME);
        JsonNode heightNode = node.get(JsonFieldNames.HEIGHT_FIELD_NAME);
        if (iconNode == null || xNode == null || yNode == null || widthNode == null || heightNode == null) {
            throw new IOException();
        } else {
            JInternalFrame internalFrame = new JInternalFrame();
            try {
                internalFrame.setIcon(iconNode.booleanValue());
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
            internalFrame.setLocation(xNode.intValue(), yNode.intValue());
            internalFrame.setSize(widthNode.intValue(), heightNode.intValue());
            return internalFrame;
        }
    }
}
