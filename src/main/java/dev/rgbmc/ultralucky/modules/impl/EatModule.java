package dev.rgbmc.ultralucky.modules.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.conditions.ConditionsParser;
import dev.rgbmc.ultralucky.fastconfig.Section;
import dev.rgbmc.ultralucky.modules.Module;
import dev.rgbmc.ultralucky.rewards.RewardsManager;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
        for (String key : getConfigManager().getConfig().getSection("eat").getKeys(false)) {
            Section section = getConfigManager().getConfig().getSection("eat." + key);
            if (!(section.getStringList("materials").contains(event.getItem().getType().toString().toLowerCase()) ||
                    section.getStringList("materials").contains(event.getItem().getType().toString().toUpperCase())))
                continue;
            RuntimeVariable variable = new RuntimeVariable();
            variable.put("world_name", event.getPlayer().getWorld().getName());
            variable.put("player_name", event.getPlayer().getName());
            variable.put("food_type", event.getItem().getType().toString().toUpperCase());
            if (event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasDisplayName()) {
                variable.put("food_name", event.getItem().getItemMeta().getDisplayName());
            }
            boolean status = ConditionsParser.checkConditions(section.getStringList("conditions"), event.getItem(), event.getPlayer(), variable);
            if (status)
                Bukkit.getScheduler().runTask(UltraLucky.instance, () -> RewardsManager.forwardRewards(section.getStringList("rewards"), event.getPlayer(), variable));
        }
    }
}
