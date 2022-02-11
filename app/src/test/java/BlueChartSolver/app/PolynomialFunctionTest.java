package BlueChartSolver.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PolynomialFunctionTest {
    @Test
    public void 多項式は次数の高い順に並ぶ() {
        Variable x = Variable.named('x');
        assertEquals("-2x^5 + 17x^3 - 9x", x.powerOf(5).times(-2)
                .plus(x.powerOf(3).times(17))
                .plus(x.times(-9))
                .toString()
        );
        assertEquals("5a - 12", Variable.named('a').times(5).plus(-12).toString());
    }

    @Test
    public void 交換法則() {
        Variable A = Variable.named('A');
        Variable B = Variable.named('B');
        assertEquals("A + B", A.plus(B).toString());
        assertEquals(A.plus(B), B.plus(A));
    }

    @Test
    public void 結合法則() {
        Variable A = Variable.named('A');
        Variable B = Variable.named('B');
        Variable C = Variable.named('C');
        assertEquals("A + B + C", (A.plus(B)).plus(C).toString());
        assertEquals((A.plus(B)).plus(C), A.plus(B.plus(C)));
    }

    @Test
    public void 分配法則() {
        Variable A = Variable.named('A');
        Variable B = Variable.named('B');
        Variable C = Variable.named('C');
        assertEquals("AB + AC", A.times(B.plus(C)).toString());
        assertEquals(A.times(B.plus(C)), A.times(B).plus(A.times(C)));

        assertEquals("AC + BC", A.plus(B).times(C).toString());
        assertEquals(A.plus(B).times(C), A.times(C).plus(B.times(C)));
    }

    @Test
    public void 二次式の展開() {
        Variable x = Variable.named('x');
        Variable a = Variable.named('a');
        Variable b = Variable.named('b');
        //TODO
//        assertEquals("", a.plus(b).powerOf(2).toString());
        assertEquals(
                x.powerOf(2).plus((a.plus(b)).times(x)).plus(a.times(b)),
                x.plus(a).times(x.plus(b))
        );
    }

    @Test
    public void 同類項はまとめられる() {
        Variable x = Variable.named('x');
        PolynomialFunction A = x.powerOf(3).times(5)
                .minus(x.powerOf(2).times(2))
                .plus(x.times(3))
                .plus(4);
        PolynomialFunction B = x.powerOf(3).times(3)
                .minus(x.powerOf(2).times(5))
                .plus(3);
        PolynomialFunction expected = x.powerOf(3).times(2)
                .plus(x.powerOf(2).times(3))
                .plus(x.times(3))
                .plus(1);
        assertEquals(expected, A.minus(B));
    }
}
