package dev.rgbmc.ultralucky.block_rewards.impl;

import dev.rgbmc.ultralucky.block_rewards.BlockReward;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class SetTypeReward implements BlockReward {
    @Override
    public void forward(Block block, Player player, String args) {
        block.setType(Material.getMaterial(args.toUpperCase()));
    }
}
