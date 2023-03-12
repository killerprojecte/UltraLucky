package dev.rgbmc.ultralucky.conditions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Condition {
    boolean parse(ItemStack item, Player player, String args);

    default void register(String tag) {
        ConditionsParser.registerCondition(tag, this);
    }
}
