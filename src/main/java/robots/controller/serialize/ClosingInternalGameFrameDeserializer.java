package robots.controller.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import robots.model.game.GameModel;
import robots.view.internal_frames.ClosingInternalGameFrame;

import java.beans.PropertyVetoException;
import java.io.IOException;

import static robots.controller.Saves.*;

public class ClosingInternalGameFrameDeserializer extends StdDeserializer<ClosingInternalGameFrame> {
    public ClosingInternalGameFrameDeserializer() {
        super(ClosingInternalGameFrame.class);
    }

    @Override
    public ClosingInternalGameFrame deserialize(
            JsonParser jsonParser, DeserializationContext deserializationContext
    ) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ClosingInternalGameFrame gameFrame = new ClosingInternalGameFrame(
                new ObjectMapper().readValue(GAME_MODEL_SAVES_FILE, GameModel.class),
                node.get(X_POS_FIELD_NAME).intValue(),
                node.get(Y_POS_FIELD_NAME).intValue(),
                node.get(HEIGHT_FIELD_NAME).intValue(),
                node.get(WIDTH_FIELD_NAME).intValue()
        );
        try {
            gameFrame.setIcon(node.get(ICON_FIELD_NAME).booleanValue());
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return gameFrame;
    }
}
