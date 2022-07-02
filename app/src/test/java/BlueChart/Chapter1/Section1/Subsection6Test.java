package BlueChart.Chapter1.Section1;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 基本：公式による展開（3次式）
 */
@SuppressWarnings("NonAsciiCharacters")
public class Subsection6Test extends base {
    /**
     * 次の式を展開せよ。
     */

    @Nested
    class 例題6 {
        /**
         * (1) (x + 3)(x^2 - 3x + 9)
         */
        @Test
        void 問1() {
            assertEquals(
                    "x^3 + 27",
                    expand("(x + 3)(x^2 - 3x + 9)")
            );
        }

        /**
         * (2) (3a - 2b)(9a^2 + 6ab + 4b^2)
         */
        @Test
        void 問2() {
            assertEquals(
                    "27a^3 - 8b^3",
                    expand("(3a - 2b)(9a^2 + 6ab + 4b^2)")
            );
        }

        /**
         * (3) (a + 3)^3
         */
        @Test
        void 問3() {
            assertEquals(
                    "a^3 + 9a^2 + 27a + 27",
                    expand("(a + 3)^3")
            );
        }

        /**
         * (4) (2x - y)^3
         */
        @Test
        void 問4() {
            assertEquals(
                    "8x^3 - 12x^2y + 6xy^2 - y^3",
                    parse("(2x - y)^3").orderByDegreeOf('x').toString() //TODO: refs #13 xに着目するのをやめる
            );
        }
    }

    /**
     * 次の式を展開せよ。
     */
    @Nested
    class 練習6 {
        /**
         * (1) (x + 2)(x^2 - 2x + 4)
         */
        @Test
        void 問1() {
            assertEquals(
                    "x^3 + 8",
                    expand("(x + 2)(x^2 - 2x + 4)")
            );
        }

        /**
         * (2) (2p - q)(4p^2 + 2pq + q^2)
         */
        @Test
        void 問2() {
            assertEquals(
                    "8p^3 - q^3",
                    expand("(2p - q)(4p^2 + 2pq + q^2)")
            );
        }

        /**
         * (3) (2x + 1)^3
         */
        @Test
        void 問3() {
            assertEquals(
                    "8x^3 + 12x^2 + 6x + 1",
                    expand("(2x + 1)^3")
            );
        }

        /**
         * (4) (3x - 2y)^3
         */
        @Test
        void 問4() {
            assertEquals(
                    "27x^3 - 54x^2y + 36xy^2 - 8y^3",
                    parse("(3x - 2y)^3").orderByDegreeOf('x').toString() //TODO: refs #13 xに着目するのをやめる
            );
        }
    }
}
