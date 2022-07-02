package blue_chart_solver.models.operators;

import blue_chart_solver.models.Polynomial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Multiply implements Operator {
    private static final Logger logger = LoggerFactory.getLogger(Multiply.class);
    @Override
    public Polynomial operate(Polynomial f1, Polynomial f2) {
        logger.debug("Operate: ({}) * ({})", f1, f2);
        return f1.times(f2);
    }
}
