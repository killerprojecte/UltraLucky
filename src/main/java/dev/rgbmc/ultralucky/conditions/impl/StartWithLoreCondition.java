package dev.rgbmc.ultralucky.conditions.impl;

import dev.rgbmc.ultralucky.conditions.Condition;
import dev.rgbmc.ultralucky.hook.PlaceholderAPIHook;
import dev.rgbmc.ultralucky.utils.Color;
import dev.rgbmc.ultralucky.utils.ItemUtil;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StartWithLoreCondition implements Condition {
    @Override
    public boolean parse(ItemStack item, Player player, String args, RuntimeVariable variable) {
        return !ItemUtil.isEmpty(item) && item.hasItemMeta() && item.getItemMeta().hasLore() &&
                item.getItemMeta().getLore().stream().anyMatch(s ->
                        s.startsWith(Color.color(variable.evalVariables(PlaceholderAPIHook.evalString(player, args))))
                );
    }
}
