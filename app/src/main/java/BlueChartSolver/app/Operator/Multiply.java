package BlueChartSolver.app.Operator;

import BlueChartSolver.app.PolynomialFunction;

public class Multiply implements Operator {
    @Override
    public PolynomialFunction operate(PolynomialFunction f1, PolynomialFunction f2) {
        return f1.times(f2);
    }
}
