package dev.rgbmc.ultralucky.utils;

import org.bukkit.Bukkit;

public class MinecraftVersion {
    public static String getMinecraftVersion() {
        return Bukkit.getBukkitVersion().split("-")[0];
    }
}
