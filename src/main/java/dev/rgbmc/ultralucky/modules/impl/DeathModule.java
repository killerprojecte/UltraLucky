package dev.rgbmc.ultralucky.modules.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.conditions.ConditionsParser;
import dev.rgbmc.ultralucky.entity_conditions.EntityConditionParser;
import dev.rgbmc.ultralucky.fastconfig.Section;
import dev.rgbmc.ultralucky.modules.Module;
import dev.rgbmc.ultralucky.rewards.RewardsManager;
import dev.rgbmc.ultralucky.utils.DynamicStorage;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

public class DeathModule implements Module {
    @Override
    public String getName() {
        return "Death";
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
    public void onDeath(EntityDeathEvent event) {
        if (!(event.getEntity().getKiller() instanceof Player)) return;
        Player player = event.getEntity().getKiller();
        for (String key : getConfigManager().getConfig().getSection("death").getKeys(false)) {
            Section section = getConfigManager().getConfig().getSection("death." + key);
            if (section.getStringList("types").stream().noneMatch(t -> t.equalsIgnoreCase(event.getEntity().getType().toString())))
                return;
            RuntimeVariable variable = new RuntimeVariable();
            variable.put("world_name", player.getWorld().getName());
            variable.put("player_name", player.getName());
            variable.put("target_type", event.getEntity().getType().toString().toUpperCase());
            variable.put("target_name", event.getEntity().getName());
            variable.put("target_location_x", String.valueOf(event.getEntity().getLocation().getBlockX()));
            variable.put("target_location_y", String.valueOf(event.getEntity().getLocation().getBlockY()));
            variable.put("target_location_z", String.valueOf(event.getEntity().getLocation().getBlockZ()));
            boolean status = ConditionsParser.checkConditions(section.getStringList("conditions"), player.getItemInHand(), player, variable);
            if (status) {
                Bukkit.getScheduler().runTask(UltraLucky.instance, () -> {
                    if (!EntityConditionParser.checkConditions(section.getStringList("entity_conditions"), event.getEntity()))
                        return;
                    DynamicStorage.storage(player, "target", "death_" + key, event.getEntity());
                    RewardsManager.forwardRewards(section.getStringList("rewards"), player, variable);
                });
            }
        }
    }
}
