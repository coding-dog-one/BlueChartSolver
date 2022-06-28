package BlueChart.Chapter1.Section1;

import BlueChartSolver.helpers.SimpleParser;
import BlueChartSolver.models.Polynomial;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings({"NonAsciiCharacters"})
public class Subsection2Test {
    private static final SimpleParser parser = new SimpleParser();

    /**
     * <h1>整式の加法・減法</h1>
     * A = x^2 + 3y^2 - 2xy、<br>
     * B = y^2 + 3xy - 2x^2、<br>
     * C = -3x^2 + xy - 4y^2であるとき、次の計算をせよ。
     */
    @Nested
    class 例題2 {
        private final Polynomial A = parser.parse("x^2 + 3y^2 - 2xy");
        private final Polynomial B = parser.parse("y^2 + 3xy - 2x^2");
        private final Polynomial C = parser.parse("-3x^2 + xy - 4y^2");

        /**
         * (1) A + B
         */
        @Test
        void 問1() {
            var organized = A.plus(B);
            assertEquals("-x^2 + xy + 4y^2", organized.orderByDegreeOf('x').toString());
        }

        /**
         * (2) A - B
         */
        @Test
        void 問2() {
            var organized = A.minus(B);
            assertEquals("3x^2 - 5xy + 2y^2", organized.orderByDegreeOf('x').toString());
        }

        /**
         * (3) -3A + 2B - C
         */
        @Test
        void 問3() {
            var organized = A.times(-3).plus(B.times(2)).minus(C);
            assertEquals("-4x^2 + 11xy - 3y^2", organized.orderByDegreeOf('x').toString());
        }

        /**
         * (4) 3(2A + C) - 2{2(A + C) - (B - C)}
         */
        @Test
        void 問4() {
            var organized = (A.times(2).plus(C)).times(3)
                    .minus((A.plus(C).times(2).minus(B.minus(C))).times(2));
            assertEquals("7x^2 - xy + 20y^2", organized.orderByDegreeOf('x').toString());
        }
    }

    /**
     * A = -2x^3 + 4x^2y + 5y^3、<br>
     * B = x^2y - 3xy^2 + 2y^3、<br>
     * C = 3x^3 - 2x^2yであるとき、次の計算をせよ。
     */
    @Nested
    class 練習2 {
        private final Polynomial A = parser.parse("-2x^3 + 4x^2y + 5y^3");
        private final Polynomial B = parser.parse("x^2y - 3xy^2 + 2y^3");
        private final Polynomial C = parser.parse("3x^3 - 2x^2y");

        /**
         * (1) 3(A - 2B) - 2(A - 2B - C)
         */
        @Test
        void 問1() {
            var organized = (A.minus(B.times(2))).times(3).minus((A.minus(B.times(2)).minus(C)).times(2));
            assertEquals("4x^3 - 2x^2y + 6xy^2 + y^3", organized.orderByDegreeOf('x').toString());
        }

        /**
         * (2) 3A - 2{(2A - B) - (A - 3B)} - 3C
         */
        @Test
        void 問2() {
            var organized = A.times(3)
                    .minus((A.times(2).minus(B).minus(A.minus(B.times(3)))).times(2))
                    .minus(C.times(3));
            assertEquals("-11x^3 + 6x^2y + 12xy^2 - 3y^3", organized.orderByDegreeOf('x').toString());
        }
    }
}
