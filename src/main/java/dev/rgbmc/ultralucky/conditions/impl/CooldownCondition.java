package dev.rgbmc.ultralucky.conditions.impl;

import dev.rgbmc.ultralucky.conditions.Condition;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownCondition implements Condition {
    public static Map<UUID, Long> cool_downs = new HashMap<>();

    @Override
    public boolean parse(ItemStack item, Player player, String args) {
        return !cool_downs.containsKey(player.getUniqueId());
    }
}
