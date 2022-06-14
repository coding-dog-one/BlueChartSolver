package BlueChartSolver.helpers;

import BlueChartSolver.models.operators.Addition;
import BlueChartSolver.models.operators.Multiply;
import BlueChartSolver.models.operators.Operator;
import BlueChartSolver.models.operators.Subtraction;

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
