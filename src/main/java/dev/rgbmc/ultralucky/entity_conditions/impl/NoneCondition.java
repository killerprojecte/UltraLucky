package dev.rgbmc.ultralucky.entity_conditions.impl;

import dev.rgbmc.ultralucky.entity_conditions.EntityCondition;
import org.bukkit.entity.LivingEntity;

public class NoneCondition implements EntityCondition {
    @Override
    public boolean parse(LivingEntity entity, String args) {
        return true;
    }
}
