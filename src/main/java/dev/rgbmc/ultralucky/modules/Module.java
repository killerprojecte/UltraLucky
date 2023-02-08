package dev.rgbmc.ultralucky.modules;

import dev.rgbmc.ultralucky.UltraLucky;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public interface Module extends Listener {
    public String getName();

    public String getVersion();

    public String getAuthor();

    AtomicReference<FileConfiguration> config = new AtomicReference<>();

    default public FileConfiguration getConfig() {
        if (config.get()==null){
            config.set(YamlConfiguration.loadConfiguration(new File(UltraLucky.instance.getDataFolder(), getName() + ".yml")));
            return config.get();
        }
        return config.get();
    }

    default public void reloadConfig() {
        config.set(YamlConfiguration.loadConfiguration(new File(UltraLucky.instance.getDataFolder(), getName() + ".yml")));
    }
}
