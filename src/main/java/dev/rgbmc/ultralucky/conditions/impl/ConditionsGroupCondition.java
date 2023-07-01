package dev.rgbmc.ultralucky.conditions.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.conditions.Condition;
import dev.rgbmc.ultralucky.conditions.ConditionsParser;
import dev.rgbmc.ultralucky.fastconfig.FastConfig;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;

public class ConditionsGroupCondition implements Condition {
    private static FastConfig config = null;

    public static void reloadConfig() {
        if (config == null) {
            config = new FastConfig();
        }
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(new File(UltraLucky.instance.getDataFolder(), "RewardsGroup.yml"));
        config.refreshPoint(configuration);
    }

    @Override
    public boolean parse(ItemStack item, Player player, String args, RuntimeVariable variable) {
        args = variable.evalVariables(args);
        if (config == null) {
            reloadConfig();
        }
        if (!config.contains("group." + args)) {
            UltraLucky.instance.getLogger().warning("无法找到条件组: " + args);
            return false;
        }
        List<String> conditions = config.getStringList("group." + args);
        variable.put("check_group_name", args);
        return ConditionsParser.checkConditions(conditions, item, player, variable);
    }
}
