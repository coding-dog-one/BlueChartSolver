package BlueChart.Chapter1.Section1;

import BlueChartSolver.models.Polynomial;
import BlueChartSolver.models.Variable;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings({"NonAsciiCharacters"})
public class Subsection2Test {
    @Nested
    class 例題2 {
        private final Variable x = Variable.named('x');
        private final Variable y = Variable.named('y');
        private final Polynomial A = x.powerOf(2)
                .plus(y.powerOf(2).times(3))
                .minus(x.times(y).times(2));
        private final Polynomial B = y.powerOf(2)
                .plus(x.times(y).times(3))
                .minus(x.powerOf(2).times(2));
        private final Polynomial C = x.powerOf(2).times(-3)
                .plus(x.times(y))
                .minus(y.powerOf(2).times(4));

        @Test
        public void test1() {
            assertEquals("-x^2 + xy + 4y^2", A.plus(B).orderByDegreeOf(x).toString());
        }

        @Test
        public void test2() {
            assertEquals("3x^2 - 5xy + 2y^2", A.minus(B).orderByDegreeOf(x).toString());
        }

        @Test
        public void test3() {
            var p = A.times(-3).plus(B.times(2)).minus(C);
            assertEquals("-4x^2 + 11xy - 3y^2", p.orderByDegreeOf(x).toString());
        }

        @Test
        public void test4() {
            var p = (A.times(2).plus(C)).times(3)
                    .minus((A.plus(C).times(2).minus(B.minus(C))).times(2));
            assertEquals("7x^2 - xy + 20y^2", p.orderByDegreeOf(x).toString());
        }
    }

    @Nested
    class 練習2 {
        private final Variable x = Variable.named('x');
        private final Variable y = Variable.named('y');
        private final Polynomial A = x.powerOf(3).times(-2)
                .plus(x.powerOf(2).times(y).times(4))
                .plus(y.powerOf(3).times(5));
        private final Polynomial B = x.powerOf(2).times(y)
                .minus(x.times(y.powerOf(2)).times(3))
                .plus(y.powerOf(3).times(2));
        private final Polynomial C = x.powerOf(3).times(3)
                .minus(x.powerOf(2).times(y).times(2));

        @Test
        public void test1() {
            var p = (A.minus(B.times(2))).times(3).minus((A.minus(B.times(2)).minus(C)).times(2));
            assertEquals("4x^3 - 2x^2y + 6xy^2 + y^3", p.orderByDegreeOf(x).toString());
        }

        @Test
        public void test2() {
            var p = A.times(3)
                    .minus((A.times(2).minus(B).minus(A.minus(B.times(3)))).times(2))
                    .minus(C.times(3));
            assertEquals("-11x^3 + 6x^2y + 12xy^2 - 3y^3", p.orderByDegreeOf(x).toString());
        }
    }
}
