package dev.rgbmc.ultralucky.modules.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.conditions.ConditionsParser;
import dev.rgbmc.ultralucky.fastconfig.Section;
import dev.rgbmc.ultralucky.modules.Module;
import dev.rgbmc.ultralucky.rewards.RewardsManager;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class TimerModule implements Module {
    private BukkitTask task = null;
    private long currentTick = 0;

    @Override
    public String getName() {
        return "Timer";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getAuthor() {
        return "Official";
    }

    @Override
    public void unregister() {
        Module.super.unregister();
        task.cancel();
    }

    @Override
    public void register() {
        Module.super.register();
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(UltraLucky.instance, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                for (String key : getConfigManager().getConfig().getSection("mining").getKeys(false)) {
                    Section section = getConfigManager().getConfig().getSection("mining." + key);
                    if (((double) currentTick / (double) section.getLong("period")) % 1 != 0) continue;
                    RuntimeVariable variable = new RuntimeVariable();
                    variable.put("world_name", player.getWorld().getName());
                    variable.put("player_name", player.getName());
                    boolean status = ConditionsParser.checkConditions(section.getStringList("conditions"), player.getItemInHand(), player, variable);
                    if (status) {
                        Bukkit.getScheduler().runTask(UltraLucky.instance, () -> RewardsManager.forwardRewards(section.getStringList("rewards"), player, variable));
                    }
                }
            }
            currentTick++;
        }, 1L, 1L);
    }
}
