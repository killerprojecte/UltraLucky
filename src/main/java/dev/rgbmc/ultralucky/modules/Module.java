package dev.rgbmc.ultralucky.modules;

import dev.rgbmc.ultralucky.UltraLucky;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

import java.util.concurrent.atomic.AtomicReference;

public interface Module extends Listener {
    public AtomicReference<FileConfiguration> config = new AtomicReference<>();

    public String getName();

    public String getVersion();

    public String getAuthor();

    default public ModuleConfig getConfigManager() {
        return new ModuleConfig(this);
    }

    default public void register() {
        UltraLucky.getModuleManager().registerModule(this);
    }
}
