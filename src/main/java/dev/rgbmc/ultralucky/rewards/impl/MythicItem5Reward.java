package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.hook.PlaceholderAPIHook;
import dev.rgbmc.ultralucky.rewards.Reward;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.entity.Player;

public class MythicItem5Reward implements Reward {
    @Override
    public void forward(Player player, String args, RuntimeVariable variable) {
        String[] param = args.split(":");
        player.getLocation().getWorld().dropItemNaturally(player.getLocation(),
                BukkitAdapter.adapt(
                        MythicBukkit.inst().getItemManager()
                                .getItem(variable.evalVariables(
                                        PlaceholderAPIHook.evalString(player, param[0])
                                )).get().generateItemStack(Integer.parseInt(param[1]))));
    }
}
