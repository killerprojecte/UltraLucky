package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.rewards.Reward;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.script.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JavascriptReward implements Reward {
    public ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

    @Override
    public void forward(Player player, String args) {
        String[] params = args.split("\\|\\|");
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("javascript");
        try {
            Bindings bindings = scriptEngine.createBindings();
            bindings.put("server", Bukkit.getServer());
            scriptEngine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
            Invocable invocable = (Invocable) scriptEngine.eval(
                    new BufferedReader(
                            new InputStreamReader(Files.newInputStream(
                                    Paths.get(
                                            UltraLucky.instance.getDataFolder()
                                                    + "/js/" + params[0] + ".js")
                            ), StandardCharsets.UTF_8)));
            invocable.invokeFunction("reward", player, params[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
