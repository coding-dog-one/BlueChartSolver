package BlueChart.Chapter1.Section1;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 基本：（多項式）×（多項式）
 */
@SuppressWarnings("NonAsciiCharacters")
public class Subsection4Test extends base {

    /**
     * 次の式を展開せよ。
     */
    @Nested
    class 例題4 {
        /**
         * (1) (3x + 2)(4x^2 - 3x - 1)
         */
        @Test
        void 問1() {
            assertEquals(
                    "12x^3 - x^2 - 9x - 2",
                    expand("(3x + 2)(4x^2 - 3x - 1)")
            );
        }

        /**
         * (2) (3x^3 - 5x^2 + 1)(1 - x + 2x^2)
         */
        @Test
        void 問2() {
            assertEquals(
                    "6x^5 - 13x^4 + 8x^3 - 3x^2 - x + 1",
                    expand("(3x^3 - 5x^2 + 1)(1 - x + 2x^2)")
            );
        }
    }

    /**
     * 次の式を展開せよ。
     */
    @Nested
    class  練習4 {
        /**
         * (1) (2a + 3b)(a - 2b)
         */
        @Test
        void 問1() {
            assertEquals(
                    "2a^2 - ab - 6b^2",
                    expand("(2a + 3b)(a - 2b)")
            );
        }

        /**
         * (2) (2x - 3y - 1)(2x - y - 3)
         */
        @Test
        void 問2() {
            assertEquals(
                    "4x^2 - 8xy + 3y^2 - 8x + 10y + 3",
                    expand("(2x - 3y - 1)(2x - y - 3)")
            );
        }

        /**
         * (3) (2a - 3b)(a^2 + 4b^2 - 3ab)
         */
        @Test
        void 問3() {
            assertEquals(
                    "2a^3 - 9a^2b + 17ab^2 - 12b^3",
                    parse("(2a - 3b)(a^2 + 4b^2 - 3ab)").orderByDegreeOf('a').toString() //TODO: refs #13 aに着目するのをやめる
            );
        }

        /**
         * (4) (3x + x^3 - 1)(2x^2 - x - 6)
         */
        @Test
        void 問4() {
            assertEquals(
                    "2x^5 - x^4 - 5x^2 - 17x + 6",
                    expand("(3x + x^3 - 1)(2x^2 - x - 6)")
            );
        }
    }
}
