package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.rewards.Reward;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import ink.ptms.zaphkiel.Zaphkiel;
import ink.ptms.zaphkiel.api.Item;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class ZaphkielReward implements Reward {
    @Override
    public void forward(Player player, String args, RuntimeVariable variable) {
        args = variable.evalVariables(PlaceholderAPI.setPlaceholders(player, args));
        Item item = Zaphkiel.INSTANCE.api().getItemManager().getItem(args);
        if (item == null) {
            UltraLucky.instance.getLogger().warning("Zaphkiel物品库 未找到物品: " + args);
            return;
        }
        variable.put("zaphkiel_item", item.getDisplay());
        player.getLocation().getWorld().dropItemNaturally(player.getLocation(), item.buildItemStack(player));
    }
}
