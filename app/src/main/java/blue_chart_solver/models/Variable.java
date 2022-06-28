package blue_chart_solver.models;

import java.util.Objects;

public class Variable {
    private final char name;

    private Variable(char name) {
        assert Character.isAlphabetic(name)
                : "name: " + name + " is invalid. Only an alphabetical character is accepted.";
        this.name = name;
    }

    public static Variable named(char name) {
        return new Variable(name);
    }

    public Polynomial plus(Polynomial addend) {
        return Polynomial.from(this).plus(addend);
    }

    public Polynomial plus(Variable addend) {
        return Polynomial.from(this).plus(addend);
    }

    public Polynomial minus(Polynomial subtrahend) {
        return Polynomial.from(this).minus(subtrahend);
    }

    public Polynomial minus(Variable subtrahend) {
        return Polynomial.from(this).minus(subtrahend);
    }

    public Polynomial times(Polynomial multiplier) {
        return Polynomial.from(this).times(multiplier);
    }

    public Polynomial times(Variable multiplier) {
        return Polynomial.from(this).times(multiplier);
    }

    public Polynomial times(int multiplier) {
        return Polynomial.from(this).times(multiplier);
    }

    public Polynomial powerOf(int exponent) {
        return Polynomial.from(this).powerOf(exponent);
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
