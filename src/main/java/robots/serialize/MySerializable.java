package robots.serialize;

import com.fasterxml.jackson.databind.ObjectWriter;

public interface MySerializable {
    boolean serialize(ObjectWriter writer);
}
