package BlueChartSolver.app;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PolynomialFunction {
    private final Map<String, Term> terms;

    private PolynomialFunction(Map<String, Term> terms) {
        this.terms = terms;
    }

    public static PolynomialFunction from(Term term) {
        Map<String, Term> terms = new HashMap<>();
        terms.put(term.toStringWithoutCoefficient(), term);
        return new PolynomialFunction(terms);
    }

    public static PolynomialFunction from(Variable var) {
        return PolynomialFunction.from(Term.from(var));
    }

    public static PolynomialFunction from(int constant) {
        return PolynomialFunction.from(Term.from(constant));
    }

    public PolynomialFunction plus(PolynomialFunction addend) {
        Map<String, Term> copy = new HashMap<>(this.terms);
        addend.terms.values().forEach(term -> {
            String key = term.toStringWithoutCoefficient();
            if (copy.containsKey(key)) {
                Term thisTerm = copy.get(key);
                copy.replace(key, thisTerm
                        .dividedBy(thisTerm.coefficient())
                        .times(thisTerm.coefficient() + term.coefficient())
                );
            } else {
                copy.put(key, term);
            }
        });
        return new PolynomialFunction(copy);
    }

    public PolynomialFunction plus(Variable addend) {
        return this.plus(PolynomialFunction.from(addend));
    }

    public PolynomialFunction plus(int addend) {
        return this.plus(PolynomialFunction.from(addend));
    }

    public PolynomialFunction minus(PolynomialFunction subtrahend) {
        Map<String, Term> terms = new HashMap<>();
        subtrahend.terms.values().forEach(term -> {
            terms.put(term.toStringWithoutCoefficient(), term.times(-1));
        });
        return this.plus(new PolynomialFunction(terms));
    }

    public PolynomialFunction times(PolynomialFunction multiplier) {
        Map<String, Term> variables = new HashMap<>();
        this.terms.values().forEach(t -> {
            multiplier.terms.values().forEach(m -> {
                Term newTerm = t.times(m);
                String key = newTerm.toStringWithoutCoefficient();
                if (variables.containsKey(key)) {
                    Term oldTerm = variables.get(key);
                    variables.put(key, oldTerm
                            .dividedBy(oldTerm.coefficient())
                            .times(oldTerm.coefficient() + newTerm.coefficient())
                    );
                } else {
                    variables.put(key, newTerm);
                }
            });
        });
        return new PolynomialFunction(variables);
    }

    public PolynomialFunction times(Variable multiplier) {
        return this.times(PolynomialFunction.from(multiplier));
    }

    public PolynomialFunction times(int multiplier) {
        return this.times(PolynomialFunction.from(multiplier));
    }

    public PolynomialFunction powerOf(int exponent) {
        //TODO 多項式の累乗を実装する
        Map<String, Term> terms = new HashMap<>();
        this.terms.values().forEach(term -> {
            Term newTerm = term.powerOf(exponent);  //単項式の想定
            terms.put(newTerm.toStringWithoutCoefficient(), newTerm);
        });
        return new PolynomialFunction(terms);
    }

    private final Pattern minusPattern = Pattern.compile("\\+ -");
    @Override
    public String toString() {
        String s = terms.values().stream()
                .sorted(Comparator.comparing(Term::maxExponent)
                        .reversed()
                        .thenComparing(Term::toStringWithoutCoefficient)
                ).map(Term::toString)
                .collect(Collectors.joining(" + "));
        return minusPattern.matcher(s).replaceAll("- ");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PolynomialFunction)) return false;
        PolynomialFunction that = (PolynomialFunction) o;
        return terms.equals(that.terms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(terms);
    }
}
