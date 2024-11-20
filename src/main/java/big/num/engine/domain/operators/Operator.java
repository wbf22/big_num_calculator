package big.num.engine.domain.operators;

import java.math.BigDecimal;
import java.util.List;

import big.num.engine.domain.Expression;

public interface Operator {

    BigDecimal apply(Expression leftOperand, Expression rightOperand);

    int order();

    List<Character> operators = List.of('-', '+', '/', 'x', '*', '%', '^', '√');
    static Operator parseIfOperator(Character charBefore, Character charOfInterest) {
        if ( !operators.contains(charOfInterest) ) {
            return null;
        }

        boolean isMinus = charOfInterest == '-';
        boolean isNegativeSign = isMinus && 
        ( 
            charBefore == null || 
            operators.contains(charBefore) || 
            charBefore == '(' ||
            charBefore == 'E' ||
            charBefore == 'e'
        );
        
        if ( isNegativeSign ) {
                return null;
        }

        return parse(charOfInterest);
    }

    static Operator parse(Character character) {
        return switch (character) {
            case '-' -> new Subtract();
            case '+' -> new Add();
            case '/' -> new Divide();
            case 'x' -> new Multiply();
            case '*' -> new Multiply();
            case '%' -> new Mod();
            case '^' -> new Power();
            case '√' -> new Root();
            default -> null;
        };
    }

}
