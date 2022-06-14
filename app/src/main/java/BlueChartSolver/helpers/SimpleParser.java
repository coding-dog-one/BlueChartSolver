package BlueChartSolver.helpers;

import BlueChartSolver.models.Polynomial;
import BlueChartSolver.models.operators.Addition;
import BlueChartSolver.models.operators.Operator;

public class SimpleParser {
    private static final TermParser termParser = new TermParser();
    private static final OperatorParser operatorParser = new OperatorParser();

    public Polynomial parse(String text) {
        String[] splitText = text.split(" ");
        var length = splitText.length;
        if (length % 2 == 0) {
            throw new IllegalArgumentException("Please input terms and operators as space-separated text. The length of split input should be odd but was " + length + ".");
        }

        var result = Polynomial.from(0);
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
