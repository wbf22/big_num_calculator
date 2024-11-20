package big.num.engine;

import big.num.engine.domain.Expression;
import big.num.engine.domain.operators.Operator;
import big.num.engine.domain.operators.Root;
import big.num.engine.domain.operators.Subtract;
import big.num.engine.util.MathUtil;

import java.math.BigDecimal;
import java.util.Stack;

public class CalculatorEngine {

    private Stack<BigDecimal> pastResults = new Stack<>();


    public BigDecimal evaluate(String expression, boolean onNegative) {

        // replace constants
        expression = replaceConstants(expression);

        // remove whitespace, add multiply to parenthesis, convert roots to exponents 
        expression = clean(expression);

        // check if referencing previous value
        Operator op = Operator.parse(expression.charAt(0));
        boolean justANegativeSign = op instanceof Subtract && onNegative;
        if (op != null && !justANegativeSign) {
            expression = pastResults.peek().toString() + expression; 
        }

        // parse and evaluate expression
        Expression parsed = Expression.parse(expression);

        BigDecimal result = parsed.evaluate();

        // save result
        while (pastResults.size() > 100) {
            pastResults.pop();
        }
        pastResults.push(result);

        return result;

    }

    private String replaceConstants(String expression) {
        return Constants.replaceConstants(expression);
    }

    public static String clean(String input) {
        input = input.replace(" ", "");
        input = input.replace(",", "");

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {

            builder.append(input.charAt(i));

            // Checking if we need to add a multiply by parenthesis
            Character charBefore = (i > 0)? input.charAt(i - 1) : null;
            Character charAfter = (i >= input.length() - 1)? null : input.charAt(i + 1);
            boolean numeric = MathUtil.isNumeric(input.charAt(i));
            boolean log = isALog(i, input);

            if (numeric && charBefore != null && charBefore == ')') {
                builder.insert(builder.length() - 1, "x");
            }
            boolean triggerChar = charAfter != null && (charAfter == '(' || charAfter == 'l');
            if (!log && numeric && triggerChar) {
                builder.append("x");
            }


            // Checking if we need to convert a root to an exponent
            Operator op = Operator.parseIfOperator(charBefore, input.charAt(i));
            if (op instanceof Root) {
                String removedThis = builder.substring(0, builder.length() - 1);
                builder = new StringBuilder(removedThis);
                // insert exponent into input
                Stack<Character> stack = new Stack<>();
                stack.push('(');
                int j = i + 1;
                while (stack.size() > 0) {
                    j++;
                    if (input.charAt(j) == '(') {
                        stack.push('(');
                    } else if (input.charAt(j) == ')') {
                        stack.pop();
                    }
                }
                input = input.substring(0, i) + input.substring(i+1, j+1) + "^(0.5)" + input.substring(j+1);
                i = -1; // reset at beginning
                builder = new StringBuilder();
            }

        }

        return builder.toString();
    }


    private static boolean isALog(int i, String input) {
        char current = input.charAt(i);
        int j = i;
        while ( j > 0 && MathUtil.isNumeric(current) ) {
            current = input.charAt(j);
            j--;
        }
        j = (j > 0)? j - 1 : j;
        return Expression.hasLog(input.substring(j, i));
    }

}
