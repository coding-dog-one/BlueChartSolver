package BlueChartSolver.app.Parser;

import BlueChartSolver.app.PolynomialFunction;
import BlueChartSolver.app.Variable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TermParser {
    private static final String allowedCharacters = "a-zA-Z0-9^";

    /**
     *  The 1st character must be a "-".
     *  The 2nd character must be alphabetic or numeric (greater than 0).
     *  If the 3rd and succeeding characters exist, they must not contain any invalid characters.
     *  Group 1 is the minus sign. Group 2 is the rest.
     */
    private static final Pattern negativePattern = Pattern.compile(
            String.format("^(-)(([a-zA-Z]|[1-9])[%s]*)", allowedCharacters)
    );

    /**
     * The 1st character must be numeric (greater than 0).
     * After 2nd character, sometimes there is a sequence of numeric (0 or higher).
     * The numeric sequence is followed by nothing or a string containing no invalid characters.
     * Group 1 is the coefficient. Groups 2 is the rest.
     */
    private static final Pattern coefficientPattern = Pattern.compile(
            String.format("^([1-9][0-9]*)([%s]*)", allowedCharacters)
    );

    /**
     *   The 1st character must be alphabetic.
     *   The 2nd character must be a "^".
     *   The 3rd character must be numeric (greater than 0).
     *   After 4th characters, sometimes there is a sequence of numeric (0 or higher).
     *   The numeric sequence is followed by nothing or a string containing no invalid characters.
     *   Group 1 is the variable and exponent including the symbol.
     *   Group 2 is the variable. Group 3 is the exponent without symbol. Group 4 is the rest.
     */
    private static final Pattern variableAndExponentPattern = Pattern.compile(
            String.format("^(([a-zA-Z])\\^([1-9][0-9]*))([%s]*)", allowedCharacters)
    );

    /**
     * The 1st character must be alphabetic.
     * The 2nd character must be alphabetic if it exists.
     * If the 3rd and succeeding characters exist, they must not contain any invalid characters.
     * Group 1 is the variable. Group 2 is the rest.
     */
    private static final Pattern variableOnlyPattern = Pattern.compile(
            String.format("^([a-zA-Z])([a-zA-Z][%s]*)?", allowedCharacters)
    );

    public PolynomialFunction parse(String text) {
        return PolynomialFunction.from(Variable.named('x'));
    }

    /**
     * 文字列が項として成立しているか？
     * @param text 判定したい文字列
     * @return 判定結果
     */
    public boolean canParse(String text) {
        return canParse(text, false);
    }

    private boolean canParse(String text, boolean isRecursiveCall) {
        System.out.println(text);
        if (text == null || text.length() == 0) {
            return isRecursiveCall; // 初回実行なら不正な文字列であり、再帰実行なら無事に判定を終えたということ
        }

        // 変数と指数の組か？
        Matcher vem = variableAndExponentPattern.matcher(text);
        if (vem.matches()) {
            // 一組削って再帰
            System.out.println("found variable and exponent: " + vem.group(2) + " & " + vem.group(3));
            return canParse(vem.group(4), true);
        }

        Matcher vm = variableOnlyPattern.matcher(text);
        if (vm.matches()) {
            System.out.println("found variable: " + vm.group(1));
            return canParse(vm.group(2), true);
        }

        // 係数を持つか？
        Matcher cm = coefficientPattern.matcher(text);
        if (cm.matches()) {
            // 係数を削って再帰
            System.out.println("found coefficient: " + cm.group(1));
            return canParse(cm.group(2), true);
        }

        // 負の数か？
        Matcher nm = negativePattern.matcher(text);
        if (nm.matches()) {
            // マイナス記号を削って再帰
            System.out.println("found minus: " + nm.group(1));
            return canParse(nm.group(2), true);
        }

        // 不正な文字列
        return false;
    }
}
