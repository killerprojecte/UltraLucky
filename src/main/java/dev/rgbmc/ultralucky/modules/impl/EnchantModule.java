package dev.rgbmc.ultralucky.modules.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.conditions.ConditionsParser;
import dev.rgbmc.ultralucky.modules.Module;
import dev.rgbmc.ultralucky.rewards.RewardsManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class EnchantModule implements Module {
    @Override
    public String getName() {
        return "Enchant";
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
    public void onEnchanting(EnchantItemEvent event) {
        if (event.isCancelled()) return;
        for (String key : getConfigManager().getConfig().getConfigurationSection("enchant").getKeys(false)) {
            ConfigurationSection section = getConfigManager().getConfig().getConfigurationSection("enchant." + key);
            ConditionsParser.checkConditions(section.getStringList("conditions"), event.getItem(), event.getEnchanter())
                    .thenAcceptAsync(status -> {
                        if (status)
                            Bukkit.getScheduler().runTask(UltraLucky.instance, () -> RewardsManager.forwardRewards(section.getStringList("rewards"), event.getEnchanter()));
                    });
        }
    }
}
