package dev.rgbmc.ultralucky.conditions.impl;

import dev.rgbmc.ultralucky.conditions.Condition;
import dev.rgbmc.ultralucky.hook.PlaceholderAPIHook;
import dev.rgbmc.ultralucky.utils.Color;
import dev.rgbmc.ultralucky.utils.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LoreCondition implements Condition {
    @Override
    public boolean parse(ItemStack item, Player player, String args) {
        return !ItemUtil.isEmpty(item) && item.hasItemMeta() && item.getItemMeta().hasLore() &&
                item.getItemMeta().getLore().contains(Color.color(PlaceholderAPIHook.evalString(player, args)));
    }
}
