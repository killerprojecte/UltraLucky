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
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AttackModule implements Module {
    @Override
    public String getName() {
        return "Attack";
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
    public void onAttackEntity(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getEntity() instanceof LivingEntity)) return;
        if (!(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();
        for (String key : getConfigManager().getConfig().getSection("attack").getKeys(false)) {
            Section section = getConfigManager().getConfig().getSection("attack." + key);
            if (section.getStringList("types").stream().noneMatch(t -> t.equalsIgnoreCase(event.getEntity().getType().toString())))
                return;
            RuntimeVariable variable = new RuntimeVariable();
            variable.put("world_name", player.getWorld().getName());
            variable.put("player_name", player.getName());
            variable.put("target_type", event.getEntity().getType().toString().toUpperCase());
            variable.put("target_name", event.getEntity().getName());
            boolean status = ConditionsParser.checkConditions(section.getStringList("conditions"), player.getItemInHand(), player, variable);
            if (status) {
                Bukkit.getScheduler().runTask(UltraLucky.instance, () -> {
                    if (!EntityConditionParser.checkConditions(section.getStringList("entity_conditions"), (LivingEntity) event.getEntity()))
                        return;
                    DynamicStorage.storage(player, "damage", "attack_" + key, event.getDamage());
                    DynamicStorage.storage(player, "final_damage", "attack_" + key, event.getFinalDamage());
                    DynamicStorage.storage(player, "target", "attack_" + key, event.getEntity());
                    RewardsManager.forwardRewards(section.getStringList("rewards"), player, variable);
                });
            }
        }
    }
}
