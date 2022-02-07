package BlueChartSolver.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MonomialFunctionTest {
    @Test
    public void 単項式の生成() {
        MonomialFunction mf = new MonomialFunction(Variable.named("x").powerOf(2).times(6));
        assertEquals("6x^2", mf.toString());

        mf.times(Variable.named("b"));
        assertEquals("6bx^2", mf.toString());  //単項式は不変ではない

        mf.times(Variable.named("a")).times(2);
        assertEquals("12abx^2", mf.toString());  //変数はアルファベットの昇順にならぶ
    }

    @Test
    public void 変数に変数をかけると単項式となる() {
        MonomialFunction mf = Variable.named("a")
                .times(Variable.named("b"))
                .times(5);
        assertEquals("5ab", mf.toString());
    }

    @Test
    public void 定数と全ての項の名前と次数が同じ単項式のみ等価とみなす() {
        MonomialFunction mf = Variable.named("a").times(2).powerOf(2)
                .times(Variable.named("b").powerOf(3))
                .times(Variable.named("c"))
                .times(5);
        assertEquals("10a^2b^3c", mf.toString());

        MonomialFunction mf2 = Variable.named("a").times(Variable.named("b"));
        assertNotEquals(mf, mf2);  //ab
        assertNotEquals(mf, mf2.times(Variable.named("c")));  //abc
        assertNotEquals(mf, mf2.times(Variable.named("a")));  //a^2bc
        assertNotEquals(mf, mf2.times(Variable.named("b").powerOf(2)));  //a^2b^3c
        assertEquals(mf, mf2.times(10));  //10a^2b^3c
    }
}
