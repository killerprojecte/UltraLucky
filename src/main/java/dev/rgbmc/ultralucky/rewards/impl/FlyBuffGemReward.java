package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.rewards.Reward;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.fastmcmirror.flybuff.api.FlyBuffAPI;

public class FlyBuffGemReward implements Reward {
    @Override
    public void forward(Player player, String args, RuntimeVariable variable) {
        try {
            String buff = variable.evalVariables(PlaceholderAPI.setPlaceholders(player, args));
            ItemStack item = FlyBuffAPI.getGem(buff);
            player.getLocation().getWorld().dropItemNaturally(player.getLocation(), item);
            variable.put("flybuff_name", buff);
        } catch (Exception e) {
            e.printStackTrace();
            UltraLucky.instance.getLogger().warning("在获取FlyBuff宝石: " + args + " 时遇到错误 请检查该宝石是否村庄");
        }
    }
}
