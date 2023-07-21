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
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnModule implements Module {
    @Override
    public String getName() {
        return "Respawn";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getAuthor() {
        return "Official";
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        for (String key : getConfigManager().getConfig().getSection("respawn").getKeys(false)) {
            Section section = getConfigManager().getConfig().getSection("respawn." + key);
            RuntimeVariable variable = new RuntimeVariable();
            variable.put("world_name", event.getPlayer().getWorld().getName());
            variable.put("player_name", event.getPlayer().getName());
            variable.put("location_x", String.valueOf(event.getPlayer().getLocation().getX()));
            variable.put("location_y", String.valueOf(event.getPlayer().getLocation().getY()));
            variable.put("location_z", String.valueOf(event.getPlayer().getLocation().getZ()));
            boolean status = ConditionsParser.checkConditions(section.getStringList("conditions"), event.getPlayer().getInventory().getItemInHand(), event.getPlayer(), variable);
            if (status)
                Bukkit.getScheduler().runTask(UltraLucky.instance, () -> RewardsManager.forwardRewards(section.getStringList("rewards"), event.getPlayer(), variable));
        }
    }
}
