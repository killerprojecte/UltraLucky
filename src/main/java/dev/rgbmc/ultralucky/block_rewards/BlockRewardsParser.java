package dev.rgbmc.ultralucky.block_rewards;

import dev.rgbmc.ultralucky.block_rewards.impl.SetTypeReward;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockRewardsParser {
    private static final Map<String, BlockReward> conditionMap = new HashMap<String, BlockReward>() {
        {
            put("set_type", new SetTypeReward());
        }
    };

    public static void forwardRewards(List<String> conditions, Block block, Player player) {
        for (String line : conditions) {
            for (Map.Entry<String, BlockReward> entry : conditionMap.entrySet()) {
                String prefix = "[" + entry.getKey() + "] ";
                if (line.startsWith(prefix)) {
                    line = line.substring(prefix.length());
                    entry.getValue().forward(block, player, line);
                }
            }
        }
    }
}
