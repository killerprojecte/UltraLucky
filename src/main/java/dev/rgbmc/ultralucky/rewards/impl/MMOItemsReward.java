package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.hook.PlaceholderAPIHook;
import dev.rgbmc.ultralucky.rewards.Reward;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.entity.Player;

public class MMOItemsReward implements Reward {
    @Override
    public void forward(Player player, String args, RuntimeVariable variable) {
        String[] param = variable.evalVariables(PlaceholderAPIHook.evalString(player, args)).split(":");
        player.getLocation().getWorld().dropItemNaturally(
                player.getLocation(),
                MMOItems.plugin.getItem(param[0], param[1])
        );
    }
}
