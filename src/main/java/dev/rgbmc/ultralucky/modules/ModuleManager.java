package dev.rgbmc.ultralucky.modules;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.modules.impl.*;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModuleManager {
    private static final List<Module> loadedModules = new ArrayList<>();

    public void loadIncludeModules() {
        List<Module> include = Arrays.asList(
                new MiningModule(),
                new FishingModule(),
                new EnchantModule(),
                new EatModule(),
                new TameModule(),
                new KillModule(),
                new ThrowEggModule(),
                new InteractBlockModule(),
                new AttackModule(),
                new TimerModule(),
                new RespawnModule(),
                new ChatModule()
        );
        for (Module module : include) {
            if (UltraLucky.instance.getConfig().getStringList("disable-modules").contains(module.getName())) {
                UltraLucky.instance.getLogger().info("[X] 模块 " + module.getName() + " 已被禁用");
                continue;
            }
            module.register();
            module.getConfigManager();
        }
    }

    public void registerModule(Module module) {
        UltraLucky.instance.getLogger().info("[!] 加载模块 名称: " + module.getName() + " 作者: " + module.getAuthor() + " 版本: " + module.getVersion());
        Bukkit.getPluginManager().registerEvents(module, UltraLucky.instance);
        loadedModules.add(module);
    }

    public void unregisterModule(Module module) {
        UltraLucky.instance.getLogger().info("[!] 卸载模块 名称: " + module.getName() + " 作者: " + module.getAuthor() + " 版本: " + module.getVersion());
        HandlerList.unregisterAll(module);
        loadedModules.remove(module);
    }

    public void reloadAllModule() {
        for (Module module : List.copyOf(loadedModules)) {
            module.getConfigManager().reloadConfig();
            module.unregister();
        }

        loadedModules.clear();
        loadIncludeModules();
    }
}
