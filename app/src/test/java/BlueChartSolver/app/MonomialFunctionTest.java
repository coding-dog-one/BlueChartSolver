package BlueChartSolver.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        MonomialFunction mf = Variable.named('x').powerOf(2).times(4);
        assertEquals("4x^2", mf.toString());
    }

    @Test
    public void 変数に変数を掛けたものは単項式であり変数は辞書順に並ぶ() {
        MonomialFunction mf = Variable.named('c').times(Variable.named('a')).times(Variable.named('b'));
        assertEquals("abc", mf.toString());
    }

    @Test
    public void 定数と全ての項の名前と次数が同じ単項式を等価とみなす() {
        //TODO 単項式同士の掛け算を実装する
        /*MonomialFunction mf = Variable.named("a")
                .times(Variable.named("b").powerOf(3))
                .times(Variable.named("c"))
                .times(5);
        assertEquals("10a^2b^3c", mf.toString());

        MonomialFunction mf2 = Variable.named("a").times(Variable.named("b"));
        assertNotEquals(mf, mf2);  //ab
        assertNotEquals(mf, mf2.times(Variable.named("c")));  //abc
        assertNotEquals(mf, mf2.times(Variable.named("a")));  //a^2bc
        assertNotEquals(mf, mf2.times(Variable.named("b").powerOf(2)));  //a^2b^3c
        assertEquals(mf, mf2.times(10));  //10a^2b^3c*/
    }
}
