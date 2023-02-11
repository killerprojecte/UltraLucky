package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.rewards.Reward;
import dev.rgbmc.ultralucky.utils.Color;
import org.bukkit.entity.Player;

public class ChatReward implements Reward {
    @Override
    public void forward(Player player, String args) {
        player.chat(Color.color(args));
    }
}