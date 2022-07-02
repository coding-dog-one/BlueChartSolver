package blue_chart_solver.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Polynomial {
    private final Map<String, Term> terms;

    private Polynomial(Map<String, Term> terms) {
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

    public static Polynomial from(Term term) {
        Map<String, Term> terms = new HashMap<>();
        terms.put(term.toStringWithoutCoefficient(), term);
        return new Polynomial(terms);
    }

    public static Polynomial from(Variable variable) {
        return Polynomial.from(Term.from(variable));
    }

    public static Polynomial from(int constant) {
        return Polynomial.from(Term.from(constant));
    }

    public Optional<Term> findTerm(Term term) {
        return terms.values().stream()
                .filter(t -> t.likes(term))
                .findFirst();
    }

    public Optional<Term> findTerm(Variable variable, int exponent) {
        return findTerm(Term.from(variable).powerOf(exponent));
    }

    public Optional<Term> findTerm(char variableName, int exponent) {
        return findTerm(Variable.named(variableName), exponent);
    }

    public Polynomial plus(Polynomial addend) {
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
        return new Polynomial(copy);
    }

    public Polynomial plus(Variable addend) {
        return this.plus(Polynomial.from(addend));
    }

    public Polynomial plus(int addend) {
        return this.plus(Polynomial.from(addend));
    }

    public Polynomial minus(Polynomial subtrahend) {
        Map<String, Term> reversed = new HashMap<>();
        subtrahend.terms.values().forEach(term -> reversed.put(term.toStringWithoutCoefficient(), term.times(-1)));
        return this.plus(new Polynomial(reversed));
    }

    public Polynomial minus(Variable subtrahend) {
        return this.minus(Polynomial.from(subtrahend));
    }

    public Polynomial minus(int subtrahend) {
        return this.plus(subtrahend * -1);
    }

    public Polynomial times(Polynomial multiplier) {
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
        return new Polynomial(variables);
    }

    public Polynomial times(Variable multiplier) {
        return this.times(Polynomial.from(multiplier));
    }

    public Polynomial times(int multiplier) {
        return this.times(Polynomial.from(multiplier));
    }

    public Polynomial powerOf(int exponent) {
        Polynomial result = new Polynomial(new HashMap<>(this.terms));
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

    public OrderedTermsList orderByDegreeOf(char... characters) {
        var variableSet = new HashSet<Variable>();
        for (var c : characters) {
            variableSet.add(Variable.named(c));
        }
        return orderByDegreeOf(variableSet);
    }

    @Override
    public String toString() {
        if (terms.size() == 0) {
            return "0";
        }
        return OrderedTermsList.orderByDegree(new HashSet<>(terms.values())).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Polynomial)) return false;
        Polynomial that = (Polynomial) o;
        return terms.equals(that.terms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(terms);
    }
}
