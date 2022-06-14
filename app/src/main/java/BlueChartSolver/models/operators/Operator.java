package BlueChartSolver.models.operators;

import BlueChartSolver.models.Polynomial;

public interface Operator {
    Polynomial operate(Polynomial f1, Polynomial f2);
}
