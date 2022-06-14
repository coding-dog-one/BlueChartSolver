package BlueChartSolver.models.operators;

import BlueChartSolver.models.Polynomial;

public class Addition implements Operator {

    @Override
    public Polynomial operate(Polynomial f1, Polynomial f2) {
        return f1.plus(f2);
    }
}
