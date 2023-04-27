package dev.rgbmc.ultralucky.modules.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.conditions.ConditionsParser;
import dev.rgbmc.ultralucky.entity_conditions.EntityConditionParser;
import dev.rgbmc.ultralucky.modules.Module;
import dev.rgbmc.ultralucky.rewards.RewardsManager;
import dev.rgbmc.ultralucky.utils.DynamicStorage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
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
        for (String key : getConfigManager().getConfig().getConfigurationSection("attack").getKeys(false)) {
            ConfigurationSection section = getConfigManager().getConfig().getConfigurationSection("attack." + key);
            if (section.getStringList("types").stream().noneMatch(t -> t.equalsIgnoreCase(event.getEntity().getType().toString())))
                return;
            ConditionsParser.checkConditions(section.getStringList("conditions"), player.getItemInHand(), player).thenAcceptAsync(status -> {
                if (status) {
                    Bukkit.getScheduler().runTask(UltraLucky.instance, () -> {
                        if (!EntityConditionParser.checkConditions(section.getStringList("entity_conditions"), (LivingEntity) event.getEntity()))
                            return;
                        DynamicStorage.storage(player, "damage", "attack_" + key, event.getDamage());
                        DynamicStorage.storage(player, "final_damage", "attack_" + key, event.getFinalDamage());
                        DynamicStorage.storage(player, "target", "attack_" + key, event.getEntity());
                        RewardsManager.forwardRewards(section.getStringList("rewards"), player);
                    });
                }
            });
        }
    }
}
