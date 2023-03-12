package dev.rgbmc.ultralucky.rewards;

import org.bukkit.entity.Player;

public interface Reward {
    void forward(Player player, String args);

    default void register(String tag) {
        RewardsManager.registerReward(tag, this);
    }
}
