package big.num.engine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static BigDecimal e = new BigDecimal(Math.E);
    public static BigDecimal pi = new BigDecimal(Math.PI);
    public static BigDecimal G = new BigDecimal(6.67430E-11);
    public static BigDecimal c = new BigDecimal(299792458);
    public static BigDecimal h = new BigDecimal(6.62607015E-34);
    public static BigDecimal hbar = new BigDecimal(1.054571817E-34);
    public static BigDecimal q = new BigDecimal(1.602176634E-19);
    public static BigDecimal k = new BigDecimal(1.380649E-23);
    public static BigDecimal Na = new BigDecimal(6.02214076E23);
    public static BigDecimal R = new BigDecimal(8.314462618);
    public static BigDecimal sigma = new BigDecimal(5.670374419E-8);
    public static BigDecimal mu0 = new BigDecimal(4 * Math.PI * 1E-7);
    public static BigDecimal epsilon0 = new BigDecimal(8.8541878128E-12);


    public static Map<String, BigDecimal> constants = Map.ofEntries(
        Map.entry("e", e),
        Map.entry("pi", pi),
        Map.entry("G", G),
        Map.entry("c", c),
        Map.entry("h", h),
        Map.entry("hbar", hbar),
        Map.entry("q", q),
        Map.entry("k", k),
        Map.entry("Na", Na),
        Map.entry("R", R),
        Map.entry("sigma", sigma),
        Map.entry("mu0", mu0),
        Map.entry("epsilon0", epsilon0)
    );
    

    public static String replaceConstants(String expression) {
        for (Map.Entry<String, BigDecimal> entry : constants.entrySet()) {
            expression = expression.replace(entry.getKey(), entry.getValue().toString());
        }
        return expression;
    }

    public static void print() {
        System.out.println("Available constants:");
        for (Map.Entry<String, BigDecimal> entry : constants.entrySet()) {
            System.out.println(entry.getKey() + "  " + entry.getValue());
        }
    }

    
}
