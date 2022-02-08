package BlueChartSolver.app;

import java.util.Objects;

public class Variable {
    private final char name;

    private Variable(char name) {
        assert Character.isAlphabetic(name)
                : "name: " + name + " is invalid. Only alphabetical character is accepted.";
        this.name = name;
    }

    public static Variable named(char name) {
        return new Variable(name);
    }

    public MonomialFunction times(int multiplier) {
        return new MonomialFunction(this).times(multiplier);
    }

    public MonomialFunction powerOf(int exponent) {
        return new MonomialFunction(this).powerOf(exponent);
    }

    public MonomialFunction times(Variable var) {
        return new MonomialFunction(this).times(var);
    }

    public char name() {
        return name;
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return name == variable.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
