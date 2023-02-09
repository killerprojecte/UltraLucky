package dev.rgbmc.ultralucky.modules.mining;

import dev.rgbmc.ultralucky.conditions.ConditionsParser;
import dev.rgbmc.ultralucky.modules.Module;
import dev.rgbmc.ultralucky.rewards.RewardsManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityTameEvent;

public class TameModule implements Module {
    @Override
    public String getName() {
        return "Tame";
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
    public void onTamed(EntityTameEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getOwner() instanceof Player)) return;
        Player player = (Player) event.getOwner();
        for (String key : getConfigManager().getConfig().getConfigurationSection("tame").getKeys(false)) {
            ConfigurationSection section = getConfigManager().getConfig().getConfigurationSection("tame." + key);
            if (!(section.getStringList("entities").contains(event.getEntity().getType().toString().toLowerCase()) ||
                    section.getStringList("entities").contains(event.getEntity().getType().toString().toUpperCase())))
                continue;
            if (!ConditionsParser.checkConditions(section.getStringList("conditions"), player.getInventory().getItemInMainHand(), player))
                continue;
            RewardsManager.forwardRewards(section.getStringList("rewards"), player);
        }
    }
}
