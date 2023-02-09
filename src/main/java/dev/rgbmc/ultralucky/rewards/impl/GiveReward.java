package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.rewards.Reward;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class GiveReward implements Reward {
    @Override
    public void forward(Player player, String args) {
        String[] param = args.split(" ");
        ItemStack item = new ItemStack(Material.getMaterial(param[0]), Integer.parseInt(param[1]));
        Map<Integer, ItemStack> drops = player.getInventory().addItem(item);
        for (Map.Entry<Integer, ItemStack> entry : drops.entrySet()) {
            player.getLocation().getWorld().dropItem(player.getLocation(), entry.getValue());
        }
    }
}
