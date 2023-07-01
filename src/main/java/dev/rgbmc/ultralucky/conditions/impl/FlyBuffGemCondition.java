package dev.rgbmc.ultralucky.conditions.impl;

import dev.rgbmc.ultralucky.conditions.Condition;
import dev.rgbmc.ultralucky.utils.ItemUtil;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.fastmcmirror.flybuff.api.FlyBuffAPI;

public class FlyBuffGemCondition implements Condition {
    @Override
    public boolean parse(ItemStack item, Player player, String args, RuntimeVariable variable) {
        args = variable.evalVariables(args);
        if (ItemUtil.isEmpty(item)) return false;
        return FlyBuffAPI.hasInstalledBuff(item) && FlyBuffAPI.getAllInstalledBuff(item).contains(args);
    }
}
