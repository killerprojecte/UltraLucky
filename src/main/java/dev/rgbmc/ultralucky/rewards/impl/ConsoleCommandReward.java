package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.rewards.Reward;
import dev.rgbmc.ultralucky.utils.Color;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ConsoleCommandReward implements Reward {
    @Override
    public void forward(Player player, String args) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Color.color(PlaceholderAPI.setPlaceholders(player, args)));
    }
}
