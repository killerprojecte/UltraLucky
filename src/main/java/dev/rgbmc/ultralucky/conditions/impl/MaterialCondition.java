package dev.rgbmc.ultralucky.conditions.impl;

import dev.rgbmc.ultralucky.conditions.Condition;
import dev.rgbmc.ultralucky.utils.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MaterialCondition implements Condition {
    @Override
    public boolean parse(ItemStack item, Player player, String args) {
        if (ItemUtil.isEmpty(item)) return false;
        return item.getType().toString().equalsIgnoreCase(args);
    }
}
