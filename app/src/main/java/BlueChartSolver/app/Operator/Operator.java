package BlueChartSolver.app.Operator;

import BlueChartSolver.app.PolynomialFunction;

public interface Operator {
    PolynomialFunction operate(PolynomialFunction f1, PolynomialFunction f2);
}
