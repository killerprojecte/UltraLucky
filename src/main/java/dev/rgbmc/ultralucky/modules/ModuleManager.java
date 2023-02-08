package dev.rgbmc.ultralucky.modules;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.modules.mining.MiningModule;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.List;

public class ModuleManager {
    public void loadIncludeModules() {
        List<Module> include = Arrays.asList(
          new MiningModule()
        );
        for (Module module : include){
            registerModule(module);
        }
    }

    public void registerModule(Module module){
        UltraLucky.instance.getLogger().info("加载模块 名称: " + module.getName() + " 作者: " + module.getAuthor() + " 版本: " + module.getVersion());
        Bukkit.getPluginManager().registerEvents(module, UltraLucky.instance);
    }
}
