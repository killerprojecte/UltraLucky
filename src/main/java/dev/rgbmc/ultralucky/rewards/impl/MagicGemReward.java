package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.rewards.Reward;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import pku.yim.magicgem.gem.Gem;
import pku.yim.magicgem.gem.GemManager;

public class MagicGemReward implements Reward {
    @Override
    public void forward(Player player, String args) {
        Gem gem = GemManager.getGemByName(PlaceholderAPI.setPlaceholders(player, args));
        if (gem == null) {
            UltraLucky.instance.getLogger().warning("魔术宝石: " + args + " 不存在");
            return;
        }
        player.getLocation().getWorld().dropItemNaturally(player.getLocation(), gem.getRealGem());
    }
}
