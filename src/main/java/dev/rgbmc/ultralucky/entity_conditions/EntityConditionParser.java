package dev.rgbmc.ultralucky.entity_conditions;

import dev.rgbmc.ultralucky.entity_conditions.impl.*;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityConditionParser {
    private static final Map<String, EntityCondition> conditionMap = new HashMap<String, EntityCondition>() {
        {
            put("helmet_type", new HelmetTypeCondition());
            put("helmet_name", new HelmetNameCondition());
            put("chestplate_type", new ChestplateTypeCondition());
            put("chestplate_name", new ChestplateNameCondition());
            put("leggings_type", new LeggingsTypeCondition());
            put("leggings_name", new LeggingsNameCondition());
            put("boots_type", new BootsTypeCondition());
            put("boots_name", new BootsNameCondition());
            put("mainhand_type", new MainHandTypeCondition());
            put("mainhand_name", new MainHandNameCondition());
            put("offhand_type", new OffHandTypeCondition());
            put("offhand_name", new OffHandNameCondition());
            put("name", new NameCondition());
        }
    };

    public static boolean checkConditions(List<String> conditions, LivingEntity entity) {
        root:
        for (String line : conditions) {
            for (Map.Entry<String, EntityCondition> entry : conditionMap.entrySet()) {
                String prefix = "[" + entry.getKey() + "] ";
                if (line.startsWith(prefix)) {
                    line = line.substring(prefix.length());
                    if (!entry.getValue().parse(entity, line)) return false;
                    continue root;
                }
            }
        }
        return true;
    }
}
