package BlueChartSolver.app.Parser;

import BlueChartSolver.app.Operator.Operator;
import BlueChartSolver.app.PolynomialFunction;

import java.util.regex.Pattern;

public class SimpleParser {
    private static final Pattern termPattern = Pattern.compile("^-?[a-zA-Z0-9^]+");
    private static final TermParser termParser = new TermParser();
    private static final OperatorParser operatorParser = new OperatorParser();
    public PolynomialFunction parse(String text) {
        String[] splitText = text.split(" ");
        assert splitText.length >= 1 && termPattern.matcher(splitText[0]).matches(): "The argument text must start with a term.";

        PolynomialFunction result = null;
        PolynomialFunction f;
        Operator op = null;
        for (String s : splitText) {
            if (termPattern.matcher(s).matches()) {
                f = termParser.parse(s);
                result = (result == null) ? f : op.operate(result, f);
            } else {
                op = operatorParser.parse(s);
            }
        }
        return result;
    }
}
