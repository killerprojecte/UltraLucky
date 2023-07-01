package dev.rgbmc.ultralucky.fastconfig;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FastConfig implements Section {
    protected static Logger logger = Logger.getLogger("FastConfig");
    protected final Map<String, Object> objectMap = new HashMap<>();

    public void refreshPoint(FileConfiguration configuration, boolean debug) {
        objectMap.clear();
        for (String key : configuration.getKeys(true)) {
            if (configuration.isConfigurationSection(key)) {
                objectMap.put(key, new FastSection(this, key));
            } else {
                objectMap.put(key, configuration.get(key));
            }
        }
        if (debug) {
            logger.info("已缓存了 " + objectMap.size() + " 个配置节点 (已包含 " +
                    objectMap.values().stream().filter(value -> value instanceof FastSection).count()
                    + " 个配置目录)");
        }
    }

    public void refreshPoint(FileConfiguration configuration) {
        refreshPoint(configuration, false);
    }

    @Override
    public Object get(String key) {
        return objectMap.get(key);
    }

    protected Set<Map.Entry<String, Object>> getAll() {
        return objectMap.entrySet();
    }

    @Override
    public List<String> getKeys(boolean deep) {
        return objectMap.keySet().stream().filter(s -> {
            if (deep) return true;
            return !s.contains(".");
        }).collect(Collectors.toList());
    }
}
