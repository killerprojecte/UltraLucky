package dev.rgbmc.ultralucky;

import dev.rgbmc.ultralucky.modules.ModuleManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class UltraLucky extends JavaPlugin {

    public static UltraLucky instance;

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
