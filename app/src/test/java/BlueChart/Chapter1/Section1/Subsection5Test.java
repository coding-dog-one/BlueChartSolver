package BlueChart.Chapter1.Section1;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 基本：公式による展開（2次式）
 */
@SuppressWarnings("NonAsciiCharacters")
public class Subsection5Test extends base {

    /**
     * 次の式を展開せよ。
     */
    @Nested
    class 例題5 {
        /**
         * (1) (a + 2)^2
         */
        @Test
        void 問1() {
            assertEquals(
                    "a^2 + 4a + 4",
                    expand("(a + 2)^2")
            );
        }

        /**
         * (2) (3x - 4y)^2
         */
        @Test
        void 問2() {
            assertEquals(
                    "9x^2 - 24xy + 16y^2",
                    expand("(3x - 4y)^2")
            );
        }

        /**
         * (3) (2a + b)(2a - b)
         */
        @Test
        void 問3() {
            assertEquals(
                    "4a^2 - b^2",
                    expand("(2a + b)(2a - b)")
            );
        }

        /**
         * (4) (x + 3)(x - 5)
         */
        @Test
        void 問4() {
            assertEquals(
                    "x^2 - 2x - 15",
                    expand("(x + 3)(x - 5)")
            );
        }

        /**
         * (5) (2x + 3)(3x + 4)
         */
        @Test
        void 問5() {
            assertEquals(
                    "6x^2 + 17x + 12",
                    expand("(2x + 3)(3x + 4)")
            );
        }

        /**
         * (6) (4x + y)(7y - 3x)
         */
        @Test
        void 問6() {
            assertEquals(
                    "-12x^2 + 25xy + 7y^2",
                    expand("(4x + y)(7y - 3x)")
            );
        }
    }

    /**
     * 次の式を展開せよ。
     */
    @Nested
    class 練習5 {
        /**
         * (1) (3x + 5y)^2
         */
        @Test
        void 問1() {
            assertEquals(
                    "9x^2 + 30xy + 25y^2",
                    expand("(3x + 5y)^2")
            );
        }

        /**
         * (2) (a^2 + 2b)^2
         */
        @Test
        void 問2() {
            assertEquals(
                    "a^4 + 4a^2b + 4b^2",
                    expand("(a^2 + 2b)^2")
            );
        }

        /**
         * (3) (3a - 2b)^2
         */
        @Test
        void 問3() {
            assertEquals(
                    "9a^2 - 12ab + 4b^2",
                    expand("(3a - 2b)^2")
            );
        }

        /**
         * (4) (2xy - 3)^2
         */
        @Test
        void 問4() {
            assertEquals(
                    "4x^2y^2 - 12xy + 9",
                    expand("(2xy - 3)^2")
            );
        }

        /**
         * (5) (2x - 3y)(2x + 3y)
         */
        @Test
        void 問5() {
            assertEquals(
                    "4x^2 - 9y^2",
                    expand("(2x - 3y)(2x + 3y)")
            );
        }

        /**
         * (6) (3x - 4y)(5y + 4x)
         */
        @Test
        void 問6() {
            assertEquals(
                    "12x^2 - xy - 20y^2",
                    expand("(3x - 4y)(5y + 4x)")
            );
        }
    }
}
