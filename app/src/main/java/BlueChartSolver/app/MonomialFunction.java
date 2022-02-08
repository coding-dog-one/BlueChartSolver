package BlueChartSolver.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MonomialFunction {
    private int constant = 1;
    private final Map<Character, Integer> exponentMap = new HashMap<>();

    public MonomialFunction(Variable var) {
        exponentMap.put(var.name(), 1);
    }

    public MonomialFunction(int i) {
        constant *= i;
    }

    public MonomialFunction times(int i) {
        constant *= i;
        return this;
    }

    public MonomialFunction times(Variable var) {
        if (exponentMap.containsKey(var.name())) {
            exponentMap.replace(var.name(), exponentMap.get(var.name()) + 1);
        } else {
            exponentMap.put(var.name(), 1);
        }
        return this;
    }

    public MonomialFunction powerOf(int exponent) {
        constant = (int) Math.pow(constant, exponent);
        exponentMap.entrySet().forEach(es -> es.setValue((es.getValue() * exponent)));
        return this;
    }

    @Override
    public String toString() {
        return (constant == 1 ? "" : constant == -1 ? "-" : constant) +
                exponentMap.entrySet().stream()
                        .map(es -> es.getKey() + (es.getValue() == 1 ? "" : "^" + es.getValue()))
                        .sorted()
                        .collect(Collectors.joining());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MonomialFunction)) return false;
        MonomialFunction that = (MonomialFunction) o;
        return constant == that.constant && exponentMap.equals(that.exponentMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(constant, exponentMap);
    }
}
