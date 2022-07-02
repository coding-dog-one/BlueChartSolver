package BlueChart.Chapter1.Section1;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 重要：掛ける順序や組み合わせを工夫して展開（2）
 */
@SuppressWarnings("NonAsciiCharacters")
public class Subsection9Test extends base {

    /**
     * 次の式を展開せよ。
     */
    @Nested
    class 例題9 {
        /**
         * (1) (x - 1)(x - 2)(x - 3)(x - 4)
         */
        @Test
        void 問1() {
            assertEquals(
                    "x^4 - 10x^3 + 35x^2 - 50x + 24",
                    expand("(x - 1)(x - 2)(x - 3)(x - 4)")
            );
        }

        /**
         * (2) (a + b + c)^2 + (b + c - a)^2 + (c + a - b)^2 + (a + b - c)^2
         */
        @Test
        void 問2() {
            assertEquals(
                    "4a^2 + 4b^2 + 4c^2",
                    expand("(a + b + c)^2 + (b + c - a)^2 + (c + a - b)^2 + (a + b - c)^2")
            );
        }

        /**
         * (a + b + c)(a^2 + b^2 + c^2 - ab - bc - ca)
         */
        @Test
        void 問3() {
            assertEquals(
                    "a^3 + b^3 + c^3 - 3abc",
                    parse("(a + b + c)(a^2 + b^2 + c^2 - ab - bc - ca)").orderByDegreeOf('a', 'b', 'c').toString()
            );
        }
    }

    /**
     * 次の式を展開せよ。<br>
     * なお、(4)は(a + b + c)(a^2 + b^2 + c^2 - ab - bc - ca) = a^3 + b^3 + c^3 - 3abcであることを利用してもよい。
     */
    @Nested
    class 練習9 {
        /**
         * (1) (x - 2)(x + 1)(x + 2)(x + 5)
         */
        @Test
        void 問1() {
            assertEquals(
                    "x^4 + 6x^3 + x^2 - 24x - 20",
                    expand("(x - 2)(x + 1)(x + 2)(x + 5)")
            );
        }

        /**
         * (2) (x + 8)(x + 7)(x - 3)(x - 4)
         */
        @Test
        void 問2() {
            assertEquals(
                    "x^4 + 8x^3 - 37x^2 - 212x + 672",
                    expand("(x + 8)(x + 7)(x - 3)(x - 4)")
            );
        }

        /**
         * (3) (x + y + z)(-x + y + z)(x - y + z)(x + y - z)
         */
        @Test
        void 問3() {
            assertEquals(
                    "-x^4 - y^4 - z^4 + 2x^2y^2 + 2x^2z^2 + 2y^2z^2", //青本では、x^2z^2ではなくz^2x^2（-x^4 - y^4 - z^4 + 2x^2y^2 + 2y^2z^2 + 2z^2x^2）
                    parse("(x + y + z)(-x + y + z)(x - y + z)(x + y - z)").orderByDegreeOf('x', 'y', 'z').toString() //TODO: refs #13 x, y, zに着目するのをやめる
            );
        }

        /**
         * (4) (x + y + 1)(x^2 + y^2 - xy - x - y + 1)
         */
        @Test
        void 問4() {
            assertEquals(
                    "x^3 + y^3 - 3xy + 1",
                    expand("(x + y + 1)(x^2 + y^2 - xy - x - y + 1)")
            );
        }
    }
}
