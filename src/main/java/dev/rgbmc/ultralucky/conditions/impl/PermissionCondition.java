package dev.rgbmc.ultralucky.conditions.impl;

import dev.rgbmc.ultralucky.conditions.Condition;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PermissionCondition implements Condition {
    @Override
    public boolean parse(ItemStack item, Player player, String args) {
        return player.hasPermission(args);
    }
}
