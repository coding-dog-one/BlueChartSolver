package blue_chart_solver.models;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class OrderedTermsList {
    private final List<Term> terms;
    private final Set<Variable> focusedVariables;
    private final Pattern minusPattern = Pattern.compile("\\+ -");

    private OrderedTermsList(List<Term> terms, Set<Variable> focusedVariables) {
        this.terms = Objects.requireNonNull(terms, "terms");
        this.focusedVariables = Objects.requireNonNull(focusedVariables, "focusedVariables");
    }

    /**
     * 着目した変数について、次数の高い順に項をソートする(xに着目したとき、x^3(=3) > x^2(=2) > 1(=0))。
     * 次いで、項全体の次数の高い順にソートする(xに着目したとき、abx^2(=4) > ax^2(=3) > x^2(=2))。
     * 次いで、項の中の各変数の次数の最大値が大きい順にソートする(xに着目したとき、a^3x(=3) > ab^2x(=2) > abcx(=1))。
     * その後、各項から係数を除いたものの辞書順にソートする(xに着目したとき、各定数について、8ab > -3ac > 2bc)。
     *
     * @param focusedVariables 着目する変数
     */
    public static OrderedTermsList orderByDegree(Set<Term> terms, Set<Variable> focusedVariables) {
        List<Term> orderedList = terms.stream()
                .sorted(comparingDegreeOf(focusedVariables).reversed()
                        .thenComparing(Comparator.comparing(Term::degree).reversed())
                        .thenComparing(Comparator.comparing(Term::maxDegree).reversed())
                        .thenComparing(Term::toStringWithoutCoefficient)
                ).collect(Collectors.toList());
        return new OrderedTermsList(orderedList, focusedVariables);
    }

    private static Comparator<Term> comparingDegreeOf(Set<Variable> focusedVariables) {
        return Comparator.comparingInt(t -> t.degreeOf(focusedVariables));
    }

    public int degree() {
        return terms.get(0).degreeOf(focusedVariables);
    }

    public Optional<Polynomial> constant() {
        return terms.stream()
                .filter(t -> t.variables().stream().noneMatch(focusedVariables::contains))
                .map(Polynomial::from)
                .reduce(Polynomial::plus);
    }

    @Override
    public String toString() {
        String s = terms.stream().map(Term::toString).collect(Collectors.joining(" + "));
        return minusPattern.matcher(s).replaceAll("- ");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderedTermsList)) return false;
        OrderedTermsList that = (OrderedTermsList) o;
        return terms.equals(that.terms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(terms);
    }
}
