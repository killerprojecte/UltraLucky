package dev.rgbmc.ultralucky.rewards;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.rewards.impl.*;
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardsManager {
    private static final Map<String, Reward> rewardMap = new HashMap<String, Reward>() {
        {
            put("console", new ConsoleCommandReward());
            put("player", new PlayerCommandReward());
            put("message", new MessageReward());
            put("chat", new ChatReward());
            put("title", new TitleReward());
            put("hover", new HoverMessageReward());
            put("actionbar", new ActionbarReward());
            put("give", new GiveReward());
            put("drop", new DropReward());
            put("broadcast", new BroadcastReward());
            put("group", new RewardsGroupReward());
            put("js", new JavascriptReward());
            put("groovy", new GroovyReward());
            put("cooldown", new CooldownReward());
            put("enchant", new EnchantReward());
            put("effect", new EffectReward());
            if (Bukkit.getPluginManager().getPlugin("FlyBuff") != null &&
                    Bukkit.getPluginManager().getPlugin("FlyBuff").getDescription().getVersion().startsWith("2.")) {
                UltraLucky.instance.getLogger().info("已检测到 FlyBuff-Next, 已添加 FlyBuff宝石 奖励");
                put("flybuff", new FlyBuffGemReward());
            }
            if (Bukkit.getPluginManager().getPlugin("MagicGem") != null &&
                    Bukkit.getPluginManager().getPlugin("MagicGem").getDescription().getVersion().startsWith("1.05")) {
                UltraLucky.instance.getLogger().info("已检测到 MagicGem 1.05, 已添加 魔术宝石 奖励");
                put("magicgem", new MagicGemReward());
            }
            if (Bukkit.getPluginManager().getPlugin("Zaphkiel") != null) {
                UltraLucky.instance.getLogger().info("已检测到 Zaphkiel, 已添加 Zaphkiel物品 奖励");
                put("zaphkiel", new ZaphkielReward());
            }
            if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
                UltraLucky.instance.getLogger().info("已检测到 Vault, 已添加 Vault货币 奖励");
                put("vault", new VaultReward());
            }
            if (Bukkit.getPluginManager().getPlugin("PlayerPoints") != null) {
                UltraLucky.instance.getLogger().info("已检测到 PlayerPoints, 已添加 PlayerPoints货币 奖励");
                put("points", new PlayerPointsReward());
            }
            if (Bukkit.getPluginManager().getPlugin("MythicMobs") != null) {
                Plugin mythicMobs = Bukkit.getPluginManager().getPlugin("MythicMobs");
                if (mythicMobs.getDescription().getVersion().startsWith("4.") || mythicMobs.getDescription().getVersion().startsWith("5.0.0")) {
                    UltraLucky.instance.getLogger().info("已检测到 MythicMobs 4.x (包括5.0.0), 已添加 MythicMobs附属 奖励");
                    put("mythicmobs", new MythicMobs4Reward());
                    put("mythicitem", new MythicItem4Reward());
                } else if (mythicMobs.getDescription().getVersion().startsWith("5.")) {
                    UltraLucky.instance.getLogger().info("已检测到 MythicMobs 5.x, 已添加 MythicMobs附属 奖励");
                    put("mythicmobs", new MythicMobs5Reward());
                    put("mythicitem", new MythicItem5Reward());
                } else {
                    UltraLucky.instance.getLogger().warning("已检测到 MythicMobs 但已安装版本: " + mythicMobs.getDescription().getVersion() + " 为找到对应兼容模块");
                }
            }
            if (Bukkit.getPluginManager().getPlugin("NBTAPI") != null) {
                UltraLucky.instance.getLogger().info("已检测到 NBTAPI, 已添加 NBT 奖励");
                put("nbt", new NBTReward());
            }
            if (Bukkit.getPluginManager().getPlugin("MMOItems") != null) {
                UltraLucky.instance.getLogger().info("已检测到 MMOItems, 已添加 MMOItems 物品奖励");
                put("mmoitems", new MMOItemsReward());
            }
        }
    };

    public static void forwardRewards(List<String> rewards, Player player, RuntimeVariable variable) {
        root:
        for (String line : rewards) {
            for (Map.Entry<String, Reward> entry : rewardMap.entrySet()) {
                String prefix = "[" + entry.getKey() + "] ";
                if (line.startsWith(prefix)) {
                    line = line.substring(prefix.length());
                    entry.getValue().forward(player, line, variable);
                    continue root;
                }
            }
        }
    }

    public static Map<String, Reward> getRewards() {
        return rewardMap;
    }

    @Deprecated
    public static void forwardRewards(List<String> rewards, Player player) {
        forwardRewards(rewards, player, new RuntimeVariable());
    }

    public static void registerReward(String tag, Reward reward) {
        UltraLucky.instance.getLogger().info("[!] 注册外部奖励 TAG: " + tag + " 路径: " + reward.getClass().getName());
        rewardMap.put(tag, reward);
    }

    public static Reward getReward(String tag) {
        return rewardMap.get(tag);
    }
}
