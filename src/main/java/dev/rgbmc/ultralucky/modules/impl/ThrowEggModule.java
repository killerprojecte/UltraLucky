package dev.rgbmc.ultralucky.modules.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.conditions.ConditionsParser;
import dev.rgbmc.ultralucky.fastconfig.Section;
import dev.rgbmc.ultralucky.modules.Module;
import dev.rgbmc.ultralucky.rewards.RewardsManager;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import org.bukkit.Bukkit;
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
        for (String key : getConfigManager().getConfig().getSection("throwing").getKeys(false)) {
            Section section = getConfigManager().getConfig().getSection("throwing." + key);
            if (section.getBoolean("hatching") && !event.isHatching()) continue;
            if (section.getStringList("hatching_type").stream().noneMatch(type -> event.getHatchingType().toString().equalsIgnoreCase(type)))
                continue;
            RuntimeVariable variable = new RuntimeVariable();
            variable.put("world_name", event.getPlayer().getWorld().getName());
            variable.put("player_name", event.getPlayer().getName());
            variable.put("hatching_type", event.getHatchingType().toString().toUpperCase());
            boolean status = ConditionsParser.checkConditions(section.getStringList("conditions"), event.getPlayer().getInventory().getItemInMainHand(), event.getPlayer(), variable);
            if (status)
                Bukkit.getScheduler().runTask(UltraLucky.instance, () -> RewardsManager.forwardRewards(section.getStringList("rewards"), event.getPlayer(), variable));
        }
    }
}
