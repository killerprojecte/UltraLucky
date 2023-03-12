package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.rewards.Reward;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnchantReward implements Reward {
    @Override
    public void forward(Player player, String args) {
        ItemStack item = player.getItemInHand();
        if (item == null) return;
        if (item.getType().equals(Material.AIR)) return;
        String[] param = args.split(":");
        int level;
        if (param.length == 2) {
            level = Integer.parseInt(param[1]);
        } else {
            level = 0;
        }
        item.addEnchantment(Enchantment.getByName(param[0].toUpperCase()), level);
        player.setItemInHand(item);
    }
}
