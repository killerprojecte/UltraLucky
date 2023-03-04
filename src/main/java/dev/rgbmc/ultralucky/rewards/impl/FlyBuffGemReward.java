package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.rewards.Reward;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.fastmcmirror.flybuff.api.FlyBuffAPI;

public class FlyBuffGemReward implements Reward {
    @Override
    public void forward(Player player, String args) {
        try {
            ItemStack item = FlyBuffAPI.getGem(PlaceholderAPI.setPlaceholders(player, args));
            player.getLocation().getWorld().dropItemNaturally(player.getLocation(), item);
        } catch (Exception e) {
            e.printStackTrace();
            UltraLucky.instance.getLogger().warning("在获取FlyBuff宝石: " + args + " 时遇到错误 请检查该宝石是否村庄");
        }
    }
}
