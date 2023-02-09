package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.rewards.Reward;
import ink.ptms.zaphkiel.Zaphkiel;
import ink.ptms.zaphkiel.api.Item;
import org.bukkit.entity.Player;

public class ZaphkielReward implements Reward {
    @Override
    public void forward(Player player, String args) {
        Item item = Zaphkiel.INSTANCE.api().getItemManager().getItem(args);
        if (item == null) {
            UltraLucky.instance.getLogger().warning("Zaphkiel物品库 未找到物品: " + args);
            return;
        }
        player.getLocation().getWorld().dropItemNaturally(player.getLocation(), item.buildItemStack(player));
    }
}
