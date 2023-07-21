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
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatModule implements Module {
    @Override
    public String getName() {
        return "Chat";
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
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) return;
        for (String key : getConfigManager().getConfig().getSection("chat").getKeys(false)) {
            Section section = getConfigManager().getConfig().getSection("chat." + key);
            RuntimeVariable variable = new RuntimeVariable();
            variable.put("world_name", event.getPlayer().getWorld().getName());
            variable.put("player_name", event.getPlayer().getName());
            variable.put("location_x", String.valueOf(event.getPlayer().getLocation().getX()));
            variable.put("location_y", String.valueOf(event.getPlayer().getLocation().getY()));
            variable.put("location_z", String.valueOf(event.getPlayer().getLocation().getZ()));
            variable.put("message", event.getMessage());
            variable.put("formatted", event.getFormat());
            boolean status = ConditionsParser.checkConditions(section.getStringList("conditions"), event.getPlayer().getInventory().getItemInHand(), event.getPlayer(), variable);
            if (status)
                Bukkit.getScheduler().runTask(UltraLucky.instance, () -> RewardsManager.forwardRewards(section.getStringList("rewards"), event.getPlayer(), variable));
        }
    }
}
