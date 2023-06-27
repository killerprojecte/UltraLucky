package dev.rgbmc.ultralucky.fastindex.listeners;

import dev.rgbmc.ultralucky.fastindex.FastIndex;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        if (event.getPlayer() == null) return;
        if (!event.canBuild()) return;
        //System.out.println("Call");
        FastIndex.updateIndex(event.getBlock());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        if (event.getPlayer() == null) return;
        //System.out.println("Call");
        FastIndex.updateIndex(event.getBlock().getLocation(), Material.AIR);
    }
}
