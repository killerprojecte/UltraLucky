package dev.rgbmc.ultralucky.modules;

import dev.rgbmc.ultralucky.UltraLucky;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public interface Module extends Listener {

    Map<Module, ModuleConfig> configMap = new HashMap<>();

    String getName();

    String getVersion();

    String getAuthor();

    default ModuleConfig getConfigManager() {
        if (configMap.containsKey(this)) {
            return configMap.get(this);
        }
        ModuleConfig config = new ModuleConfig(this);
        configMap.put(this, config);
        return config;
    }

    default void register() {
        UltraLucky.getModuleManager().registerModule(this);
    }
}
