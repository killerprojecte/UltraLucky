package dev.rgbmc.ultralucky.modules.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.block_conditions.BlockConditionsParser;
import dev.rgbmc.ultralucky.block_rewards.BlockRewardsParser;
import dev.rgbmc.ultralucky.conditions.ConditionsParser;
import dev.rgbmc.ultralucky.fastconfig.Section;
import dev.rgbmc.ultralucky.modules.Module;
import dev.rgbmc.ultralucky.rewards.RewardsManager;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import org.bukkit.Bukkit;
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
        for (String key : getConfigManager().getConfig().getSection("interact").getKeys(false)) {
            Section section = getConfigManager().getConfig().getSection("interact." + key);
            RuntimeVariable variable = new RuntimeVariable();
            variable.put("world_name", player.getWorld().getName());
            variable.put("player_name", player.getName());
            variable.put("block_type", event.getClickedBlock().getType().toString().toUpperCase());
            variable.put("interact_type", event.getAction().toString().toUpperCase());
            boolean status = ConditionsParser.checkConditions(section.getStringList("conditions"), event.getItem(), player, variable);
            if (status) {
                if (BlockConditionsParser.checkConditions(section.getStringList("block_conditions"), event.getClickedBlock(), event.getPlayer())) {
                    Bukkit.getScheduler().runTask(UltraLucky.instance, () -> {
                        RewardsManager.forwardRewards(section.getStringList("rewards"), player, variable);
                        BlockRewardsParser.forwardRewards(section.getStringList("block_rewards"), event.getClickedBlock(), player);

                    });
                }
            }
        }
    }
}
