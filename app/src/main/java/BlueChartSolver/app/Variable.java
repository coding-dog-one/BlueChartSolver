package BlueChartSolver.app;

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

    public PolynomialFunction plus(PolynomialFunction addend) {
        return PolynomialFunction.from(this).plus(addend);
    }

    public PolynomialFunction plus(Variable addend) {
        return PolynomialFunction.from(this).plus(addend);
    }

    public PolynomialFunction minus(PolynomialFunction subtrahend) {
        return PolynomialFunction.from(this).minus(subtrahend);
    }

    public PolynomialFunction minus(Variable subtrahend) {
        return PolynomialFunction.from(this).minus(subtrahend);
    }

    public PolynomialFunction times(PolynomialFunction multiplier) {
        return PolynomialFunction.from(this).times(multiplier);
    }

    public PolynomialFunction times(Variable multiplier) {
        return PolynomialFunction.from(this).times(multiplier);
    }

    public PolynomialFunction times(int multiplier) {
        return PolynomialFunction.from(this).times(multiplier);
    }

    public PolynomialFunction powerOf(int exponent) {
        return PolynomialFunction.from(this).powerOf(exponent);
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
