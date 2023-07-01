package dev.rgbmc.ultralucky.rewards;

import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import org.bukkit.entity.Player;

public interface Reward {
    default void forward(Player player, String args) {
        forward(player, args, new RuntimeVariable());
    }

    default void forward(Player player, String args, RuntimeVariable variable) {
        forward(player, args);
    }

    default void register(String tag) {
        RewardsManager.registerReward(tag, this);
    }
}
