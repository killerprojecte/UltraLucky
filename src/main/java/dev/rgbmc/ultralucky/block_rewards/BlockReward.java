package dev.rgbmc.ultralucky.block_rewards;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface BlockReward {
    void forward(Block block, Player player, String args);
}
