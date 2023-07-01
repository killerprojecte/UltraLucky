package dev.rgbmc.ultralucky.conditions.impl;

import dev.rgbmc.ultralucky.conditions.Condition;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnchantCondition implements Condition {
    @Override
    public boolean parse(ItemStack item, Player player, String args, RuntimeVariable variable) {
        if (item == null) return false;
        if (item.getType().equals(Material.AIR)) return false;
        String[] param = variable.evalVariables(args).split(":");
        if (param.length == 1) {
            return item.containsEnchantment(Enchantment.getByName(param[0].toUpperCase()));
        } else {
            return item.getEnchantmentLevel(Enchantment.getByName(param[0].toUpperCase())) == Integer.parseInt(param[1]);
        }
    }
}
