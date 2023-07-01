package dev.rgbmc.ultralucky.conditions;

import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Condition {
    default boolean parse(ItemStack item, Player player, String args) {
        return parse(item, player, args, new RuntimeVariable());
    }

    default boolean parse(ItemStack item, Player player, String args, RuntimeVariable variable) {
        return parse(item, player, args);
    }

    default void register(String tag) {
        ConditionsParser.registerCondition(tag, this);
    }
}
