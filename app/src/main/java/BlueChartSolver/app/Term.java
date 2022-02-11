package BlueChartSolver.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Term {
    private final int coefficient;
    private final Map<Variable, Integer> variables;  // map of <variable, exponent>

    private Term(int coefficient, Map<Variable, Integer> variables) {
        this.coefficient = coefficient;
        this.variables = variables;
    }

    public static Term from(Variable var) {
        Map<Variable, Integer> variables = new HashMap<>();
        variables.put(var, 1);
        return new Term(1, variables);
    }

    public static Term from(int constant) {
        return new Term(constant, new HashMap<>());
    }

    public Term times(Term multiplier) {
        Map<Variable, Integer> copy = new HashMap<>(this.variables);
        multiplier.variables.forEach((key, value) -> {
            if (copy.containsKey(key)) {
                copy.replace(key, copy.get(key) + value);
            } else {
                copy.put(key, value);
            }
        });
        int coefficient = this.coefficient * multiplier.coefficient;
        return new Term(coefficient, copy);
    }

    public Term times(int multiplier) {
        return this.times(Term.from(multiplier));
    }

    public Term dividedBy(int divisor) {
        Map<Variable, Integer> copy = new HashMap<>(this.variables);
        return new Term(this.coefficient / divisor, copy);
    }

    public Term powerOf(int exponent) {
        Map<Variable, Integer> copy = new HashMap<>(this.variables);
        copy.entrySet().forEach(es -> es.setValue((es.getValue() * exponent)));
        return new Term((int) Math.pow(this.coefficient, exponent), copy);
    }

    public int coefficient() {
        return coefficient;
    }

    public int maxExponent() {
        return variables.values().stream().max(Integer::compareTo).orElse(0);
    }

    public String toStringWithoutCoefficient() {
        return variables.entrySet().stream()
                .map(es -> es.getKey() + (es.getValue() == 1 ? "" : "^" + es.getValue()))
                .sorted()
                .collect(Collectors.joining());
    }

    @Override
    public String toString() {
        return (coefficient == 1 ? "" : coefficient == -1 ? "-" : coefficient) + toStringWithoutCoefficient();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Term)) return false;
        Term that = (Term) o;
        return coefficient == that.coefficient && variables.equals(that.variables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coefficient, variables);
    }
}
