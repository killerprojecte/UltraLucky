package dev.rgbmc.ultralucky.conditions.impl;

import dev.rgbmc.ultralucky.conditions.Condition;
import dev.rgbmc.ultralucky.conditions.ConditionsParser;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventorySlotCondition implements Condition {
    @Override
    public boolean parse(ItemStack item, Player player, String args, RuntimeVariable variable) {
        String[] param = variable.evalVariables(args).split(",");
        ItemStack itemstack = player.getInventory().getItem(Integer.parseInt(param[0]));
        return ConditionsParser.getCondition("group").parse(itemstack, player, param[1]);
    }
}
