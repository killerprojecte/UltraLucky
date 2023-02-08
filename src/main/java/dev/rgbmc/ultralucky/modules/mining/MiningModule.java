package dev.rgbmc.ultralucky.modules.mining;

import dev.rgbmc.ultralucky.conditions.ConditionsParser;
import dev.rgbmc.ultralucky.modules.Module;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

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

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMine(BlockBreakEvent event){
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        ItemStack pickaxe = event.getPlayer().getInventory().getItemInMainHand();
        for (String key : getConfig().getConfigurationSection("mining").getKeys(false)){
            if (!ConditionsParser.checkConditions(getConfig().getStringList("mining." + key + "conditions"), pickaxe, player)) continue;

        }
    }
}
