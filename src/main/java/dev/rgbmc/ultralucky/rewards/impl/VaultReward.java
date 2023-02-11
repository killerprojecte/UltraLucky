package dev.rgbmc.ultralucky.rewards.impl;

import com.ezylang.evalex.EvaluationException;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.bigmath.functions.bigdecimalmath.BigDecimalMathFunctions;
import com.ezylang.evalex.bigmath.operators.bigdecimalmath.BigDecimalMathOperators;
import com.ezylang.evalex.config.ExpressionConfiguration;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.parser.ParseException;
import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.hook.PlaceholderAPIHook;
import dev.rgbmc.ultralucky.rewards.Reward;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultReward implements Reward {
    private static final ExpressionConfiguration evalExConfiguration =
            ExpressionConfiguration.defaultConfiguration()
                    .withAdditionalFunctions(BigDecimalMathFunctions.allFunctions())
                    .withAdditionalOperators(BigDecimalMathOperators.allOperators());
    private static Economy econ = null;

    @Override
    public void forward(Player player, String args) {
        if (econ == null) {
            if (!setupEconomy()) {
                UltraLucky.instance.getLogger().severe("Vault 尚未配置经济实现 请安装基础插件(CMI/ESS/等)或独立的Economy经济");
                return;
            }
        }
        Expression expression = new Expression(PlaceholderAPIHook.evalString(player, args), evalExConfiguration);
        try {
            EvaluationValue result = expression.evaluate();
            econ.depositPlayer(player, result.getNumberValue().doubleValue());
        } catch (EvaluationException | ParseException e) {
            e.printStackTrace();
            UltraLucky.instance.getLogger().warning("在计算Vault奖励时遇到错误 公式: " + PlaceholderAPIHook.evalString(player, args));
        }
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
