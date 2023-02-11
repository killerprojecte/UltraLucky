package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.rewards.Reward;
import io.lumine.mythic.api.adapters.AbstractLocation;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.entity.Player;

import java.util.Optional;

public class MythicMobs5Reward implements Reward {
    @Override
    public void forward(Player player, String args) {
        String[] param = args.split(",");
        AbstractLocation location = BukkitAdapter.adapt(player.getLocation());
        Optional<MythicMob> optionalMythicMob = MythicBukkit.inst().getMobManager().getMythicMob(param[0]);
        if (optionalMythicMob.isEmpty()) {
            UltraLucky.instance.getLogger().warning("无法从MythicMobs 5.x中找到 怪物: " + param[0]);
            return;
        }
        optionalMythicMob.get().spawn(location, Integer.parseInt(param[1]));
    }
}
