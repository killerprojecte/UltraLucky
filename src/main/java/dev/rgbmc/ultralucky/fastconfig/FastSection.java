package dev.rgbmc.ultralucky.fastconfig;

import java.util.List;
import java.util.stream.Collectors;

public class FastSection implements Section {
    private final FastConfig parent;
    private final String key;

    protected FastSection(FastConfig parent, String key) {
        this.parent = parent;
        this.key = key;
    }

    @Override
    public Object get(String key) {
        return parent.get(this.key + "." + key);
    }

    @Override
    public List<String> getKeys(boolean deep) {
        return parent.objectMap.keySet().stream().filter(s -> {
            if (!s.startsWith(this.key + ".")) return false;
            if (deep) return true;
            return !s.replace(this.key + ".", "").contains(".");
        }).map(s -> s.replace(this.key + ".", "")).collect(Collectors.toList());
    }
}
