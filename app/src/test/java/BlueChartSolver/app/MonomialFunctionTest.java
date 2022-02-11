package BlueChartSolver.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MonomialFunctionTest {
    @Test
    public void 定数は単項式であり単項式とは項が一つの多項式() {
        PolynomialFunction f = PolynomialFunction.from(5);
        assertEquals("5", f.toString());
    }

    @Test
    public void 変数は単項式() {
        PolynomialFunction f = PolynomialFunction.from(Variable.named('x'));
        assertEquals("x", f.toString());
    }

    @Test
    public void 次数や係数をもつ変数は単項式() {
        PolynomialFunction f1 = Variable.named('x').powerOf(2).times(4);
        assertEquals("4x^2", f1.toString());

        PolynomialFunction f2 = Variable.named('x').times(4).powerOf(2);
        assertEquals("16x^2", f2.toString());
    }

    @Test
    public void 変数に変数を掛けたものは単項式であり変数は辞書順に並ぶ() {
        PolynomialFunction f = Variable.named('c')
                .times(Variable.named('a'))
                .times(Variable.named('b'));
        assertEquals("abc", f.toString());
    }

    @Test
    public void 単項式に単項式を掛けたものは単項式() {
        PolynomialFunction f1 = Variable.named('a').powerOf(2).times(2);
        PolynomialFunction f2 = Variable.named('a').powerOf(3).times(3);
        assertEquals("6a^5", f1.times(f2).toString());

        PolynomialFunction f3 = Variable.named('b').powerOf(3).times(3);
        assertEquals("6a^2b^3", f1.times(f3).toString());
    }

    @Test
    public void 定数と全ての変数の名前と次数が同じ単項式は等価である() {
        PolynomialFunction f1 = Variable.named('a').powerOf(2)
                .times(Variable.named('b').powerOf(3))
                .times(Variable.named('c'))
                .times(5);
        assertEquals("5a^2b^3c", f1.toString());

        PolynomialFunction f2 = Variable.named('a').times(Variable.named('b'));
        assertNotEquals(f1, f2);  //ab
        assertNotEquals(f1, f2.times(Variable.named('c')));  //abc
        assertNotEquals(f1, f2.times(Variable.named('c')).times(Variable.named('a')));  //a^2bc
        assertNotEquals(f1, f2.times(Variable.named('c')).times(Variable.named('a')).times(Variable.named('b').powerOf(2)));  //a^2b^3c
        assertEquals(f1, f2.times(Variable.named('c')).times(Variable.named('a')).times(Variable.named('b').powerOf(2)).times(5));  //5a^2b^3c
    }

    @Test
    public void 同類項はまとめられる() {
        Variable x = Variable.named('x');
        assertEquals(x.times(2), x.plus(x));

        PolynomialFunction x2 = x.powerOf(2);
        assertEquals(x2.times(5), x2.times(2).plus(x2.times(3)));
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
}
