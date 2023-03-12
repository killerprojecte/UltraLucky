package dev.rgbmc.ultralucky.block_conditions;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.block_conditions.impl.LocationCondition;
import dev.rgbmc.ultralucky.block_conditions.impl.TypeCondition;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockConditionsParser {
    private static final Map<String, BlockCondition> conditionMap = new HashMap<String, BlockCondition>() {
        {
            put("loc", new LocationCondition());
            put("type", new TypeCondition());
        }
    };

    public static boolean checkConditions(List<String> conditions, Block block, Player player) {
        root:
        for (String line : conditions) {
            for (Map.Entry<String, BlockCondition> entry : conditionMap.entrySet()) {
                String prefix = "[" + entry.getKey() + "] ";
                if (line.startsWith(prefix)) {
                    line = line.substring(prefix.length());
                    if (!entry.getValue().parse(block, line, player)) return false;
                    continue root;
                }
            }
        }
        return true;
    }

    public static void registerEntityCondition(String tag, BlockCondition condition) {
        UltraLucky.instance.getLogger().info("[!] 注册外部方块条件 TAG: " + tag + " 路径: " + condition.getClass().getName());
        conditionMap.put(tag, condition);
    }
}
