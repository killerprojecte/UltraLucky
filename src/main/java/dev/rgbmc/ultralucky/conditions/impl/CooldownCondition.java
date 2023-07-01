package dev.rgbmc.ultralucky.conditions.impl;

import dev.rgbmc.ultralucky.conditions.Condition;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownCondition implements Condition {
    public static Map<String, Map<UUID, Long>> cool_downs = new HashMap<>();

    @Override
    public boolean parse(ItemStack item, Player player, String args, RuntimeVariable variable) {
        args = variable.evalVariables(args);
        if (cool_downs.containsKey(args)) {
            Map<UUID, Long> data = cool_downs.get(args);
            return !data.containsKey(player.getUniqueId());
        }
        return true;
    }
}
