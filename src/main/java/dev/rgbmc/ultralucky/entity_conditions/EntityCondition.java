package dev.rgbmc.ultralucky.entity_conditions;

import org.bukkit.entity.LivingEntity;

public interface EntityCondition {
    boolean parse(LivingEntity entity, String args);
}
