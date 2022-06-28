package blue_chart_solver.models.operators;

import blue_chart_solver.models.Polynomial;

public interface Operator {
    Polynomial operate(Polynomial f1, Polynomial f2);
}
