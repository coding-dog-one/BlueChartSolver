package BlueChartSolver.app.Parser;

import BlueChartSolver.app.Operator.Addition;
import BlueChartSolver.app.Operator.Operator;
import BlueChartSolver.app.PolynomialFunction;

import java.util.regex.Pattern;

public class SimpleParser {
    private static final Pattern termPattern = Pattern.compile("^-?[a-zA-Z0-9^]+");
    private static final TermParser termParser = new TermParser();
    private static final OperatorParser operatorParser = new OperatorParser();

    public PolynomialFunction parse(String text) {
        String[] splitText = text.split(" ");
        if (splitText.length % 2 == 0) {
            throw new IllegalArgumentException("Input terms and operators as space-separated text. The length after split should be odd.");
        }

        var result = PolynomialFunction.from(0);
        var operator = (Operator) new Addition();
        for (int i = 0; i < splitText.length; i++) {
            String s = splitText[i];
            System.out.println(s);
            if (i % 2 == 0) {
                result = operator.operate(result, termParser.parse(s));
            } else {
                operator = operatorParser.parse(s);
            }
        }
        return result;
    }
}
