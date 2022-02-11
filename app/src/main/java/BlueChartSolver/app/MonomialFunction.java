package BlueChartSolver.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MonomialFunction {
    private final int constant;
    private final Map<Character, Integer> exponentMap;

    private MonomialFunction(int constant, Map<Character, Integer> exponentMap) {
        this.constant = constant;
        this.exponentMap = exponentMap;
    }

    public MonomialFunction(Variable var) {
        Map<Character, Integer> exponentMap = new HashMap<>();
        exponentMap.put(var.name(), 1);

        this.constant = 1;
        this.exponentMap = exponentMap;
    }

    public MonomialFunction(int i) {
        this.constant = i;
        this.exponentMap = new HashMap<>();
    }

    public MonomialFunction times(MonomialFunction mf) {
        Map<Character, Integer> copy = new HashMap<>(this.exponentMap);
        mf.exponentMap.forEach((key, value) -> {
            if (copy.containsKey(key)) {
                copy.replace(key, copy.get(key) + value);
            } else {
                copy.put(key, value);
            }
        });
        return new MonomialFunction(this.constant * mf.constant, copy);
    }

    public MonomialFunction times(int i) {
        return this.times(new MonomialFunction(i));
    }

    public MonomialFunction times(Variable var) {
        return this.times(new MonomialFunction(var));
    }

    public MonomialFunction powerOf(int exponent) {
        Map<Character, Integer> copy = new HashMap<>(this.exponentMap);
        copy.entrySet().forEach(es -> es.setValue((es.getValue() * exponent)));
        return new MonomialFunction((int) Math.pow(this.constant, exponent), copy);
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
