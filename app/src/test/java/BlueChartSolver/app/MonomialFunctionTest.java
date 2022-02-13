package BlueChartSolver.app;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("NonAsciiCharacters")
public class MonomialFunctionTest {
    @Test
    public void 定数は単項式であり単項式とは項が一つの多項式() {
        Term t = Term.from(5);
        assertEquals("5", t.toString());

        PolynomialFunction f = PolynomialFunction.from(t);
        assertEquals("5", f.toString());
    }

    @Test
    public void 変数は単項式() {
        Term t = Term.from(Variable.named('x'));
        assertEquals("x", t.toString());

        PolynomialFunction f = PolynomialFunction.from(t);
        assertEquals("x", f.toString());
    }

    @Test
    public void 次数や係数をもつ変数は単項式() {
        Term t1 = Term.from(Variable.named('x')).powerOf(2).times(4);
        assertEquals("4x^2", t1.toString());

        PolynomialFunction f1 = Variable.named('x').powerOf(2).times(4);
        assertEquals("4x^2", f1.toString());

        Term t2 = Term.from(Variable.named('x')).times(4).powerOf(2);
        assertEquals("16x^2", t2.toString());

        PolynomialFunction f2 = Variable.named('x').times(4).powerOf(2);
        assertEquals("16x^2", f2.toString());
    }

    @Test
    public void 変数に変数を掛けたものは単項式であり変数は辞書順に並ぶ() {
        Term t = Term.from(Variable.named('c'))
                .times(Term.from(Variable.named('a')))
                .times(Term.from(Variable.named('b')));
        assertEquals("abc", t.toString());

        PolynomialFunction f = Variable.named('c')
                .times(Variable.named('a'))
                .times(Variable.named('b'));
        assertEquals("abc", f.toString());
    }

    @Test
    public void 単項式に単項式を掛けたものは単項式() {
        Term t1 = Term.from(Variable.named('a')).powerOf(2).times(2);
        Term t2 = Term.from(Variable.named('a')).powerOf(3).times(3);
        assertEquals("6a^5", t1.times(t2).toString());

        PolynomialFunction f1 = Variable.named('a').powerOf(2).times(2);
        PolynomialFunction f2 = Variable.named('a').powerOf(3).times(3);
        assertEquals("6a^5", f1.times(f2).toString());

        Term t3 = Term.from(Variable.named('b')).powerOf(3).times(3);
        assertEquals("6a^2b^3", t1.times(t3).toString());

        PolynomialFunction f3 = Variable.named('b').powerOf(3).times(3);
        assertEquals("6a^2b^3", f1.times(f3).toString());
    }

    @Test
    public void 定数と全ての変数の名前と次数が同じ単項式は等価である() {
        PolynomialFunction f1 = Variable.named('a').powerOf(2)
                .times(Variable.named('b').powerOf(3))
                .times(5);
        assertEquals("5a^2b^3", f1.toString());

        PolynomialFunction f2 = Variable.named('a').times(Variable.named('b'));
        assertNotEquals(f1, f2);  //ab
        assertNotEquals(f1, f2.times(Variable.named('a')));  //a^2b
        assertNotEquals(f1, f2.times(Variable.named('a')).times(Variable.named('b').powerOf(2)));  //a^2b^3
        assertEquals(f1, f2.times(Variable.named('a')).times(Variable.named('b').powerOf(2)).times(5));  //5a^2b^3
    }

    @Test
    public void 文字とそれらの次数が同じ項は同類項() {
        Term t1 = Term.from(Variable.named('a')).powerOf(3);
        assertNotEquals(t1, t1.times(100));
        assertTrue(t1.likes(t1.times(100)));

        assertNotEquals(t1, t1.times(Term.from(Variable.named('b'))));
        assertFalse(t1.likes(t1.times(Term.from(Variable.named('b')))));
    }

    @Test
    public void 同類項はまとめられる() {
        Term t = Term.from(Variable.named('x'));
        assertEquals(t.times(2), t.plus(t));

        Variable x = Variable.named('x');
        assertEquals(x.times(2), x.plus(x));

        Term t2 = Term.from(x).powerOf(2);
        assertEquals(t2.times(5), t2.times(2).plus(t2.times(3)));

        PolynomialFunction x2 = x.powerOf(2);
        assertEquals(x2.times(5), x2.times(2).plus(x2.times(3)));

        assertThrows(AssertionError.class, () -> {
            // Term#plusは同類項の和を求めるのに使用する
           t.plus(t2);
        });
    }

    @Test
    public void 交換法則() {
        Variable a = Variable.named('a');
        Variable b = Variable.named('b');
        assertEquals(a.times(b), b.times(a));
    }

    @Test
    public void 結合法則() {
        Variable a = Variable.named('a');
        Variable b = Variable.named('b');
        Variable c = Variable.named('c');
        assertEquals((a.times(b)).times(c), a.times((b.times(c))));
    }

    @Test
    public void 指数法則() {
        Variable a = Variable.named('a');
        int m = 2;
        int n = 3;
        assertEquals(a.powerOf(m).times(a.powerOf(n)), a.powerOf(m + n));
        assertEquals(a.powerOf(m).powerOf(n), a.powerOf(m * n));

        Variable b = Variable.named('b');
        assertEquals(a.times(b).powerOf(n), a.powerOf(n).times(b.powerOf(n)));
    }

    @Test
    public void 次数の取得() {
        Variable x = Variable.named('x');
        Variable y = Variable.named('y');
        Term t = Term.from(x).powerOf(3);
        assertEquals(3, t.degree());

        Term t2 = t.times(Term.from(y).powerOf(2));
        assertEquals(5, t2.degree());
        assertEquals(5, t2.degreeOf(Set.of(x, y)));
        assertEquals(3, t2.degreeOf(x));

        Term constant = Term.from(5);
        assertEquals(0, constant.degree());
    }
}
