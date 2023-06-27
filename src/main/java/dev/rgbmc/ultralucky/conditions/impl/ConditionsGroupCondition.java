package dev.rgbmc.ultralucky.conditions.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.conditions.Condition;
import dev.rgbmc.ultralucky.conditions.ConditionsParser;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;

public class ConditionsGroupCondition implements Condition {
    @Override
    public boolean parse(ItemStack item, Player player, String args) {
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(new File(UltraLucky.instance.getDataFolder(), "RewardsGroup.yml"));
        if (!configuration.contains("group." + args, true)) {
            UltraLucky.instance.getLogger().warning("无法找到条件组: " + args);
            return false;
        }
        List<String> conditions = configuration.getStringList("group." + args);
        return ConditionsParser.checkConditions(conditions, item, player);
    }
}
