package dev.rgbmc.ultralucky.modules.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.conditions.ConditionsParser;
import dev.rgbmc.ultralucky.fastconfig.Section;
import dev.rgbmc.ultralucky.fastindex.FastIndex;
import dev.rgbmc.ultralucky.fastindex.IndexData;
import dev.rgbmc.ultralucky.fastindex.IndexState;
import dev.rgbmc.ultralucky.modules.Module;
import dev.rgbmc.ultralucky.rewards.RewardsManager;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

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
    public void onMine(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        ItemStack pickaxe = event.getPlayer().getInventory().getItemInMainHand();
        for (String key : getConfigManager().getConfig().getSection("mining").getKeys(false)) {
            Section section = getConfigManager().getConfig().getSection("mining." + key);
            if (!(section.getStringList("materials").contains(event.getBlock().getType().toString().toLowerCase()) ||
                    section.getStringList("materials").contains(event.getBlock().getType().toString().toUpperCase()) || section.getStringList("materials").contains("*")))
                continue;
            Runnable runnable = () -> {
                RuntimeVariable variable = new RuntimeVariable();
                variable.put("world_name", player.getWorld().getName());
                variable.put("player_name", player.getName());
                variable.put("block_type", event.getBlock().getType().toString().toUpperCase());
                variable.put("location_x", String.valueOf(event.getBlock().getLocation().getBlockX()));
                variable.put("location_y", String.valueOf(event.getBlock().getLocation().getBlockY()));
                variable.put("location_z", String.valueOf(event.getBlock().getLocation().getBlockZ()));
                boolean status = ConditionsParser.checkConditions(section.getStringList("conditions"), pickaxe, player, variable);
                if (status) {
                    if (section.getBoolean("prevent-drop")) {
                        event.setDropItems(false);
                    }
                    Bukkit.getScheduler().runTask(UltraLucky.instance, () -> RewardsManager.forwardRewards(section.getStringList("rewards"), player, variable));
                } else if (section.getBoolean("cancel")) {
                    if (ConditionsParser.checkConditions(Collections.singletonList(section.getStringList("conditions").get(0)), pickaxe, player, variable)) {
                        event.setCancelled(true);
                    }
                }
            };
            if (section.getBoolean("prevent-replace")) {
                if (FastIndex.isIndexed(event.getBlock())) {
                    IndexData indexData = FastIndex.getIndex(event.getBlock());
                    //System.out.println(indexData.getState().toString());
                    if (indexData.getState() != IndexState.INIT) return;
                }
                runnable.run();
            } else {
                runnable.run();
            }
        }
    }
}
