package dev.rgbmc.ultralucky.hook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaceholderAPIHook {
    public static String evalString(Player player, String str) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            return Placeholder.eval(player, str); // redirect to PlaceholderAPI.setPlaceholders() [Fix cause class not found]
        }
        return str;
    }
}
