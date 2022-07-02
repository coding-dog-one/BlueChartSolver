package BlueChart.Chapter1.Section1;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 基本：掛ける順序や組み合わせを工夫して展開（1）
 */
@SuppressWarnings("NonAsciiCharacters")
public class Subsection8Test extends base {

    /**
     * 次の式を展開せよ。
     */
    @Nested
    class 例題8 {
        /**
         * (1) (x + y)(x^2 + y^2)(x - y)
         */
        @Test
        void 問1() {
            assertEquals(
                    "x^4 - y^4",
                    expand("(x + y)(x^2 + y^2)(x - y)")
            );
        }

        /**
         * (2) (p + 2q)^2(p - 2q)^2
         */
        @Test
        void 問2() {
            assertEquals(
                    "p^4 - 8p^2q^2 + 16q^4",
                    parse("(p + 2q)^2(p - 2q)^2").orderByDegreeOf('p').toString() //TODO: refs #13 pに着目するのをやめる
            );
        }

        /**
         * (3) (x + 1)(x - 2)(x^2 - x + 1)(x^2 + 2x + 4)
         */
        @Test
        void 問3() {
            assertEquals(
                    "x^6 - 7x^3 - 8",
                    expand("(x + 1)(x - 2)(x^2 - x + 1)(x^2 + 2x + 4)")
            );
        }
    }

    /**
     * 次の式を展開せよ。
     */
    @Nested
    class 練習8 {
        /**
         * (1) (x + 3)(x - 3)(x^2 + 9)
         */
        @Test
        void 問1() {
            assertEquals(
                    "x^4 - 81",
                    expand("(x + 3)(x - 3)(x^2 + 9)")
            );
        }

        /**
         * (2) (x - 1)(x - 2)(x + 1)(x + 2)
         */
        @Test
        void 問2() {
            assertEquals(
                    "x^4 - 5x^2 + 4",
                    expand("(x - 1)(x - 2)(x + 1)(x + 2)")
            );
        }

        /**
         * (3) (a + b)^3(a - b)^3
         */
        @Test
        void 問3() {
            assertEquals(
                    "a^6 - 3a^4b^2 + 3a^2b^4 - b^6",
                    parse("(a + b)^3(a - b)^3").orderByDegreeOf('a').toString() //TODO: refs #13 aに着目するのをやめる
            );
        }

        /**
         * (4) (x + 3)(x - 1)(x^2 + x + 1)(x^2 - 3x + 9)
         */
        @Test
        void 問4() {
            assertEquals(
                    "x^6 + 26x^3 - 27",
                    expand("(x + 3)(x - 1)(x^2 + x + 1)(x^2 - 3x + 9)")
            );
        }
    }
}
