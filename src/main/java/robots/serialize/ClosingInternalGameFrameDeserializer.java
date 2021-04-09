package robots.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import robots.model.game.GameModel;
import robots.view.internal_frames.ClosingInternalGameFrame;
import robots.view.internal_frames.ClosingInternalLogFrame;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Map;

import static robots.model.game.GameModel.GAME_MODEL_SAVES_FILE;
import static robots.view.internal_frames.JInternalFrameClosing.*;

public class ClosingInternalGameFrameDeserializer extends StdDeserializer<ClosingInternalGameFrame> {
    public ClosingInternalGameFrameDeserializer() {
        super(ClosingInternalGameFrame.class);
    }

    @Override
    public ClosingInternalGameFrame deserialize(
            JsonParser jsonParser, DeserializationContext deserializationContext
    ) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Map<String, Integer> jsonValues = DefaultDeserializer.getDefaultOrRead(
                node,
                ClosingInternalGameFrame.DEFAULT_GAME_WINDOW_POS_X,
                ClosingInternalGameFrame.DEFAULT_GAME_WINDOW_POS_Y,
                ClosingInternalLogFrame.DEFAULT_LOG_WINDOW_HEIGHT,
                ClosingInternalLogFrame.DEFAULT_LOG_WINDOW_WIDTH
        );
        ClosingInternalGameFrame gameFrame = new ClosingInternalGameFrame(
                new ObjectMapper().readValue(GAME_MODEL_SAVES_FILE, GameModel.class),
                jsonValues.get(X_POS_FIELD_NAME),
                jsonValues.get(Y_POS_FIELD_NAME),
                jsonValues.get(HEIGHT_FIELD_NAME),
                jsonValues.get(WIDTH_FIELD_NAME)
        );
        try {
            gameFrame.setIcon(node.get(ICON_FIELD_NAME).booleanValue());
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return gameFrame;
    }
}
