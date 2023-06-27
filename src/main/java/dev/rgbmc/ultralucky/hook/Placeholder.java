package dev.rgbmc.ultralucky.hook;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class Placeholder {
    public static String eval(Player player, String str) {
        return PlaceholderAPI.setPlaceholders(player, str);
    }
}
