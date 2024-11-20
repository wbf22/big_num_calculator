package big.num.engine.domain.operators;

import java.math.BigDecimal;

import big.num.engine.domain.Expression;
import big.num.engine.util.MathUtil;

public class Power implements Operator {
    @Override
    public BigDecimal apply(Expression leftOperand, Expression rightOperand) {
        BigDecimal leftValue = leftOperand.evaluate();
        BigDecimal rightValue = rightOperand.evaluate();

        BigDecimal result;
        if (MathUtil.isIntegerValue(rightValue)) {
            result = leftOperand.evaluate().pow(rightOperand.evaluate().intValue());
        }
        else if (
            leftValue.compareTo(BigDecimal.valueOf(Double.MAX_VALUE)) < 0 &&
            rightValue.compareTo(BigDecimal.valueOf(Double.MAX_VALUE)) < 0
        ) {
            double res = Math.pow(leftValue.doubleValue(), rightValue.doubleValue());
            result = BigDecimal.valueOf(res);
        }
        else {
            throw new IllegalStateException("Value too large for power");
        }

        return result;
    }

    @Override
    public int order() {
        return 2;
    }

    @Override
    public String toString() {
        return "^";
    }

}
