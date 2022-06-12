package BlueChartSolver.app.Parser;

import BlueChartSolver.app.Operator.Addition;
import BlueChartSolver.app.Operator.Multiply;
import BlueChartSolver.app.Operator.Operator;
import BlueChartSolver.app.Operator.Subtraction;

public class OperatorParser {
    public Operator parse(String text) {
        switch (text) {
            case "+":
                return new Addition();
            case "-":
                return new Subtraction();
            case "*":
                return new Multiply();
            default:
                throw new IllegalArgumentException("Invalid text was given. Expected: (+|-|*)");
        }
    }
}
