package BlueChartSolver.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MonomialFunction {
    private int degree = 1;
    private final Map<String, Variable> vars = new HashMap<>();

    public MonomialFunction(Variable var) {
        degree *= var.degree();
        vars.put(var.name(), Variable.named(var.name()).powerOf(var.exponent()));
    }

    public MonomialFunction times(int i) {
        degree *= i;
        return this;
    }

    public MonomialFunction times(Variable var) {
        degree *= var.degree();
        if (vars.containsKey(var.name())) {
            vars.replace(var.name(), Variable.named(var.name()).powerOf(vars.get(var.name()).exponent() + var.exponent()));
        } else {
            vars.put(var.name(), Variable.named(var.name()).powerOf(var.exponent()));
        }
        return this;
    }

    @Override
    public String toString() {
        return degree == 1 ? "" : degree + vars.values().stream()
                .map(Variable::toString)
                .sorted()
                .collect(Collectors.joining());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MonomialFunction)) return false;
        MonomialFunction that = (MonomialFunction) o;
        return degree == that.degree && vars.equals(that.vars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(degree, vars);
    }
}
