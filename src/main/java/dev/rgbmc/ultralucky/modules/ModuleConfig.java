package dev.rgbmc.ultralucky.modules;

import dev.rgbmc.ultralucky.UltraLucky;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ModuleConfig {
    private final Module parent;
    private FileConfiguration config = null;

    public ModuleConfig(Module module) {
        parent = module;
        reloadConfig();
    }

    public FileConfiguration getConfig() {
        if (config == null) {
            config = YamlConfiguration.loadConfiguration(new File(UltraLucky.instance.getDataFolder(), parent.getName() + ".yml"));
            return config;
        }
        return config;
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(new File(UltraLucky.instance.getDataFolder(), parent.getName() + ".yml"));
    }
}
