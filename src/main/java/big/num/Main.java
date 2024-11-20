package big.num;

import java.math.BigDecimal;
import java.util.Scanner;

import big.num.engine.CalculatorEngine;
import big.num.engine.Constants;
import big.num.util.AnsiColor;

public class Main {
    public static void main(String[] args) {
        
        try (Scanner scanner = new Scanner(System.in)) {
            CalculatorEngine calculatorEngine = new CalculatorEngine();

            while (true) {

                if (scanner.hasNextLine()) {
                    try {
                        String line = scanner.nextLine();
                        if (line.equals("cl") || line.equals("clear")) {
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            continue;
                        }
                        else if (line.equals("constants")) {
                            Constants.print();
                            continue;
                        }

                        BigDecimal result = calculatorEngine.evaluate(line, false);
                        System.out.print(AnsiColor.GREEN);
                        System.out.println(beautify(result));
                    }
                    catch (Exception e) {
                        e.printStackTrace();

                        System.out.println();
                        System.out.print("To see available constants enter 'constants'");
                    }
                }
            }

        }

    }

    public static String beautify(BigDecimal result) {
        String valueString = result.toString();

        StringBuilder valueWithCommas = new StringBuilder();
        int dotIndex = valueString.indexOf(".");
        dotIndex = (dotIndex == -1)? valueString.length() : dotIndex;
        for (int i = 0; i < dotIndex; i++) {
            valueWithCommas.append(valueString.charAt(i));
            if ((dotIndex - i - 1) % 3 == 0 && i != dotIndex - 1) {
                valueWithCommas.append(",");
            }
        }

        for (int i = dotIndex; i < valueString.length(); i++) {
            valueWithCommas.append(valueString.charAt(i));
        }

        String resultString = valueWithCommas.toString();

        return getPadding(resultString) + resultString + AnsiColor.RESET;
    }


    public static String getPadding(String content) {
        int terminalWidth = getTerminalWidth();
        int paddingLength = terminalWidth - content.length();
        return " ".repeat(Math.max(0, paddingLength));
    }

    public static int getTerminalWidth() {
        int width = 80; // Default width
        try {
            Process process = new ProcessBuilder("sh", "-c", "tput cols 2> /dev/tty").start();
            process.waitFor();
            width = Integer.parseInt(new String(process.getInputStream().readAllBytes()).trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return width;
    }
}

