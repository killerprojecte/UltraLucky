package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.rewards.Reward;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.AbstractLocation;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class MythicMobs4Reward implements Reward {
    @Override
    public void forward(Player player, String args) {
        String[] param = PlaceholderAPI.setPlaceholders(player, args).split(",");
        AbstractLocation location = BukkitAdapter.adapt(player.getLocation());
        if (MythicMobs.inst().getMobManager().getMythicMob(param[0]) == null) {
            UltraLucky.instance.getLogger().warning("无法从MythicMobs 4.x中找到 怪物: " + param[0]);
            return;
        }
        MythicMobs.inst().getMobManager().getMythicMob(param[0]).spawn(location, Integer.parseInt(param[1]));
    }
}
