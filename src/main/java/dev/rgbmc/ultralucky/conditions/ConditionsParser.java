package dev.rgbmc.ultralucky.conditions;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.conditions.impl.*;
import dev.rgbmc.ultralucky.utils.AsyncFuture;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ConditionsParser {
    private static final Map<String, Condition> conditionMap = new HashMap<String, Condition>() {
        {
            put("name", new NameCondition());
            put("none", new NoneCondition());
            put("lore", new LoreCondition());
            put("start-with-lore", new StartWithLoreCondition());
            put("permission", new PermissionCondition());
            put("chance", new ChanceCondition());
            put("math", new MathCondition());
            put("time", new TimeCondition());
            put("material", new MaterialCondition());
            put("group", new ConditionsGroupCondition());
            put("world", new WorldCondition());
            put("world_regex", new WorldRegexCondition());
            put("js", new JavascriptCondition());
            put("invslot", new InventorySlotCondition());
            put("groovy", new GroovyCondition());
            put("cooldown", new CooldownCondition());
            put("enchant", new EnchantCondition());
            if (Bukkit.getPluginManager().getPlugin("FlyBuff") != null &&
                    Bukkit.getPluginManager().getPlugin("FlyBuff").getDescription().getVersion().startsWith("2.")) {
                UltraLucky.instance.getLogger().info("已检测到 FlyBuff-Next, 已添加 FlyBuff宝石 条件检测");
                put("flybuff", new FlyBuffGemCondition());
            }
            if (Bukkit.getPluginManager().getPlugin("NBTAPI") != null) {
                UltraLucky.instance.getLogger().info("已检测到 NBTAPI, 已添加 NBT 条件检测");
                put("nbt", new NBTCondition());
            }
        }
    };

    public static CompletableFuture<Boolean> checkConditions(List<String> conditions, ItemStack item, Player player) {
        AsyncFuture<Boolean> task = new AsyncFuture<>(() -> {
            CompletableFuture<Boolean> future = new CompletableFuture<>();
            for (String line : conditions) {
                for (Map.Entry<String, Condition> entry : conditionMap.entrySet()) {
                    String prefix = "[" + entry.getKey() + "] ";
                    if (line.startsWith(prefix)) {
                        String param = line.substring(prefix.length());
                        if (entry.getKey().equals("group")) {
                            AsyncFuture<Boolean> conditionsGroup = new AsyncFuture<>(() -> entry.getValue().parse(item, player, param));
                            conditionsGroup.execute().thenAcceptAsync(status -> {
                                if (!status) future.complete(false);
                            });
                        } else {
                            Bukkit.getScheduler().runTask(UltraLucky.instance, () -> {
                                if (!entry.getValue().parse(item, player, param)) future.complete(false);
                            });
                        }
                    }
                }
            }
            try {
                return future.get(500L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                return true;
            }
        });
        return task.execute();
    }

    public static void registerCondition(String tag, Condition condition) {
        UltraLucky.instance.getLogger().info("[!] 注册外部条件 TAG: " + tag + " 路径: " + condition.getClass().getName());
        conditionMap.put(tag, condition);
    }

    public static Condition getCondition(String tag) {
        return conditionMap.get(tag);
    }
}
