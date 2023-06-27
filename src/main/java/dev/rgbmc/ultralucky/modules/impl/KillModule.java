package dev.rgbmc.ultralucky.modules.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.conditions.ConditionsParser;
import dev.rgbmc.ultralucky.entity_conditions.EntityConditionParser;
import dev.rgbmc.ultralucky.modules.Module;
import dev.rgbmc.ultralucky.rewards.RewardsManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillModule implements Module {
    @Override
    public String getName() {
        return "Kill";
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
    public void onEntityDead(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        Player killer = event.getEntity().getKiller();
        for (String key : getConfigManager().getConfig().getConfigurationSection("kill").getKeys(false)) {
            ConfigurationSection section = getConfigManager().getConfig().getConfigurationSection("kill." + key);
            if (section.getStringList("types").stream().noneMatch(t -> t.equalsIgnoreCase(event.getEntity().getType().toString())))
                continue;
            boolean status = ConditionsParser.checkConditions(section.getStringList("conditions"), killer.getItemInHand(), killer);
            if (status) {
                Bukkit.getScheduler().runTask(UltraLucky.instance, () -> {
                    if (!EntityConditionParser.checkConditions(section.getStringList("entity_conditions"), event.getEntity()))
                        return;
                    RewardsManager.forwardRewards(section.getStringList("rewards"), killer);
                });
            }
        }
    }
}
