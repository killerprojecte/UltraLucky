package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.rewards.Reward;
import dev.rgbmc.ultralucky.utils.Color;
import org.bukkit.entity.Player;

public class MessageReward implements Reward {
    @Override
    public void forward(Player player, String args) {
        player.sendMessage(Color.color(args));
    }
}
