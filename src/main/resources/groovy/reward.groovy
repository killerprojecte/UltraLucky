//Groovy脚本示例
//请勿将条件版本与奖励版本混用
//仅需修改parse方法部分
package groovy

import org.bukkit.entity.Player

static reward(Player player, String param) {
    player.sendMessage("这是Groovy脚本奖励 参数: $param")
    return true
}

reward(player, param)