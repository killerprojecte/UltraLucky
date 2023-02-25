package dev.rgbmc.ultralucky.entity_conditions.impl;

import dev.rgbmc.ultralucky.entity_conditions.EntityCondition;
import dev.rgbmc.ultralucky.utils.Color;
import org.bukkit.entity.LivingEntity;

public class NameCondition implements EntityCondition {
    @Override
    public boolean parse(LivingEntity entity, String args) {
        if (entity.getCustomName() == null) return false;
        return entity.getCustomName().equals(Color.color(args));
    }
}
