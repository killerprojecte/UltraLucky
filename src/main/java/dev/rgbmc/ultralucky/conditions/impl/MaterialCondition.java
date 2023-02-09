package dev.rgbmc.ultralucky.conditions.impl;

import dev.rgbmc.ultralucky.conditions.Condition;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MaterialCondition implements Condition {
    @Override
    public boolean parse(ItemStack item, Player player, String args) {
        return item.getType().toString().equalsIgnoreCase(args);
    }
}
