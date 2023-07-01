package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.rewards.Reward;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pku.yim.magicgem.gem.Gem;
import pku.yim.magicgem.gem.GemManager;

public class MagicGemReward implements Reward {
    @Override
    public void forward(Player player, String args, RuntimeVariable variable) {
        String gem_name = variable.evalVariables(PlaceholderAPI.setPlaceholders(player, args));
        Gem gem = GemManager.getGemByName(gem_name);
        if (gem == null) {
            UltraLucky.instance.getLogger().warning("魔术宝石: " + gem_name + " 不存在");
            return;
        }
        ItemStack real_gem = gem.getRealGem();
        variable.put("magicgem_name", gem_name);
        if (real_gem.hasItemMeta() && real_gem.getItemMeta().hasDisplayName()) {
            variable.put("magicgem_item_name", real_gem.getItemMeta().getDisplayName());
        }
        player.getLocation().getWorld().dropItemNaturally(player.getLocation(), real_gem);
    }
}
