package dev.rgbmc.ultralucky.entity_conditions.impl;

import dev.rgbmc.ultralucky.entity_conditions.EntityCondition;
import org.bukkit.entity.LivingEntity;

public class OffHandTypeCondition implements EntityCondition {
    @Override
    public boolean parse(LivingEntity entity, String args) {
        if (entity.getEquipment() == null) return false;
        return entity.getEquipment().getItemInOffHand().getType().toString().equalsIgnoreCase(args);
    }
}
