package big.num.engine.domain.operators;

import java.math.BigDecimal;

import big.num.engine.domain.Expression;

public class Multiply implements Operator {
    @Override
    public BigDecimal apply(Expression leftOperand, Expression rightOperand) {
        BigDecimal result = leftOperand.evaluate().multiply(rightOperand.evaluate());
        return result;
    }

    @Override
    public int order() {
        return 1;
    }


    @Override
    public String toString() {
        return "x";
    }
}
