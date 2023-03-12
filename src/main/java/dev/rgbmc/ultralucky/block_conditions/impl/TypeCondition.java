package dev.rgbmc.ultralucky.block_conditions.impl;

import dev.rgbmc.ultralucky.block_conditions.BlockCondition;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class TypeCondition implements BlockCondition {
    @Override
    public boolean parse(Block block, String args, Player player) {
        return block.getType().equals(Material.getMaterial(args.toUpperCase()));
    }
}
