package robots.serialize.save;

import com.fasterxml.jackson.databind.ObjectMapper;
import robots.model.log.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Save {
    private final File saveFile;
    private final Class<?> savedClass;
    public static final String RESTORING_FAILED_IO = "%s restoring failed due to corrupted save files.";

    public Save(File saveFile, Class<?> savedClass) {
        this.saveFile = saveFile;
        this.savedClass = savedClass;
    }

    public Class<?> getSavedClass() {
        return savedClass;
    }

    public Optional<Object> restore() {
        try {
            return saveFile.exists()
                    ? Optional.of(new ObjectMapper().readValue(saveFile, savedClass))
                    : Optional.empty();
        } catch (IOException e) {
            Logger.error(String.format(RESTORING_FAILED_IO, savedClass.getName()));
            return Optional.empty();
        }
    }

    public static <T> boolean storeObject(File saveFile, T objectToSave) {
        try {
            new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(saveFile, objectToSave);
            return true;
        } catch (IOException e) {
            Logger.error(e.getMessage());
            return false;
        }
    }
}
