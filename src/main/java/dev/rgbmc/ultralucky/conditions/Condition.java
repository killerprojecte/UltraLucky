package dev.rgbmc.ultralucky.conditions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Condition {
    public boolean parse(ItemStack item, Player player, String args);
}
