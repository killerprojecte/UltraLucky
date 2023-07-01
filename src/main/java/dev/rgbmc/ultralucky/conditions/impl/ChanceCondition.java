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
import dev.rgbmc.ultralucky.variables.RuntimeVariable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ChanceCondition implements Condition {
    private static final ExpressionConfiguration evalExConfiguration =
            ExpressionConfiguration.defaultConfiguration()
                    .withAdditionalFunctions(BigDecimalMathFunctions.allFunctions())
                    .withAdditionalOperators(BigDecimalMathOperators.allOperators());

    @Override
    public boolean parse(ItemStack item, Player player, String args, RuntimeVariable variable) {
        String chance = variable.evalVariables(PlaceholderAPIHook.evalString(player, args));
        Expression expression = new Expression(chance, evalExConfiguration);
        try {
            EvaluationValue result = expression.evaluate();
            if (result.getNumberValue().doubleValue() > Math.random()) return true;
        } catch (EvaluationException | ParseException e) {
            UltraLucky.instance.getLogger().severe("在计算概率时遇到错误 设置的概率: " + chance);
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
