//Groovy脚本示例
//请勿将条件版本与奖励版本混用
//仅需修改parse方法部分
package groovy

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

static boolean parse(ItemStack item, Player player, String param) {
    player.sendMessage("这是Groovy脚本条件 物品TYPE: ${item.getType().toString()} 参数: $param")
    return true
}

return parse(item, player, param)