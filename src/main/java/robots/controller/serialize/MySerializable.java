package robots.controller.serialize;

import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;

public interface MySerializable {
    void serialize(ObjectWriter writer) throws IOException;
}
