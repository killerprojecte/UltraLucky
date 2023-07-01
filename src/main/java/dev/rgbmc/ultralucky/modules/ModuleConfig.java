package dev.rgbmc.ultralucky.modules;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.fastconfig.FastConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ModuleConfig {
    private final Module parent;
    private final FastConfig config = new FastConfig();
    private FileConfiguration configuration;

    public ModuleConfig(Module module) {
        parent = module;
        reloadConfig();
    }

    public FastConfig getConfig() {
        if (configuration == null) {
            reloadConfig();
        }
        return config;
    }

    public void reloadConfig() {
        configuration = YamlConfiguration.loadConfiguration(new File(UltraLucky.instance.getDataFolder(), parent.getName() + ".yml"));
        config.refreshPoint(configuration);
    }
}
