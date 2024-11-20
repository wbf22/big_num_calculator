package big.num.engine.domain;

import java.math.BigDecimal;

import big.num.engine.util.MathUtil;

public class Factorial extends Expression {

    private Expression innerExpression;

    public Factorial(String innerExpression) {
        super(null, null, null);
        this.innerExpression = Expression.parse(innerExpression);
    }


    @Override
    public BigDecimal evaluate() {
        BigDecimal innerRes = innerExpression.evaluate();

        if (!MathUtil.isLongValue(innerRes)) {
            throw new IllegalStateException("Error ! on dec num");
        }
        long cnt = innerRes.longValue() - 1;
        for (long i = cnt; i > 0; i--) {
            innerRes = innerRes.multiply(BigDecimal.valueOf(i));
        }

        return innerRes;
    }

    @Override
    public String toString() {
        return innerExpression + "!";
    }
    
}
