package dev.rgbmc.ultralucky.conditions.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.conditions.Condition;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class GroovyCondition implements Condition {
    @Override
    public boolean parse(ItemStack item, Player player, String args) {
        String[] params = args.split("\\|\\|");
        GroovyShell gs = new GroovyShell();
        Script script;
        try {
            script = gs.parse(new File(UltraLucky.instance.getDataFolder(), "scripts/" + params[0] + ".groovy"));
        } catch (IOException e) {
            UltraLucky.instance.getLogger().severe("在解析Groovy脚本 " + params[0] + " 时遇到错误:");
            e.printStackTrace();
            return false;
        }
        Binding binding = new Binding();
        binding.setVariable("item", item);
        binding.setVariable("player", player);
        binding.setVariable("param", params[1]);
        script.setBinding(binding);
        Object value = script.run();
        if (!(value instanceof Boolean)) {
            UltraLucky.instance.getLogger().severe("执行的Groovy脚本返回的并不是Boolean值");
            return false;
        }
        return (boolean) value;
    }
}
