package BlueChartSolver.app;

import java.util.*;
import java.util.stream.Collectors;

public class PolynomialFunction {
    private final Map<String, Term> terms;

    private PolynomialFunction(Map<String, Term> terms) {
        this.terms = validateTerms(Objects.requireNonNull(terms, "terms"));
    }

    /**
     * 係数が0の項を取り除く
     */
    private Map<String, Term> validateTerms(Map<String, Term> terms) {
        Map<String, Term> result = new HashMap<>();
        terms.values().stream()
                .filter(t -> t.coefficient() != 0)
                .forEach(t -> result.put(t.toStringWithoutCoefficient(), t));
        return result;
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
                copy.replace(key, thisTerm.plus(term));
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
        Map<String, Term> reversed = new HashMap<>();
        subtrahend.terms.values().forEach(term -> reversed.put(term.toStringWithoutCoefficient(), term.times(-1)));
        return this.plus(new PolynomialFunction(reversed));
    }

    public PolynomialFunction minus(Variable subtrahend) {
        return this.minus(PolynomialFunction.from(subtrahend));
    }

    public PolynomialFunction minus(int subtrahend) {
        return this.plus(subtrahend * -1);
    }

    public PolynomialFunction times(PolynomialFunction multiplier) {
        Map<String, Term> variables = new HashMap<>();
        this.terms.values().forEach(t -> multiplier.terms.values().forEach(m -> {
            Term newTerm = t.times(m);
            String key = newTerm.toStringWithoutCoefficient();
            if (variables.containsKey(key)) {
                variables.replace(key, variables.get(key).plus(newTerm));
            } else {
                variables.put(key, newTerm);
            }
        }));
        return new PolynomialFunction(variables);
    }

    public PolynomialFunction times(Variable multiplier) {
        return this.times(PolynomialFunction.from(multiplier));
    }

    public PolynomialFunction times(int multiplier) {
        return this.times(PolynomialFunction.from(multiplier));
    }

    public PolynomialFunction powerOf(int exponent) {
        PolynomialFunction result = new PolynomialFunction(new HashMap<>(this.terms));
        for (int i = 0; i < exponent - 1; i++) {
            result = result.times(this);
        }
        return result;
    }

    public OrderedTermsList orderByDegreeOf(Set<Variable> focusedVariables) {
        return OrderedTermsList.orderByDegree(new HashSet<>(terms.values()), focusedVariables);
    }

    public OrderedTermsList orderByDegreeOf(Variable variable) {
        return orderByDegreeOf(Set.of(variable));
    }

    @Override
    public String toString() {
        Set<Variable> allVariables = terms.values().stream()
                .map(Term::variables)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        return orderByDegreeOf(allVariables).toString();
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
