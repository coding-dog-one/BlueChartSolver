package BlueChartSolver.app.Parser;

import BlueChartSolver.app.PolynomialFunction;
import BlueChartSolver.app.Variable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TermParser {
    private static final String allowedCharacters = "a-zA-Z0-9^";

    /**
     *  The 1st character must be a "-".<br>
     *  The 2nd character must be alphabetic or numeric (greater than 0).<br>
     *  If the 3rd and succeeding characters exist, they must not contain any invalid characters.
     *  <ul>
     *      <li>Group 1 is the minus sign.</li>
     *      <li>Group 2 is the rest.</li>
     *  </ul>
     */
    private static final Pattern negativePattern = Pattern.compile(
            String.format("^(-)(([a-zA-Z]|[1-9])[%s]*)", allowedCharacters)
    );

    /**
     * The 1st character must be numeric (greater than 0).<br>
     * After 2nd character, sometimes there is a sequence of numeric (0 or higher).<br>
     * The numeric sequence is followed by nothing or a string containing no invalid characters.
     * <ul>
     *      <li>Group 1 is the coefficient.</li>
     *      <li>Groups 2 is the rest.</li>
     */
    private static final Pattern coefficientPattern = Pattern.compile(
            String.format("^([1-9][0-9]*)([%s]*)", allowedCharacters)
    );

    /**
     *   The 1st character must be alphabetic.<br>
     *   The 2nd character must be a "^".<br>
     *   The 3rd character must be numeric (greater than 0).<br>
     *   After 4th characters, sometimes there is a sequence of numeric (0 or higher).<br>
     *   The numeric sequence is followed by nothing or a string containing no invalid characters.
     *   <ul>
     *     <li>Group 1 is the variable and exponent including the symbol.</li>
     *     <li>Group 2 is the variable. </li>
     *     <li>Group 3 is the exponent without symbol. </li>
     *     <li>Group 4 is the rest.</li>
     *   </ul>
     */
    private static final Pattern variableAndExponentPattern = Pattern.compile(
            String.format("^(([a-zA-Z])\\^([1-9][0-9]*))([%s]*)", allowedCharacters)
    );

    /**
     * The 1st character must be alphabetic.<br>
     * The 2nd character must be alphabetic if it exists.<br>
     * If the 3rd and succeeding characters exist, they must not contain any invalid characters.
     * <ul>
     *     <li>Group 1 is the variable.</li>
     *     <li>Group 2 is the rest.</li>
     * </ul>
     */
    private static final Pattern variableOnlyPattern = Pattern.compile(
            String.format("^([a-zA-Z])([a-zA-Z][%s]*)?", allowedCharacters)
    );

    public PolynomialFunction parse(String text) {
        return parse(text, false);
    }

    private PolynomialFunction parse(String text, boolean isRecursiveCall) {
        System.out.println(text);
        if (text == null || text.length() == 0) {
            if (!isRecursiveCall) {
                throw new IllegalArgumentException("Failed to parse text as term. Empty string is not allowed.");
            }

            return PolynomialFunction.from(1);
        }

        Matcher vem = variableAndExponentPattern.matcher(text);
        if (vem.matches()) {
            var variable = Variable.named(vem.group(2).charAt(0));
            var exponent = Integer.parseInt(vem.group(3));
            var other = vem.group(4);
            System.out.println("found variable and exponent: " + variable + " power of " + exponent);
            return parse(other, true).times(variable.powerOf(exponent));
        }

        Matcher vm = variableOnlyPattern.matcher(text);
        if (vm.matches()) {
            var variable = Variable.named(vm.group(1).charAt(0));
            var other = vm.group(2);
            System.out.println("found variable: " + variable);
            return parse(other, true).times(variable);
        }

        Matcher cm = coefficientPattern.matcher(text);
        if (cm.matches()) {
            var coefficient = Integer.parseInt(cm.group(1));
            var other = cm.group(2);
            System.out.println("found coefficient: " + coefficient);
            return parse(other, true).times(coefficient);
        }

        Matcher nm = negativePattern.matcher(text);
        if (nm.matches()) {
            var minus = nm.group(1);
            var other = nm.group(2);
            System.out.println("found minus: " + minus);
            return parse(other, true).times(-1);
        }

        throw new IllegalArgumentException("Failed to parse text as term. Text is not empty but invalid.");
    }
}
