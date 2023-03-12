package dev.rgbmc.ultralucky.block_conditions;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface BlockCondition {
    boolean parse(Block block, String args, Player player);
}
