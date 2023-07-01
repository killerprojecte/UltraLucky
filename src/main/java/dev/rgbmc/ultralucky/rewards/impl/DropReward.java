package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.rewards.Reward;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DropReward implements Reward {
    @Override
    public void forward(Player player, String args, RuntimeVariable variable) {
        String[] param = variable.evalVariables(PlaceholderAPI.setPlaceholders(player, args)).split(" ");
        ItemStack item = new ItemStack(Material.getMaterial(param[0]), Integer.parseInt(param[1]));
        variable.put("drop_item_type", item.getType().toString().toUpperCase());
        player.getLocation().getWorld().dropItemNaturally(player.getLocation(), item);
    }
}
