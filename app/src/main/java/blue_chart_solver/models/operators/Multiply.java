package blue_chart_solver.models.operators;

import blue_chart_solver.models.Polynomial;

public class Multiply implements Operator {
    @Override
    public Polynomial operate(Polynomial f1, Polynomial f2) {
        return f1.times(f2);
    }
}
