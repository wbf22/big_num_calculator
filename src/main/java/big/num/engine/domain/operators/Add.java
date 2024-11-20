package big.num.engine.domain.operators;

import java.math.BigDecimal;

import big.num.engine.domain.Expression;

public class Add implements Operator {
    @Override
    public BigDecimal apply(Expression leftOperand, Expression rightOperand) {
        BigDecimal result = leftOperand.evaluate().add(rightOperand.evaluate());
        return result;
    }

    @Override
    public int order() {
        return 0;
    }

    @Override
    public String toString() {
        return "+";
    }
}
