//环境变量server -> Bukkit.getServer()

//
// 条件参数
// item -> ItemStack
// player -> Player
// param -> 传入参数
function parse(item, player, param) {
    return true;
}

//
// 奖励参数
// player -> Player
// param -> 传入参数
function reward(player, param) {
    //自定义代码
    player.sendMessage("这是JavaScript奖励");
}