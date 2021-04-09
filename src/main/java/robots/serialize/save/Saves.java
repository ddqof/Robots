package robots.serialize.save;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import robots.model.log.Logger;
import robots.serialize.MySerializable;
import robots.view.frames.MainApplicationClosingFrame;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Saves {
    public static final File SAVES_PATH = new File("saves");
    public static final String JSON_EXTENSION = ".json";

    private final MainApplicationClosingFrame frame;
    private final List<Save> saves;

    public Saves(MainApplicationClosingFrame frameToSave, List<Save> saves) {
        this.frame = frameToSave;
        this.saves = saves;
    }

    public List<Optional<Object>> restore() {
        if (SAVES_PATH.exists() && frame.askForSavesRestore() == JOptionPane.YES_OPTION) {
            return saves.stream()
                    .map(x -> {
                        try {
                            return Optional.of(x.restore());
                        } catch (ObjectRestoreFailedException e) {
                            Logger.error(e.getMessage());
                            return Optional.empty();
                        }
                    })
                    .collect(Collectors.toList());
        }
        List<Optional<Object>> emptyResult = new ArrayList<>();
        for (var i = 0; i < saves.size(); i++) emptyResult.add(Optional.empty());
        return emptyResult;
    }

    public void storeAtExit(List<MySerializable> objectsToSave) {
        frame.setActionOnClose(
                () -> {
                    if (!SAVES_PATH.exists()) SAVES_PATH.mkdir();
                    ObjectWriter prettyPrinter = new ObjectMapper().writerWithDefaultPrettyPrinter();
                    objectsToSave.forEach(x -> {
                        try {
                            x.serialize(prettyPrinter);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
        );
    }
}
