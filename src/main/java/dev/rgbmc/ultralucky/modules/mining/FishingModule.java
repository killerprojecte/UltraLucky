package dev.rgbmc.ultralucky.modules.mining;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.conditions.ConditionsParser;
import dev.rgbmc.ultralucky.modules.Module;
import dev.rgbmc.ultralucky.rewards.RewardsManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerFishEvent;

public class FishingModule implements Module {
    @Override
    public String getName() {
        return "Fishing";
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
    public void onFishing(PlayerFishEvent event) {
        if (event.isCancelled()) return;
        if (event.getCaught() == null) return;
        for (String key : getConfigManager().getConfig().getConfigurationSection("fishing").getKeys(false)) {
            ConfigurationSection section = getConfigManager().getConfig().getConfigurationSection("fishing." + key);
            ConditionsParser.checkConditions(section.getStringList("conditions"), event.getPlayer().getInventory().getItemInMainHand(), event.getPlayer()).thenAcceptAsync(status -> {
                if (status)
                    Bukkit.getScheduler().runTask(UltraLucky.instance, () -> RewardsManager.forwardRewards(section.getStringList("rewards"), event.getPlayer()));
            });
        }
    }
}
