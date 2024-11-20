package big.num.engine.domain;

import java.math.BigDecimal;

import big.num.engine.domain.operators.Operator;

public class Log extends Expression {

    private BigDecimal base;
    private Expression innerExpression;

    public Log(BigDecimal base, String innerExpression) {
        super(null, null, null);
        this.base = base;
        this.innerExpression = Expression.parse(innerExpression);
    }


    @Override
    public BigDecimal evaluate() {
        BigDecimal inner = innerExpression.evaluate();

        if (
            inner.compareTo(BigDecimal.valueOf(Double.MAX_VALUE)) >= 0 &&
            base.compareTo(BigDecimal.valueOf(Double.MAX_VALUE)) >= 0
        ) {
            throw new IllegalStateException("Value too large for log");
        }

        double res = Math.log(inner.doubleValue()) / Math.log(base.doubleValue());
        return BigDecimal.valueOf(res);
    }

    @Override
    public String toString() {
        return "log" + base + "(" + innerExpression + ")";
    }
    
}
