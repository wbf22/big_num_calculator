package big.num.engine.domain.operators;

import java.math.BigDecimal;

import big.num.engine.domain.Expression;

public class Root implements Operator {
    @Override
    public BigDecimal apply(Expression leftOperand, Expression rightOperand) {
        throw new IllegalStateException("This shouldn't be called. Only to check if root operator is present");
    }

    @Override
    public int order() {
        return 2;
    }

    @Override
    public String toString() {
        return "√";
    }
}
