package blue_chart_solver.models.operators;

import blue_chart_solver.models.Polynomial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Subtraction implements Operator {
    private static final Logger logger = LoggerFactory.getLogger(Subtraction.class);
    @Override
    public Polynomial operate(Polynomial f1, Polynomial f2) {
        logger.debug("operate: ({}) - ({})", f1, f2);
        return f1.minus(f2);
    }
}
