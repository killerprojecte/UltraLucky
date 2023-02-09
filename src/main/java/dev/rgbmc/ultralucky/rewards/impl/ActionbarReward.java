package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.rewards.Reward;
import dev.rgbmc.ultralucky.utils.Color;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ActionbarReward implements Reward {
    @Override
    public void forward(Player player, String args) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Color.color(args)));
    }
}
