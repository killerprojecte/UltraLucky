package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.rewards.Reward;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.AbstractLocation;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class MythicMobs4Reward implements Reward {
    @Override
    public void forward(Player player, String args, RuntimeVariable variable) {
        String[] param = variable.evalVariables(PlaceholderAPI.setPlaceholders(player, args)).split(",");
        AbstractLocation location = BukkitAdapter.adapt(player.getLocation());
        MythicMob mob = MythicMobs.inst().getMobManager().getMythicMob(param[0]);
        if (mob == null) {
            UltraLucky.instance.getLogger().warning("无法从MythicMobs 4.x中找到 怪物: " + param[0]);
            return;
        }
        variable.put("mythicmob_name", mob.getInternalName());
        mob.spawn(location, Integer.parseInt(param[1]));
    }
}
