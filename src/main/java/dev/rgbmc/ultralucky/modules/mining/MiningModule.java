package dev.rgbmc.ultralucky.modules.mining;

import dev.rgbmc.ultralucky.modules.Module;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class MiningModule implements Module {
    @Override
    public String getName() {
        return "Mining";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getAuthor() {
        return "Official";
    }

    @EventHandler
    public void onMine(BlockBreakEvent event){

    }
}
