package dev.rgbmc.ultralucky.conditions.impl;

import dev.rgbmc.ultralucky.conditions.Condition;
import dev.rgbmc.ultralucky.hook.PlaceholderAPIHook;
import dev.rgbmc.ultralucky.utils.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StartWithLoreCondition implements Condition {
    @Override
    public boolean parse(ItemStack item, Player player, String args) {
        return item.hasItemMeta() && item.getItemMeta().hasLore() &&
                item.getItemMeta().getLore().stream().anyMatch(s ->
                        s.startsWith(Color.color(PlaceholderAPIHook.evalString(player, args)))
                );
    }
}
