package dev.rgbmc.ultralucky.modules.mining;

import dev.rgbmc.ultralucky.conditions.ConditionsParser;
import dev.rgbmc.ultralucky.modules.Module;
import dev.rgbmc.ultralucky.rewards.RewardsManager;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class EatModule implements Module {
    @Override
    public String getName() {
        return "Eat";
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
    public void onEating(PlayerItemConsumeEvent event) {
        if (event.isCancelled()) return;
        if (event.getItem().getType().equals(Material.AIR)) return;
        for (String key : getConfigManager().getConfig().getConfigurationSection("eat").getKeys(false)) {
            ConfigurationSection section = getConfigManager().getConfig().getConfigurationSection("eat." + key);
            if (!(section.getStringList("materials").contains(event.getItem().getType().toString().toLowerCase()) ||
                    section.getStringList("materials").contains(event.getItem().getType().toString().toUpperCase())))
                continue;
            if (!ConditionsParser.checkConditions(section.getStringList("conditions"), event.getItem(), event.getPlayer()))
                continue;
            RewardsManager.forwardRewards(section.getStringList("rewards"), event.getPlayer());
        }
    }
}
