package dev.rgbmc.ultralucky.conditions.impl;

import dev.rgbmc.ultralucky.conditions.Condition;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WorldRegexCondition implements Condition {
    @Override
    public boolean parse(ItemStack item, Player player, String args, RuntimeVariable variable) {
        return player.getLocation().getWorld().getName().matches(variable.evalVariables(PlaceholderAPI.setPlaceholders(player, args)));
    }
}
