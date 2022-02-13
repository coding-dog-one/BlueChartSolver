package BlueChartSolver.app;

import java.util.*;
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
     * 定数など、次数が同じもの同士は、項の中の各変数の次数の最大値が大きい順にソートする(xに着目したとき、y^3(=3) > 5ab(=1) > 1(=0))。
     * その後、各項から係数を除いたものの辞書順にソートする(xに着目したとき、各定数について、8ab > -3ac > 2bc)。
     *
     * @param focusedVariables 着目する変数
     */
    public static OrderedTermsList orderByDegree(Set<Term> terms, Set<Variable> focusedVariables) {
        List<Term> orderedList = terms.stream()
                .sorted(comparingDegreeOf(focusedVariables).reversed()
                        .thenComparing(Comparator.comparing(Term::maxDegree).reversed())
                        .thenComparing(Term::toStringWithoutCoefficient)
                ).collect(Collectors.toList());
        return new OrderedTermsList(orderedList, focusedVariables);
    }

    private static Comparator<Term> comparingDegreeOf(Set<Variable> focusedVariables) {
        return Comparator.comparingInt(t -> t.degreeOf(focusedVariables));
    }

    public Optional<PolynomialFunction> constant() {
        return terms.stream()
                .filter(t -> t.variables().stream().noneMatch(focusedVariables::contains))
                .map(PolynomialFunction::from)
                .reduce(PolynomialFunction::plus);
    }

    @Override
    public String toString() {
        String s = terms.stream().map(Term::toString).collect(Collectors.joining(" + "));
        return minusPattern.matcher(s).replaceAll("- ");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlueChartSolver.app.OrderedTermsList)) return false;
        BlueChartSolver.app.OrderedTermsList that = (BlueChartSolver.app.OrderedTermsList) o;
        return terms.equals(that.terms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(terms);
    }
}
