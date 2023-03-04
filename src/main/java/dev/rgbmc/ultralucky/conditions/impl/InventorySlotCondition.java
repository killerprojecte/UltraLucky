package dev.rgbmc.ultralucky.conditions.impl;

import dev.rgbmc.ultralucky.conditions.Condition;
import dev.rgbmc.ultralucky.conditions.ConditionsParser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventorySlotCondition implements Condition {
    @Override
    public boolean parse(ItemStack item, Player player, String args) {
        String[] param = args.split(",");
        ItemStack itemstack = player.getInventory().getItem(Integer.parseInt(param[0]));
        return ConditionsParser.getCondition("group").parse(itemstack, player, param[1]);
    }
}
