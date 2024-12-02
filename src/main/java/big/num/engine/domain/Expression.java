package big.num.engine.domain;

import java.math.BigDecimal;
import java.util.Stack;

import big.num.engine.Constants;
import big.num.engine.domain.operators.Operator;
import big.num.engine.util.MathUtil;

public class Expression {


    private Expression leftOperand;
    private Operator operator;
    private Expression rightOperand;


    public Expression(Expression leftOperand, Operator operator, Expression rightOperand) {
        this.leftOperand = leftOperand;
        this.operator = operator;
        this.rightOperand = rightOperand;
    }


    public static Expression parse( String input) {
        // 4 + 3 * (9 + 2 )
        // -4 * (7 * 8 )
        // (9 + 1) % 76
        // 5 * 3 + 1
        // (9 + 5) + ( 4 * (7 / 8) )
        // (9 + 5)( 8 )(1 + 7)


        // resolve functions
        if (containsFunctions(input)) {
            return parseFunctions(input);
        }


        // resolve parenthesis
        if (input.contains("(")) {
            return parseParenToken(input);
        }

        // find lowest precedence operator
        Operator lowestPrecedenc = null;
        int opIndex = 0;
        for (int i = 0; i < input.length(); i++) {
            Character charBefore = (i > 0)? input.charAt(i - 1) : null;
            Operator op = Operator.parseIfOperator(charBefore, input.charAt(i));

            if (
                op != null &&
                ( lowestPrecedenc == null || lowestPrecedenc.order() >= op.order() )
            ) {
                    lowestPrecedenc = op;
                    opIndex = i;
            }
        }


        if (lowestPrecedenc == null) {
            // parse as single number
            return new Number(
                new BigDecimal( input )
            );
        }
        else {
            if (opIndex == 0) {
                throw new IllegalStateException("Leading operator");
            } else if (opIndex == input.length() - 1) {
                throw new IllegalStateException("Trailing operator");
            }
            else {
                return new Expression(
                    parse( input.substring(0, opIndex) ),
                    lowestPrecedenc,
                    parse( input.substring(opIndex + 1))
                );
            }
        }




    }

    private static boolean containsFunctions(String input) {
        return hasLog(input) ||
        input.contains("!") ||
        input.contains("sin") ||
        input.contains("cos") ||
        input.contains("tan") ||
        input.contains("sinh") ||
        input.contains("cosh") ||
        input.contains("tanh") ||
        input.contains("sin⁻¹") ||
        input.contains("cos⁻¹") ||
        input.contains("tan⁻¹") ||
        input.contains("sinh⁻¹") ||
        input.contains("cosh⁻¹") ||
        input.contains("tanh⁻¹");
    }

    public static boolean hasLog(String input) {
        return input.contains("ln") ||
        input.contains("log₁₀") ||
        input.contains("log");
    }

    private static Expression parseFunctions(String input) {
        Expression function;

        int start = 0;
        int innerEnd = input.length() - 1;
        if (hasLog(input)) {
            BigDecimal base = Constants.e;

            int index = input.indexOf("ln");
            if (index != -1) {
                start = index;
            }

            index = input.indexOf("log₁₀");
            if (index != -1) {
                base = BigDecimal.TEN;
                start = index;
            }

            index = input.indexOf("log");
            if (index != -1) {
                int parenIndex = index;
                while(input.charAt(parenIndex) != '(') {
                    parenIndex++;
                }
                if (parenIndex == index + 3)
                    base = BigDecimal.TEN;
                else 
                    base = new BigDecimal(input.substring(index + 3, parenIndex));
                start = index;
            }

            int innerStart = start;
            while(input.charAt(innerStart) != '(') {
                innerStart++;
            }

            innerEnd = getOtherEndOfParenthesis(innerStart, input);

            function = new Log(base, input.substring(innerStart + 1, innerEnd));
            innerEnd++;
        }
        else if (input.contains("!")) {
            int index = input.indexOf("!");
            if (input.charAt(index-1) == ')') {
                start = getOtherEndOfParenthesis(index-1, input);
            }
            else {
                start = index - 1;
                while(start > 0 && MathUtil.isNumeric(input.charAt(start))) {
                    start--;
                }
            }
            
            function = new Factorial(input.substring(start, index));
            innerEnd = (index < input.length())? index + 1 : innerEnd;
        }
        else {
            return null;
        }


        return parse(input.substring(0, start) + function.evaluate() + input.substring(innerEnd));
    }

    private static Expression parseParenToken(String input) {
        Stack<Integer> innerParen = new Stack<>();
        int firstOccurance = input.indexOf('(');
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(')
                innerParen.push(i);

            if (input.charAt(i) == ')') {
                innerParen.pop();

                if (innerParen.isEmpty()) {
                    Expression innerExpression = Expression.parse(input.substring(firstOccurance + 1, i));

                    if (firstOccurance == 0) {
                        return parse(
                            innerExpression.evaluate() + input.substring(i + 1)
                        );
                    } else if (i == input.length() - 1) {
                        return parse(
                            input.substring(0, firstOccurance) + innerExpression.evaluate()
                        );
                    }
                    else {
                        return parse(
                            input.substring(0, firstOccurance) + innerExpression.evaluate() + input.substring(i+1)
                        );
                    }

                }
            }
        }

        throw new IllegalStateException("Missing parenthesis");
    }

    private static int getOtherEndOfParenthesis(int startIndex, String input) {
        int step = (input.charAt(startIndex) == '(')? 1 : -1;
        char startChar = input.charAt(startIndex);
        char endChar = (input.charAt(startIndex) == '(')? ')' : '(';

        Stack<Integer> parens = new Stack<>();
        parens.push(startIndex);
        int i = startIndex;
        while (!parens.empty()) { 
            i += step;
            if (input.charAt(i) == startChar) {
                parens.push(i);
            }
            if (input.charAt(i) == endChar) {
                parens.pop();
            }
        }

        return i;
    }

    public BigDecimal evaluate() {
        String valueRounded = operator.apply( leftOperand, rightOperand ).stripTrailingZeros().toPlainString();
        return new BigDecimal(valueRounded);
    }


    @Override
    public String toString() {
        return leftOperand.toString() + operator.toString() + rightOperand.toString();
    }
}
