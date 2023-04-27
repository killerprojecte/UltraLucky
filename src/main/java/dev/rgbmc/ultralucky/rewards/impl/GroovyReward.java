package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.rewards.Reward;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class GroovyReward implements Reward {
    @Override
    public void forward(Player player, String args) {
        System.out.println("running");
        String[] params = args.split("\\|\\|");
        GroovyShell gs = new GroovyShell();
        Script script;
        try {
            script = gs.parse(new File(UltraLucky.instance.getDataFolder(), "scripts/" + params[0] + ".groovy"));
        } catch (IOException e) {
            UltraLucky.instance.getLogger().severe("在解析Groovy脚本 " + params[0] + " 时遇到错误:");
            e.printStackTrace();
            return;
        }
        Binding binding = new Binding();
        binding.setVariable("player", player);
        binding.setVariable("param", params[1]);
        script.setBinding(binding);
        script.run();
    }
}
