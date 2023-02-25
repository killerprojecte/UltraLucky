package dev.rgbmc.ultralucky.entity_conditions.impl;

import dev.rgbmc.ultralucky.entity_conditions.EntityCondition;
import org.bukkit.entity.LivingEntity;

public class BootsTypeCondition implements EntityCondition {
    @Override
    public boolean parse(LivingEntity entity, String args) {
        if (entity.getEquipment() == null) return false;
        if (entity.getEquipment().getBoots() == null) return false;
        return entity.getEquipment().getBoots().getType().toString().equalsIgnoreCase(args);
    }
}
