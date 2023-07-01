package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.rewards.Reward;
import dev.rgbmc.ultralucky.utils.Color;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class ChatReward implements Reward {
    @Override
    public void forward(Player player, String args, RuntimeVariable variable) {
        player.chat(Color.color(variable.evalVariables(PlaceholderAPI.setPlaceholders(player, args))));
    }
}
