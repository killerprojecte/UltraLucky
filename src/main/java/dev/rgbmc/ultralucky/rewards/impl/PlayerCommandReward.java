package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.rewards.Reward;
import dev.rgbmc.ultralucky.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerCommandReward implements Reward {
    @Override
    public void forward(Player player, String args) {
        Bukkit.dispatchCommand(player, Color.color(args));
    }
}
