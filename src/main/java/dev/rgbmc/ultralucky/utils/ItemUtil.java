package dev.rgbmc.ultralucky.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemUtil {
    public static boolean isEmpty(ItemStack item) {
        return item == null || item.getType().equals(Material.AIR);
    }
}
