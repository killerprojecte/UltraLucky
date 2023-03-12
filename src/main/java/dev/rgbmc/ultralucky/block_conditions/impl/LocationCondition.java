package dev.rgbmc.ultralucky.block_conditions.impl;

import com.ezylang.evalex.EvaluationException;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.bigmath.functions.bigdecimalmath.BigDecimalMathFunctions;
import com.ezylang.evalex.bigmath.operators.bigdecimalmath.BigDecimalMathOperators;
import com.ezylang.evalex.config.ExpressionConfiguration;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.parser.ParseException;
import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.block_conditions.BlockCondition;
import dev.rgbmc.ultralucky.hook.PlaceholderAPIHook;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class LocationCondition implements BlockCondition {

    private static final ExpressionConfiguration evalExConfiguration =
            ExpressionConfiguration.defaultConfiguration()
                    .withAdditionalFunctions(BigDecimalMathFunctions.allFunctions())
                    .withAdditionalOperators(BigDecimalMathOperators.allOperators());

    @Override
    public boolean parse(Block block, String args, Player player) {
        String[] param = PlaceholderAPIHook.evalString(player, args).split(",");
        Expression expression;
        EvaluationValue result;
        int x;
        int y;
        int z;
        try {
            expression = new Expression(param[0], evalExConfiguration);
            result = expression.evaluate();
            x = result.getNumberValue().intValue();
            expression = new Expression(param[1], evalExConfiguration);
            result = expression.evaluate();
            y = result.getNumberValue().intValue();
            expression = new Expression(param[2], evalExConfiguration);
            result = expression.evaluate();
            z = result.getNumberValue().intValue();
        } catch (EvaluationException | ParseException e) {
            UltraLucky.instance.getLogger().severe("在计算位置数学公式时遇到错误 公式: " + args);
            e.printStackTrace();
            return false;
        }
        return block.getLocation().getBlockX() == x && block.getLocation().getBlockY() == y && block.getLocation().getBlockZ() == z;
    }
}
