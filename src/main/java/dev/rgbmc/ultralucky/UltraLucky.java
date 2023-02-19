package dev.rgbmc.ultralucky;

import dev.rgbmc.ultralucky.command.UltraLuckyCommand;
import dev.rgbmc.ultralucky.database.BlockStorage;
import dev.rgbmc.ultralucky.modules.ModuleManager;
import dev.rgbmc.ultralucky.utils.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public final class UltraLucky extends JavaPlugin {

    public static UltraLucky instance;
    public static BlockStorage blockStorage;
    private static ModuleManager moduleManager;

    public static ModuleManager getModuleManager() {
        return moduleManager;
    }

    private Metrics metrics;

    @Override
    public void onEnable() {
        instance = this;
        saveFile("config.yml");
        saveFile("Mining.yml");
        saveFile("Fishing.yml");
        saveFile("Enchant.yml");
        saveFile("Eat.yml");
        saveFile("Tame.yml");
        printLogo("                                    \n" +
                "────────────────────────────────────\n" +
                "                                    \n" +
                "╦ ╦┬ ┌┬┐┬─┐┌─┐  ╦  ┬ ┬┌─┐┬┌─┬ ┬     \n" +
                "║ ║│  │ ├┬┘├─┤  ║  │ ││  ├┴┐└┬┘     \n" +
                "╚═╝┴─┘┴ ┴└─┴ ┴  ╩═╝└─┘└─┘┴ ┴ ┴      \n" +
                "                                    \n" +
                "────────────────────────────────────\n" +
                "UltraLucky 由 FlyProject 开发\n" +
                "版本: " + getDescription().getVersion() + "\n" +
                "Github: https://github.com/killerprojecte/UltraLucky\n" +
                "!!本插件使用GPLv3协议开源 并带有附加条款 请仔细阅读!!\n");
        if (Double.parseDouble(System.getProperty("java.specification.version")) < 11) {
            getLogger().severe("[!] 当前服务端使用的JDK版本过低 请使用Java11及更新版本");
            setEnabled(false);
            return;
        }
        metrics = new Metrics(this, 17766);
        ;
        moduleManager = new ModuleManager();
        blockStorage = new BlockStorage();
        Bukkit.getPluginManager().registerEvents(blockStorage, this);
        getLogger().info("[!] BlockStorage 方块数据库初始化完成");
        moduleManager.loadIncludeModules();
        getLogger().info("[!] 所有内置组件已加载完成");
        getCommand("ultralucky").setExecutor(new UltraLuckyCommand());
    }

    @Override
    public void onDisable() {
        getLogger().info("[!] 正在断开与 BlockStorage 方块数据库 的连接");
        blockStorage.close();
        metrics.shutdown();
    }

    private void printLogo(String str) {
        String[] lines = str.split("\n");
        for (String l : lines) {
            getLogger().info(l);
        }
    }

    private void saveFile(String name) {
        saveFile(name, false, name);
    }

    private void saveFile(String name, boolean replace, String saveName) {
        URL url = getClass().getClassLoader().getResource(name);
        if (url == null) {
            getLogger().severe(name + " Not Found in JarFile");
            return;
        }
        File file = new File(getDataFolder() + "/" + saveName);
        if (!replace) {
            if (file.exists()) return;
        }
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        URLConnection connection = null;
        try {
            connection = url.openConnection();
        } catch (IOException e) {
            getLogger().severe("Failed unpack file " + name + ":" + e.getMessage());
        }
        connection.setUseCaches(false);
        try {
            saveFile(connection.getInputStream(), file);
        } catch (IOException e) {
            getLogger().severe("Failed unpack file " + name + ":" + e.getMessage());
        }
    }

    private void saveFile(InputStream inputStream, File file) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }
    }
}
