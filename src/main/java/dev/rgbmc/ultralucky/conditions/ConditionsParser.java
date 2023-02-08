package dev.rgbmc.ultralucky.conditions;

import com.google.common.collect.Maps;
import dev.rgbmc.ultralucky.conditions.impl.NameCondition;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConditionsParser {
    private static Map<String, Condition> conditionMap = new HashMap<String, Condition>(){
        {
            put("name", new NameCondition());
        }
    };
    public static boolean checkConditions(List<String> conditions, ItemStack item, Player player){
        root:
        for (String line : conditions){
            for (Map.Entry<String, Condition> entry : conditionMap.entrySet()){
                String prefix = "[" + entry.getKey() + "] ";
                if (line.startsWith(prefix)){
                    line = line.substring(prefix.length());
                    if (!entry.getValue().parse(item, player, line)) return false;
                    continue root;
                }
            }
        }
        return true;
    }
}
