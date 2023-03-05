package dev.rgbmc.ultralucky.entity_conditions.impl;

import dev.rgbmc.ultralucky.entity_conditions.EntityCondition;
import dev.rgbmc.ultralucky.utils.Color;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class LeggingsNameCondition implements EntityCondition {
    @Override
    public boolean parse(LivingEntity entity, String args) {
        if (entity.getEquipment() == null) return false;
        ItemStack item = entity.getEquipment().getLeggings();
        if (item == null) return false;
        if (!item.hasItemMeta()) return false;
        if (!item.getItemMeta().hasDisplayName()) return false;
        return item.getItemMeta().getDisplayName().equalsIgnoreCase(Color.color(args));
    }
}
