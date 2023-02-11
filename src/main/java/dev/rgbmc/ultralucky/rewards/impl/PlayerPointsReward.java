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
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerPointsReward implements Reward {
    private static final ExpressionConfiguration evalExConfiguration =
            ExpressionConfiguration.defaultConfiguration()
                    .withAdditionalFunctions(BigDecimalMathFunctions.allFunctions())
                    .withAdditionalOperators(BigDecimalMathOperators.allOperators());
    private static PlayerPointsAPI playerPointsAPI = null;

    @Override
    public void forward(Player player, String args) {
        if (playerPointsAPI == null) {
            PlayerPoints plugin = (PlayerPoints) Bukkit.getPluginManager().getPlugin("PlayerPoints");
            playerPointsAPI = plugin.getAPI();
        }
        Expression expression = new Expression(PlaceholderAPIHook.evalString(player, args), evalExConfiguration);
        try {
            EvaluationValue result = expression.evaluate();
            playerPointsAPI.give(player.getUniqueId(), result.getNumberValue().intValue());
        } catch (EvaluationException | ParseException e) {
            e.printStackTrace();
            UltraLucky.instance.getLogger().warning("在计算PlayerPoints奖励时遇到错误 公式: " + PlaceholderAPIHook.evalString(player, args));
        }
    }
}
