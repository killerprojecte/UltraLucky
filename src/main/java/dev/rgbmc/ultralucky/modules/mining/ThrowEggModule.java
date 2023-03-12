package dev.rgbmc.ultralucky.modules.mining;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.conditions.ConditionsParser;
import dev.rgbmc.ultralucky.modules.Module;
import dev.rgbmc.ultralucky.rewards.RewardsManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerEggThrowEvent;

public class ThrowEggModule implements Module {
    @Override
    public String getName() {
        return "ThrowEgg";
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
    public void onThrowEgg(PlayerEggThrowEvent event) {
        for (String key : getConfigManager().getConfig().getConfigurationSection("throwing").getKeys(false)) {
            ConfigurationSection section = getConfigManager().getConfig().getConfigurationSection("throwing." + key);
            if (section.getBoolean("hatching") && !event.isHatching()) continue;
            if (section.getStringList("hatching_type").stream().noneMatch(type -> event.getHatchingType().toString().equalsIgnoreCase(type)))
                continue;
            ConditionsParser.checkConditions(section.getStringList("conditions"), event.getPlayer().getInventory().getItemInMainHand(), event.getPlayer()).thenAcceptAsync(status -> {
                if (status)
                    Bukkit.getScheduler().runTask(UltraLucky.instance, () -> RewardsManager.forwardRewards(section.getStringList("rewards"), event.getPlayer()));
            });
        }
    }
}
