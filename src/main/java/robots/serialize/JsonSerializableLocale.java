package robots.serialize;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;

import java.io.File;
import java.util.Locale;

public class JsonSerializableLocale implements JsonSerializable {
    private final String country;
    private final String language;
    public static final File SAVES_FILE = new File(
            Saves.PATH, String.format("locale.%s", Saves.JSON_EXTENSION));

    public String getCountry() {
        return country;
    }

    public String getLanguage() {
        return language;
    }

    @JsonCreator
    public JsonSerializableLocale(
            @JsonProperty("country") String country,
            @JsonProperty("language") String language) {
        this.country = country;
        this.language = language;
    }

    public JsonSerializableLocale(Locale locale) {
        this.country = locale.getCountry();
        this.language = locale.getLanguage();
    }

    @Override
    public boolean serialize() {
        return Save.storeObject(SAVES_FILE, this);
    }
}
