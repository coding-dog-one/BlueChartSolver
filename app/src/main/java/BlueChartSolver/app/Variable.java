package BlueChartSolver.app;

import java.util.Objects;

public class Variable {
    private final String name;
    private final int exponent;
    private final int degree;

    private Variable(String name, int exponent, int degree) {
        this.name = name;
        this.exponent = exponent;
        this.degree = degree;
    }

    public static Variable named(String name) {
        return new Variable(name, 1, 1);
    }

    public Variable times(int multiplier) {
        return new Variable(this.name, this.exponent, this.degree * multiplier);
    }

    public Variable powerOf(int exponent) {
        return new Variable(this.name, this.exponent == 1 ? exponent : this.exponent + exponent, this.degree);
    }

    @Override
    public String toString() {
        return (degree == 1 ? "" : degree) + name + (exponent == 1 ? "" : "^" + exponent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return exponent == variable.exponent && degree == variable.degree && name.equals(variable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, exponent, degree);
    }
}
