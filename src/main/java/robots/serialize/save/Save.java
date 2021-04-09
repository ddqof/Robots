package robots.serialize.save;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Save {
    private final File saveFile;
    private final Class<?> savedClass;

    public static final String RESTORING_FAILED_IO = "%s restoring failed due to corrupted save files.";

    public Save(File saveFile, Class<?> savedClass) {
        this.saveFile = saveFile;
        this.savedClass = savedClass;
    }

    public Object restore() throws ObjectRestoreFailedException {
        try {
            return new ObjectMapper().readValue(saveFile, savedClass);
        } catch (IOException e) {
            throw new ObjectRestoreFailedException(String.format(RESTORING_FAILED_IO, savedClass.getName()));
        }
    }
}
