package dev.rgbmc.ultralucky.variables;

import java.util.Map;
import java.util.Set;

public interface Variable {
    void put(String key, String value);

    String get(String key);

    Set<Map.Entry<String, String>> getAll();

    void remove(String key);

    void removeAll();

    String evalVariables(String origin);
}
