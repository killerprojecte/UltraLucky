package dev.rgbmc.ultralucky.utils;

import dev.rgbmc.ultralucky.UltraLucky;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Announcement {
    public static void get() {
        AsyncFuture<Void> asyncFuture = new AsyncFuture<>(() -> {
            try {
                request("https://ghproxy.com/https://raw.githubusercontent.com/killerprojecte/UltraLucky/master/announcement");
            } catch (Exception e) {
                UltraLucky.instance.getLogger().info("获取公告时遇到错误: " + e.getMessage() + " 尝试重新获取");
                try {
                    request("https://raw.fgit.ml/killerprojecte/UltraLucky/master/announcement");
                } catch (Exception ex) {
                    UltraLucky.instance.getLogger().warning("获取公告时遇到错误: " + ex.getMessage());
                }
            }
            return null;
        });
        asyncFuture.execute();
    }

    private static void request(String link) throws Exception {
        List<String> list = new ArrayList<>();
        URL url = new URL(link);
        URLConnection connection = url.openConnection();
        connection.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        String line;
        while ((line = br.readLine()) != null) {
            list.add(line);
        }
        br.close();
        UltraLucky.instance.getLogger().info("[公告]");
        for (String l : list) {
            UltraLucky.instance.getLogger().info("> " + l);
        }
    }
}
