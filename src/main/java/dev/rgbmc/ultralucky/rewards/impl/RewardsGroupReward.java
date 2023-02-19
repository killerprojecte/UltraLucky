package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.rewards.Reward;
import dev.rgbmc.ultralucky.rewards.RewardsManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RewardsGroupReward implements Reward {
    @Override
    public void forward(Player player, String args) {
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(new File(UltraLucky.instance.getDataFolder(), "RewardsGroup.yml"));
        if (!configuration.contains("group." + args, true)) {
            UltraLucky.instance.getLogger().warning("无法找到奖励组: " + args);
            return;
        }
        List<String> reward = new ArrayList<>();
        if (configuration.getBoolean("group." + args + ".random")) {
            List<String> temp_list = configuration.getStringList("group." + args + ".rewards");
            Collections.shuffle(temp_list);
            reward.add(temp_list.get(0));
        } else {
            reward.addAll(configuration.getStringList("group." + args + ".rewards"));
        }
        RewardsManager.forwardRewards(reward.stream().map(s -> s.replace("$group", args)).collect(Collectors.toList()), player);
    }
}
