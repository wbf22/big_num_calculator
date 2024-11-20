package big.num.engine.domain.operators;

import java.math.BigDecimal;
import java.math.RoundingMode;

import big.num.engine.domain.Expression;

public class Mod implements Operator {
    @Override
    public BigDecimal apply(Expression leftOperand, Expression rightOperand) {
        BigDecimal left = leftOperand.evaluate();
        BigDecimal div = left.divide(rightOperand.evaluate(), RoundingMode.FLOOR);
        return left.subtract(div);
    }

    @Override
    public int order() {
        return 1;
    }


    @Override
    public String toString() {
        return "%";
    }
}
