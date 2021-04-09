package robots.serialize;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Map;

import static robots.view.internal_frames.JInternalFrameClosing.*;

public class DefaultDeserializer {
    public static Map<String, Integer> getDefaultOrRead(JsonNode node, int def_x, int def_y, int def_height, int def_width) {
        int x = def_x;
        int y = def_y;
        int height = def_height;
        int width = def_width;
        if (node.has(WIDTH_FIELD_NAME)) {
            width = node.get(WIDTH_FIELD_NAME).intValue();
        }
        if (node.has(HEIGHT_FIELD_NAME)) {
            height = node.get(HEIGHT_FIELD_NAME).intValue();
        }
        if (node.has(X_POS_FIELD_NAME)) {
            x = node.get(X_POS_FIELD_NAME).intValue();
        }
        if (node.has(Y_POS_FIELD_NAME)) {
            y = node.get(Y_POS_FIELD_NAME).intValue();
        }
        Map<String, Integer> result = new HashMap<>();
        result.put(X_POS_FIELD_NAME, x);
        result.put(Y_POS_FIELD_NAME, y);
        result.put(HEIGHT_FIELD_NAME, height);
        result.put(WIDTH_FIELD_NAME, width);
        return result;
    }
}
