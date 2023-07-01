package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.conditions.impl.CooldownCondition;
import dev.rgbmc.ultralucky.rewards.Reward;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CooldownReward implements Reward {

    private static final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(2);

    @Override
    public void forward(Player player, String args, RuntimeVariable variable) {
        args = variable.evalVariables(args);
        String[] param = args.split(" ");
        long second = Long.parseLong(param[1]);
        Map<UUID, Long> data;
        if (CooldownCondition.cool_downs.containsKey(param[0])) {
            data = CooldownCondition.cool_downs.get(param[0]);
        } else {
            data = new HashMap<>();
        }
        data.put(player.getUniqueId(), second);
        CooldownCondition.cool_downs.put(param[0], data);
        executorService.schedule(() -> {
            Map<UUID, Long> map = CooldownCondition.cool_downs.get(param[0]);
            map.remove(player.getUniqueId());
            CooldownCondition.cool_downs.put(param[0], data);
        }, second, TimeUnit.SECONDS);
    }
}
