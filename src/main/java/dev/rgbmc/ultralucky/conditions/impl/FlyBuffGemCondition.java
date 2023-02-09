package dev.rgbmc.ultralucky.conditions.impl;

import dev.rgbmc.ultralucky.conditions.Condition;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.fastmcmirror.flybuff.api.FlyBuffAPI;

public class FlyBuffGemCondition implements Condition {
    @Override
    public boolean parse(ItemStack item, Player player, String args) {
        return FlyBuffAPI.hasInstalledBuff(item) && FlyBuffAPI.getAllInstalledBuff(item).contains(args);
    }
}
