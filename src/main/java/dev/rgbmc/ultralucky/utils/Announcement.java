package dev.rgbmc.ultralucky.utils;

import dev.rgbmc.ultralucky.UltraLucky;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Announcement {
    public static void get() {
        Bukkit.getScheduler().runTaskAsynchronously(UltraLucky.instance, () -> {
            List<String> list = new ArrayList<>();
            try {
                URL url = new URL("https://ghproxy.com/https://raw.githubusercontent.com/killerprojecte/UltraLucky/master/announcement");
                URLConnection connection = url.openConnection();
                connection.connect();
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    list.add(line);
                }
                br.close();
                UltraLucky.instance.getLogger().info("[公告]");
                for (String l : list) {
                    UltraLucky.instance.getLogger().info("> " + l);
                }
            } catch (Exception e) {
                UltraLucky.instance.getLogger().warning("获取公告时遇到错误: " + e.getMessage());
            }
        });
    }
}
