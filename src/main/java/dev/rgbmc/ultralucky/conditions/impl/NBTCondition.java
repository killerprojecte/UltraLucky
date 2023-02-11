package dev.rgbmc.ultralucky.conditions.impl;

import de.tr7zw.nbtapi.NBTItem;
import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.conditions.Condition;
import dev.rgbmc.ultralucky.hook.PlaceholderAPIHook;
import dev.rgbmc.ultralucky.utils.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NBTCondition implements Condition {
    @Override
    public boolean parse(ItemStack item, Player player, String args) {
        if (ItemUtil.isEmpty(item)) return false;
        String[] param = PlaceholderAPIHook.evalString(player, args).split(",");
        //[nbt] <TAG>,<操作: has, contains, equals>,<数据类型: int, double, float, long, string>,<数据>
        NBTItem nbtItem = new NBTItem(item);
        String tag = param[0];
        if (param[1].equalsIgnoreCase("has")) {
            return nbtItem.hasTag(tag);
        } else {
            String data_type = param[2];
            if (data_type.equalsIgnoreCase("int")) {
                if (param[1].equalsIgnoreCase("contains")) {
                    return nbtItem.getInteger(tag).toString().contains(param[3]);
                } else if (param[1].equalsIgnoreCase("equals")) {
                    return nbtItem.getInteger(tag) == Integer.parseInt(param[3]);
                } else {
                    UltraLucky.instance.getLogger().warning("不支持的NBT匹配方式: " + param[1]);
                }
            } else if (data_type.equalsIgnoreCase("double")) {
                if (param[1].equalsIgnoreCase("contains")) {
                    return nbtItem.getDouble(tag).toString().contains(param[3]);
                } else if (param[1].equalsIgnoreCase("equals")) {
                    return nbtItem.getDouble(tag) == Double.parseDouble(param[3]);
                } else {
                    UltraLucky.instance.getLogger().warning("不支持的NBT匹配方式: " + param[1]);
                }
            } else if (data_type.equalsIgnoreCase("float")) {
                if (param[1].equalsIgnoreCase("contains")) {
                    return nbtItem.getFloat(tag).toString().contains(param[3]);
                } else if (param[1].equalsIgnoreCase("equals")) {
                    return nbtItem.getFloat(tag) == Float.parseFloat(param[3]);
                } else {
                    UltraLucky.instance.getLogger().warning("不支持的NBT匹配方式: " + param[1]);
                }
            } else if (data_type.equalsIgnoreCase("long")) {
                if (param[1].equalsIgnoreCase("contains")) {
                    return nbtItem.getLong(tag).toString().contains(param[3]);
                } else if (param[1].equalsIgnoreCase("equals")) {
                    return nbtItem.getLong(tag) == Long.parseLong(param[3]);
                } else {
                    UltraLucky.instance.getLogger().warning("不支持的NBT匹配方式: " + param[1]);
                }
            } else if (data_type.equalsIgnoreCase("string")) {
                if (param[1].equalsIgnoreCase("contains")) {
                    return nbtItem.getString(tag).contains(param[3]);
                } else if (param[1].equalsIgnoreCase("equals")) {
                    return nbtItem.getString(tag).equals(param[3]);
                } else {
                    UltraLucky.instance.getLogger().warning("不支持的NBT匹配方式: " + param[1]);
                }
            } else {
                UltraLucky.instance.getLogger().warning("不支持的NBT数据类型: " + data_type);
            }
        }
        return false;
    }
}
