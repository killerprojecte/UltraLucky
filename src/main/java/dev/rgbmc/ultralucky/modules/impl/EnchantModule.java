package dev.rgbmc.ultralucky.modules.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.conditions.ConditionsParser;
import dev.rgbmc.ultralucky.fastconfig.Section;
import dev.rgbmc.ultralucky.modules.Module;
import dev.rgbmc.ultralucky.rewards.RewardsManager;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import org.bukkit.Bukkit;
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
        for (String key : getConfigManager().getConfig().getSection("enchant").getKeys(false)) {
            Section section = getConfigManager().getConfig().getSection("enchant." + key);
            RuntimeVariable variable = new RuntimeVariable();
            variable.put("world_name", event.getEnchanter().getWorld().getName());
            variable.put("player_name", event.getEnchanter().getName());
            variable.put("enchant_cost", String.valueOf(event.getExpLevelCost()));
            boolean status = ConditionsParser.checkConditions(section.getStringList("conditions"), event.getItem(), event.getEnchanter(), variable);
            if (status)
                Bukkit.getScheduler().runTask(UltraLucky.instance, () -> RewardsManager.forwardRewards(section.getStringList("rewards"), event.getEnchanter(), variable));
        }
    }
}
