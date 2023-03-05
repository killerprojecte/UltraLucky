package dev.rgbmc.ultralucky.modules;

import dev.rgbmc.ultralucky.UltraLucky;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public interface Module extends Listener {

    Map<Module, ModuleConfig> configMap = new HashMap<>();

    public String getName();

    public String getVersion();

    public String getAuthor();

    default public ModuleConfig getConfigManager() {
        if (configMap.containsKey(this)) {
            return configMap.get(this);
        }
        ModuleConfig config = new ModuleConfig(this);
        configMap.put(this, config);
        return config;
    }

    default public void register() {
        UltraLucky.getModuleManager().registerModule(this);
    }
}
