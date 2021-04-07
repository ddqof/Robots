package robots.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import robots.model.game.GameModel;
import robots.view.internal_frames.ClosingInternalGameFrame;

import java.beans.PropertyVetoException;
import java.io.IOException;

import static robots.serialize.SavesConfig.*;

public class ClosingInternalGameFrameDeserializer extends StdDeserializer<ClosingInternalGameFrame> {
    public ClosingInternalGameFrameDeserializer() {
        super(ClosingInternalGameFrame.class);
    }

    @Override
    public ClosingInternalGameFrame deserialize(
            JsonParser jsonParser, DeserializationContext deserializationContext
    ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        GameModel gameModel = mapper.readValue(GAME_MODEL_SAVES_FILE, GameModel.class);
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ClosingInternalGameFrame gameFrame = new ClosingInternalGameFrame(
                gameModel,
                (int) node.get(X_POS_FIELD_NAME).numberValue(),
                (int) node.get(Y_POS_FIELD_NAME).numberValue(),
                (int) node.get(HEIGHT_FIELD_NAME).numberValue(),
                (int) node.get(WIDTH_FIELD_NAME).numberValue()
        );
        try {
            gameFrame.setIcon(node.get(ICON_FIELD_NAME).booleanValue());
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return gameFrame;
    }
}
