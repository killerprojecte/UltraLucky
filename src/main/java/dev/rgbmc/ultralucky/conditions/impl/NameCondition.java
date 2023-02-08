package dev.rgbmc.ultralucky.conditions.impl;

import dev.rgbmc.ultralucky.conditions.Condition;
import dev.rgbmc.ultralucky.utils.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NameCondition implements Condition {
    @Override
    public boolean parse(ItemStack item, Player player, String args) {
        return item.hasItemMeta() &&
                item.getItemMeta().hasDisplayName() &&
                item.getItemMeta().getDisplayName().equals(Color.color(args)
                );
    }
}
