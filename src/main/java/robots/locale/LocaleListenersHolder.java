package robots.locale;

import java.util.ArrayList;
import java.util.List;

public class LocaleListenersHolder {
    private static final List<LocaleChangeListener> listeners = new ArrayList<>();

    public static void register(LocaleChangeListener listener) {
        listeners.add(listener);
    }

    public static void updateLanguage() {
        listeners.forEach(LocaleChangeListener::onLanguageUpdate);
    }
}
