package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.fastconfig.FastConfig;
import dev.rgbmc.ultralucky.rewards.Reward;
import dev.rgbmc.ultralucky.rewards.RewardsManager;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RewardsGroupReward implements Reward {
    private static FastConfig config = null;

    public static void reloadConfig() {
        if (config == null) {
            config = new FastConfig();
        }
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(new File(UltraLucky.instance.getDataFolder(), "RewardsGroup.yml"));
        config.refreshPoint(configuration);
    }

    @Override
    public void forward(Player player, String args, RuntimeVariable variable) {
        args = variable.evalVariables(args);
        if (config == null) {
            reloadConfig();
        }
        if (!config.contains("group." + args)) {
            UltraLucky.instance.getLogger().warning("无法找到奖励组: " + args);
            return;
        }
        List<String> reward = new ArrayList<>();
        if (config.getBoolean("group." + args + ".random")) {
            List<String> temp_list = config.getStringList("group." + args + ".rewards");
            Collections.shuffle(temp_list);
            reward.add(temp_list.get(0));
        } else {
            reward.addAll(config.getStringList("group." + args + ".rewards"));
        }
        variable.put("group_name", args);
        RewardsManager.forwardRewards(reward, player, variable);
    }
}
