package dev.rgbmc.ultralucky.utils;

import org.bukkit.entity.Player;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class DynamicStorage {
    private static final Map<String, Object> storage = new HashMap<>();

    public static void storage(Player player, String type, String key, Object data) {
        storage.put(Base64.getEncoder().encodeToString((player.getName() + type + key).getBytes(StandardCharsets.UTF_8)), data);
    }

    public static Object get(Player player, String type, String key) {
        return storage.get(Base64.getEncoder().encodeToString((player.getName() + type + key).getBytes(StandardCharsets.UTF_8)));
    }

    public static void delete(Player player, String type, String key) {
        storage.remove(Base64.getEncoder().encodeToString((player.getName() + type + key).getBytes(StandardCharsets.UTF_8)));
    }
}
