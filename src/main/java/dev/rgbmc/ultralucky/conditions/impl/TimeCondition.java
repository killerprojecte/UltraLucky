package dev.rgbmc.ultralucky.conditions.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.conditions.Condition;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeCondition implements Condition {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public boolean parse(ItemStack item, Player player, String args, RuntimeVariable variable) {
        args = variable.evalVariables(args);
        String[] param = args.split(",");
        try {
            Date start = sdf.parse(param[0]);
            Date end = sdf.parse(param[1]);
            Date now = new Date();
            if (now.after(start) && now.before(end)) return true;
        } catch (ParseException e) {
            UltraLucky.instance.getLogger().severe("在解析时间区间时遇到错误 参数: " + args);
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
