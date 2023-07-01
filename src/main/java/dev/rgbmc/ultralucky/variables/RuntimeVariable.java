package dev.rgbmc.ultralucky.variables;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RuntimeVariable implements Variable {
    private final Map<String, String> map = new HashMap<>();

    @Override
    public void put(String key, String value) {
        map.put(key, value);
    }

    @Override
    public String get(String key) {
        return map.get(key);
    }

    @Override
    public Set<Map.Entry<String, String>> getAll() {
        return map.entrySet();
    }

    @Override
    public void remove(String key) {
        map.remove(key);
    }

    @Override
    public void removeAll() {
        map.clear();
    }

    @Override
    public String evalVariables(String origin) {
        String copy = origin;
        for (Map.Entry<String, String> entry : getAll()) {
            copy = copy.replace("$" + entry.getKey(), entry.getValue());
        }
        return copy;
    }
}
