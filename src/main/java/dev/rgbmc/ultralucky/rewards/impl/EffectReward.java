package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.hook.PlaceholderAPIHook;
import dev.rgbmc.ultralucky.rewards.Reward;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectReward implements Reward {
    @Override
    public void forward(Player player, String args, RuntimeVariable variable) {
        String[] params = variable.evalVariables(PlaceholderAPIHook.evalString(player, args)).split(",");
        PotionEffect potionEffect = new PotionEffect(PotionEffectType.getByName(params[0]), Integer.parseInt(params[1]), Integer.parseInt(params[2]));
        player.addPotionEffect(potionEffect);
    }
}
