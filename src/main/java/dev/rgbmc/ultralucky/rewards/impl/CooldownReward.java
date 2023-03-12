package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.conditions.impl.CooldownCondition;
import dev.rgbmc.ultralucky.rewards.Reward;
import org.bukkit.entity.Player;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CooldownReward implements Reward {

    private static final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(2);

    @Override
    public void forward(Player player, String args) {
        long second = Long.parseLong(args);
        CooldownCondition.cool_downs.put(player.getUniqueId(), second);
        executorService.schedule(() -> {
            CooldownCondition.cool_downs.remove(player.getUniqueId());
        }, second, TimeUnit.SECONDS);
    }
}
