package blue_chart_solver.models;

import blue_chart_solver.helpers.SimpleParser;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings({"NonAsciiCharacters", "OptionalGetWithoutIsPresent"})
class PolynomialTest {
    @Test
    void 多項式は着目した変数の次数の高い順に並べることができる() {
        Variable x = Variable.named('x');
        Variable y = Variable.named('y');
        Polynomial p = x.powerOf(2)
                .plus(x.times(y).times(2))
                .plus(y.powerOf(2))
                .minus(5);
        assertEquals("x^2 + 2xy + y^2 - 5", p.toString());
        assertEquals("y^2 + 2xy + x^2 - 5", p.orderByDegreeOf(y).toString());
        assertEquals("x^2 - 5", p.orderByDegreeOf(y).constant().orElse(Polynomial.from(0)).toString());
    }

    @Test
    void findTermReturnsMatchTerm() {
        var f = new SimpleParser().parse("-5x^3 + 3x^2 - 100x + 3");
        assertEquals(-5, f.findTerm(Term.from(Variable.named('x')).powerOf(3)).get().coefficient());
        assertEquals(-5, f.findTerm(Variable.named('x'), 3).get().coefficient());
        assertEquals(-5, f.findTerm('x', 3).get().coefficient());

        assertEquals(3, f.findTerm('x', 2).get().coefficient());
        assertEquals(-100, f.findTerm('x', 1).get().coefficient());

        assertEquals(Optional.empty(), f.findTerm('y', 2));
        assertEquals(Optional.empty(), f.findTerm('x', 10));
    }

    @Test
    void 交換法則() {
        Variable A = Variable.named('A');
        Variable B = Variable.named('B');
        assertEquals("A + B", A.plus(B).toString());
        assertEquals(A.plus(B), B.plus(A));
    }

    @Test
    void 結合法則() {
        Variable A = Variable.named('A');
        Variable B = Variable.named('B');
        Variable C = Variable.named('C');
        assertEquals("A + B + C", (A.plus(B)).plus(C).toString());
        assertEquals((A.plus(B)).plus(C), A.plus(B.plus(C)));
    }

    @Test
    void 分配法則() {
        Variable A = Variable.named('A');
        Variable B = Variable.named('B');
        Variable C = Variable.named('C');
        assertEquals("AB + AC", A.times(B.plus(C)).toString());
        assertEquals(A.times(B.plus(C)), A.times(B).plus(A.times(C)));

        assertEquals("AC + BC", A.plus(B).times(C).toString());
        assertEquals(A.plus(B).times(C), A.times(C).plus(B.times(C)));
    }

    @Test
    void 同類項はまとめられる() {
        Variable x = Variable.named('x');
        Polynomial A = x.powerOf(3).times(5)
                .minus(x.powerOf(2).times(2))
                .plus(x.times(3))
                .plus(4);
        Polynomial B = x.powerOf(3).times(3)
                .minus(x.powerOf(2).times(5))
                .plus(3);
        Polynomial expected = x.powerOf(3).times(2)
                .plus(x.powerOf(2).times(3))
                .plus(x.times(3))
                .plus(1);
        assertEquals(expected, A.minus(B));
    }

    @Test
    void 二次式の展開公式() {
        Variable a = Variable.named('a');
        Variable b = Variable.named('b');
        assertEquals(
                a.powerOf(2).plus(a.times(b).times(2)).plus(b.powerOf(2)),
                a.plus(b).powerOf(2)
        );

        assertEquals(
                a.powerOf(2).minus(a.times(b).times(2)).plus(b.powerOf(2)),
                a.minus(b).powerOf(2)
        );

        assertEquals(
                a.powerOf(2).minus(b.powerOf(2)),
                a.plus(b).times(a.minus(b))
        );

        Variable x = Variable.named('x');
        assertEquals(
                x.powerOf(2).plus((a.plus(b)).times(x)).plus(a.times(b)),
                x.plus(a).times(x.plus(b))
        );

        assertEquals(
                x.powerOf(2).plus((a.plus(b)).times(x)).plus(a.times(b)),
                x.plus(a).times(x.plus(b))
        );

        Variable c = Variable.named('c');
        Variable d = Variable.named('d');
        assertEquals(
                x.powerOf(2).times(a).times(c)
                        .plus(x.times(a.times(d).plus(b.times(c))))
                        .plus(b.times(d)),
                a.times(x).plus(b).times(c.times(x).plus(d))
        );
    }

    @Test
    void 三次式の展開公式() {
        Variable a = Variable.named('a');
        Variable b = Variable.named('b');
        assertEquals(
                a.powerOf(3).plus(b.powerOf(3)),
                a.plus(b).times(a.powerOf(2).minus(a.times(b)).plus(b.powerOf(2)))
        );

        assertEquals(
                a.powerOf(3).minus(b.powerOf(3)),
                a.minus(b).times(a.powerOf(2).plus(a.times(b)).plus(b.powerOf(2)))
        );

        assertEquals(
                a.powerOf(3)
                        .plus(a.powerOf(2).times(b).times(3))
                        .plus(b.powerOf(2).times(a).times(3))
                        .plus(b.powerOf(3)),
                a.plus(b).powerOf(3)
        );

        assertEquals(
                a.powerOf(3)
                        .minus(a.powerOf(2).times(b).times(3))
                        .plus(b.powerOf(2).times(a).times(3))
                        .minus(b.powerOf(3)),
                a.minus(b).powerOf(3)
        );
    }
}
