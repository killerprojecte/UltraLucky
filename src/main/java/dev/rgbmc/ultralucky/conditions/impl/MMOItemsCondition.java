package dev.rgbmc.ultralucky.conditions.impl;

import dev.rgbmc.ultralucky.conditions.Condition;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MMOItemsCondition implements Condition {
    @Override
    public boolean parse(ItemStack item, Player player, String args, RuntimeVariable variable) {
        return MMOItems.getID(item) != null;
    }
}
