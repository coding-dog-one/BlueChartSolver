package BlueChartSolver.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MonomialFunctionTest {
    @Test
    public void 定数は単項式() {
        MonomialFunction mf = new MonomialFunction(5);
        assertEquals("5", mf.toString());
    }

    @Test
    public void 変数は単項式() {
        MonomialFunction mf = new MonomialFunction(Variable.named('x'));
        assertEquals("x", mf.toString());
    }

    @Test
    public void 次数や係数をもつ変数は単項式() {
        MonomialFunction mf1 = Variable.named('x').powerOf(2).times(4);
        assertEquals("4x^2", mf1.toString());

        MonomialFunction mf2 = Variable.named('x').times(4).powerOf(2);
        assertEquals("16x^2", mf2.toString());
    }

    @Test
    public void 変数に変数を掛けたものは単項式であり変数は辞書順に並ぶ() {
        MonomialFunction mf = Variable.named('c').times(Variable.named('a')).times(Variable.named('b'));
        assertEquals("abc", mf.toString());
    }

    @Test
    public void 単項式に単項式を掛けたものは単項式() {
        MonomialFunction mf1 = Variable.named('a').powerOf(2).times(2);
        MonomialFunction mf2 = Variable.named('a').powerOf(3).times(3);
        assertEquals("6a^5", mf1.times(mf2).toString());

        MonomialFunction mf3 = Variable.named('b').powerOf(3).times(3);
        assertEquals("6a^2b^3", mf1.times(mf3).toString());
    }

    @Test
    public void 定数と全ての項の名前と次数が同じ単項式は等価である() {
        MonomialFunction mf = Variable.named('a').powerOf(2)
                .times(Variable.named('b').powerOf(3))
                .times(Variable.named('c'))
                .times(5);
        assertEquals("5a^2b^3c", mf.toString());

        MonomialFunction mf2 = Variable.named('a').times(Variable.named('b'));
        assertNotEquals(mf, mf2);  //ab
        assertNotEquals(mf, mf2.times(Variable.named('c')));  //abc
        assertNotEquals(mf, mf2.times(Variable.named('c')).times(Variable.named('a')));  //a^2bc
        assertNotEquals(mf, mf2.times(Variable.named('c')).times(Variable.named('a')).times(Variable.named('b').powerOf(2)));  //a^2b^3c
        assertEquals(mf, mf2.times(Variable.named('c')).times(Variable.named('a')).times(Variable.named('b').powerOf(2)).times(5));  //5a^2b^3c
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
