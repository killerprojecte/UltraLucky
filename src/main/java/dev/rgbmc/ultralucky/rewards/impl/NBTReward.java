package dev.rgbmc.ultralucky.rewards.impl;

import de.tr7zw.nbtapi.NBTItem;
import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.hook.PlaceholderAPIHook;
import dev.rgbmc.ultralucky.rewards.Reward;
import dev.rgbmc.ultralucky.utils.ItemUtil;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NBTReward implements Reward {
    @Override
    public void forward(Player player, String args, RuntimeVariable variable) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (ItemUtil.isEmpty(item)) return;
        String[] param = variable.evalVariables(PlaceholderAPIHook.evalString(player, args)).split(",");
        //[nbt] <TAG>,<数据类型: int, double, float, long, string>,<数据>
        NBTItem nbtItem = new NBTItem(item);
        String tag = param[0];
        String data_type = param[1];
        if (data_type.equalsIgnoreCase("int")) {
            nbtItem.setInteger(tag, Integer.parseInt(param[2]));
        } else if (data_type.equalsIgnoreCase("double")) {
            nbtItem.setDouble(tag, Double.parseDouble(param[2]));
        } else if (data_type.equalsIgnoreCase("float")) {
            nbtItem.setFloat(tag, Float.parseFloat(param[2]));
        } else if (data_type.equalsIgnoreCase("long")) {
            nbtItem.setLong(tag, Long.parseLong(param[2]));
        } else if (data_type.equalsIgnoreCase("string")) {
            nbtItem.setString(tag, param[2]);
        } else {
            UltraLucky.instance.getLogger().warning("未知的NBT数据类型: " + data_type);
        }
    }
}
