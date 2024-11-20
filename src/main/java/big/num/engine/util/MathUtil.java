package big.num.engine.util;

import java.math.BigDecimal;

public class MathUtil {
    
    /**
     * Returns true if char is a digit or a '.'
     * @param input
     * @return
     */
    public static boolean isNumeric(Character input) {
        if (input == '.') return true;

        try {
            Integer intg = Integer.parseInt(input.toString());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public static boolean isIntegerValue(BigDecimal bd) {
        return bd.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0 
            && bd.compareTo( BigDecimal.valueOf(Integer.MAX_VALUE) ) < 0;
    }

    public static boolean isLongValue(BigDecimal bd) {
        return bd.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0 
            && bd.compareTo( BigDecimal.valueOf(Long.MAX_VALUE) ) < 0;
    }


}
