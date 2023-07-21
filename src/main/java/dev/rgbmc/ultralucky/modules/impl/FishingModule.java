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

    @EventHandler(priority = EventPriority.MONITOR)
    public void onFishing(PlayerFishEvent event) {
        if (event.isCancelled()) return;
        if (event.getCaught() == null) return;
        for (String key : getConfigManager().getConfig().getSection("fishing").getKeys(false)) {
            Section section = getConfigManager().getConfig().getSection("fishing." + key);
            if (section.getStringList("types").stream().noneMatch(s -> s.toUpperCase().equals(event.getCaught().getType().toString())))
                continue;
            RuntimeVariable variable = new RuntimeVariable();
            variable.put("world_name", event.getPlayer().getWorld().getName());
            variable.put("player_name", event.getPlayer().getName());
            variable.put("fish_type", event.getCaught().getType().toString());
            variable.put("fish_name", event.getCaught().getName());
            boolean status = ConditionsParser.checkConditions(section.getStringList("conditions"), event.getPlayer().getInventory().getItemInMainHand(), event.getPlayer(), variable);
            if (status)
                Bukkit.getScheduler().runTask(UltraLucky.instance, () -> RewardsManager.forwardRewards(section.getStringList("rewards"), event.getPlayer(), variable));
        }
    }
}
