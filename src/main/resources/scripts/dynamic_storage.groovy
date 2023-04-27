//Groovy脚本示例
//请勿将条件版本与奖励版本混用
//仅需修改parse方法部分
package groovy

import dev.rgbmc.ultralucky.utils.Color
import dev.rgbmc.ultralucky.utils.DynamicStorage
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player

static reward(Player player, String param) {
    double damage = DynamicStorage.get(player, "damage", "attack_example") as double
    double final_damage = DynamicStorage.get(player, "final_damage", "attack_example") as double
    //println("伤害: " + damage)
    //println("最终伤害: " + final_damage)
    LivingEntity entity = DynamicStorage.get(player, "target", "attack_example") as LivingEntity
    double addition = final_damage + (damage * 0.75)
    if (param.equalsIgnoreCase("x2")) addition = addition * 2
    entity.damage(addition, player)
    player.sendMessage(Color.color("&c打出了 &e" + String.format("%.2f", addition) + " 点&c额外伤害"))
    return true
}

reward(player, param)