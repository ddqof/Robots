package robots.serialize;

import com.fasterxml.jackson.databind.ObjectWriter;

public interface JsonSerializable {
    boolean serialize(ObjectWriter writer);
}
