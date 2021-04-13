package robots.serialize.save;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Saves {
    public static final File SAVES_PATH = new File("saves");
    public static final String JSON_EXTENSION = ".json";

    private final List<Save> saveList;

    public Saves(List<Save> saveList) {
        this.saveList = saveList;
    }

    public Map<Class<?>, Optional<Object>> restore() {
        return saveList
                .stream()
                .collect(Collectors.toMap(Save::getSavedClass, Save::restore));
    }
}
