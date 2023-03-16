package dev.rgbmc.ultralucky.modules.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.block_conditions.BlockConditionsParser;
import dev.rgbmc.ultralucky.block_rewards.BlockRewardsParser;
import dev.rgbmc.ultralucky.conditions.ConditionsParser;
import dev.rgbmc.ultralucky.modules.Module;
import dev.rgbmc.ultralucky.rewards.RewardsManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractBlockModule implements Module {
    @Override
    public String getName() {
        return "InteractBlock";
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
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.isCancelled()) return;
        for (String key : getConfigManager().getConfig().getConfigurationSection("interact").getKeys(false)) {
            ConfigurationSection section = getConfigManager().getConfig().getConfigurationSection("interact." + key);
            ConditionsParser.checkConditions(section.getStringList("conditions"), event.getItem(), player)
                    .thenAcceptAsync(status -> {
                        if (status) {
                            if (BlockConditionsParser.checkConditions(section.getStringList("block_conditions"), event.getClickedBlock(), event.getPlayer())) {
                                Bukkit.getScheduler().runTask(UltraLucky.instance, () -> {
                                    RewardsManager.forwardRewards(section.getStringList("rewards"), player);
                                    BlockRewardsParser.forwardRewards(section.getStringList("block_rewards"), event.getClickedBlock(), player);

                                });
                            }
                        }
                    });
        }
    }
}
