package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.rewards.Reward;
import dev.rgbmc.ultralucky.utils.Color;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class TitleReward implements Reward {
    @Override
    public void forward(Player player, String args) {
        String[] param = PlaceholderAPI.setPlaceholders(player, args).split(",");
        if (param.length == 2) {
            player.sendTitle(Color.color(param[0]), Color.color(param[1]));
        } else if (param.length == 5) {
            player.sendTitle(Color.color(param[0]), Color.color(param[1]), Integer.parseInt(param[2]),
                    Integer.parseInt(param[3]), Integer.parseInt(param[4]));
        } else {
            UltraLucky.instance.getLogger().warning("错误的标题参数: " + args);
        }
    }
}
