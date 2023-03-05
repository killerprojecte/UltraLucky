package dev.rgbmc.ultralucky.modules.mining;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.conditions.ConditionsParser;
import dev.rgbmc.ultralucky.modules.Module;
import dev.rgbmc.ultralucky.rewards.RewardsManager;
import dev.rgbmc.ultralucky.utils.AsyncFuture;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
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
        for (String key : getConfigManager().getConfig().getConfigurationSection("mining").getKeys(false)) {
            ConfigurationSection section = getConfigManager().getConfig().getConfigurationSection("mining." + key);
            if (!(section.getStringList("materials").contains(event.getBlock().getType().toString().toLowerCase()) ||
                    section.getStringList("materials").contains(event.getBlock().getType().toString().toUpperCase())))
                continue;
            AsyncFuture<Boolean> asyncFuture = new AsyncFuture<>(() -> UltraLucky.blockStorage.query(event.getBlock().getLocation()));
            Runnable runnable = () -> {
                if (!ConditionsParser.checkConditions(section.getStringList("conditions"), pickaxe, player)) {
                    if (section.getBoolean("cancel") && ConditionsParser.checkConditions(Collections.singletonList(section.getStringList("conditions").get(0)), pickaxe, player)) {
                        event.setCancelled(true);
                    }
                    return;
                }
                if (section.getBoolean("prevent-drop")) {
                    event.setDropItems(false);
                }
                RewardsManager.forwardRewards(section.getStringList("rewards"), player);
            };
            if (section.getBoolean("prevent-replace")) {
                asyncFuture.execute().thenAcceptAsync(status -> {
                    if (!status) {
                        Bukkit.getScheduler().runTask(UltraLucky.instance, runnable);
                    }
                });
            } else {
                runnable.run();
            }
        }
    }
}
