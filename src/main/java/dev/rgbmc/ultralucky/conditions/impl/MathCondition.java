package dev.rgbmc.ultralucky.conditions.impl;

import com.ezylang.evalex.EvaluationException;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.bigmath.functions.bigdecimalmath.BigDecimalMathFunctions;
import com.ezylang.evalex.bigmath.operators.bigdecimalmath.BigDecimalMathOperators;
import com.ezylang.evalex.config.ExpressionConfiguration;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.parser.ParseException;
import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.conditions.Condition;
import dev.rgbmc.ultralucky.hook.PlaceholderAPIHook;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MathCondition implements Condition {
    private static final ExpressionConfiguration evalExConfiguration =
            ExpressionConfiguration.defaultConfiguration()
                    .withAdditionalFunctions(BigDecimalMathFunctions.allFunctions())
                    .withAdditionalOperators(BigDecimalMathOperators.allOperators());

    @Override
    public boolean parse(ItemStack item, Player player, String args) {
        Expression expression = new Expression(PlaceholderAPIHook.evalString(player, args), evalExConfiguration);
        try {
            EvaluationValue result = expression.evaluate();
            return result.getBooleanValue();
        } catch (EvaluationException | ParseException e) {
            UltraLucky.instance.getLogger().severe("在比较数学公式时遇到错误 公式: " + args);
            e.printStackTrace();
            return false;
        }
    }
}
