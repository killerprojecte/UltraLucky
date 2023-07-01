package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.rewards.Reward;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class GiveReward implements Reward {
    @Override
    public void forward(Player player, String args, RuntimeVariable variable) {
        String[] param = variable.evalVariables(PlaceholderAPI.setPlaceholders(player, args)).split(" ");
        ItemStack item = new ItemStack(Material.getMaterial(param[0]), Integer.parseInt(param[1]));
        Map<Integer, ItemStack> drops = player.getInventory().addItem(item);
        for (Map.Entry<Integer, ItemStack> entry : drops.entrySet()) {
            player.getLocation().getWorld().dropItem(player.getLocation(), entry.getValue());
        }
    }
}
