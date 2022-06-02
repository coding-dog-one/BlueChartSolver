package BlueChartSolver.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Term {
    private final int coefficient;
    private final Map<Variable, Integer> variables;  // map of <variable, exponent>

    private Term(int coefficient, Map<Variable, Integer> variables) {
        this.coefficient = coefficient;
        this.variables = Objects.requireNonNull(variables, "variables");
    }

    public static Term from(Variable var) {
        Map<Variable, Integer> variables = new HashMap<>();
        variables.put(var, 1);
        return new Term(1, variables);
    }

    public static Term from(int constant) {
        return new Term(constant, new HashMap<>());
    }

    public Term plus(Term addend) {
        assert this.likes(addend) : "can't add unlike term.";
        return new Term(this.coefficient + addend.coefficient, new HashMap<>(this.variables));
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

    public Term powerOf(int exponent) {
        Map<Variable, Integer> copy = new HashMap<>(this.variables);
        copy.entrySet().forEach(es -> es.setValue((es.getValue() * exponent)));
        return new Term((int) Math.pow(this.coefficient, exponent), copy);
    }

    public int maxDegree() {
        return variables.values().stream().max(Integer::compareTo).orElse(0);
    }

    public int degreeOf(Set<Variable> variableSet) {
        return variables.entrySet().stream()
                .filter(es -> variableSet.contains(es.getKey()))
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    public int degreeOf(Variable variable) {
        return degreeOf(Set.of(variable));
    }

    public int degree() {
        return degreeOf(this.variables.keySet());
    }

    public int coefficient() {
        return coefficient;
    }

    public Set<Variable> variables() {
        return variables.keySet();
    }

    public String toStringWithoutCoefficient() {
        return variables.entrySet().stream()
                .map(es -> es.getKey() + (es.getValue() == 1 ? "" : "^" + es.getValue()))
                .sorted()
                .collect(Collectors.joining());
    }

    @Override
    public String toString() {
        if (variables.size() == 0) { return Integer.toString(coefficient); }
        return (coefficient == 1 ? "" : coefficient == -1 ? "-" : coefficient) + toStringWithoutCoefficient();
    }

    public boolean likes(Term other) {
        return this.variables.equals(other.variables);
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
