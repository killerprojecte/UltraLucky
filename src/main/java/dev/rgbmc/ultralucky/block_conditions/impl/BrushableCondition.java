package dev.rgbmc.ultralucky.block_conditions.impl;

import dev.rgbmc.ultralucky.block_conditions.BlockCondition;
import org.bukkit.block.Block;
import org.bukkit.block.BrushableBlock;
import org.bukkit.entity.Player;

public class BrushableCondition implements BlockCondition {
    @Override
    public boolean parse(Block block, String args, Player player) {
        return (block instanceof BrushableBlock);
    }
}
