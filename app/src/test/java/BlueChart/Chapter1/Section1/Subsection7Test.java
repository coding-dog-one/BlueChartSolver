package BlueChart.Chapter1.Section1;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 基本：おき換えを利用した展開
 */
@SuppressWarnings("NonAsciiCharacters")
public class Subsection7Test extends base {

    /**
     * 次の式を展開せよ。
     */
    @Nested
    class 例題7 {
        /**
         * (1) (a + b + c)^2
         */
        @Test
        void 問1() {
            assertEquals(
                    "a^2 + b^2 + c^2 + 2ab + 2ac + 2bc", //青本では、acではなくca（a^2 + b^2 + c^2 + 2ab + 2bc + 2ca）
                    parse("(a + b + c)^2").orderByDegreeOf('a', 'b', 'c').toString() //TODO: refs #13 a,b,cに着目するのをやめる
            );
        }

        /**
         * (2) (x + y + z)(x - y - z)
         */
        @Test
        void 問2() {
            assertEquals(
                    "x^2 - y^2 - z^2 - 2yz",
                    parse("(x + y + z)(x - y - z)").orderByDegreeOf('x').toString() //TODO: refs #13 xに着目するのをやめる
            );
        }

        /**
         * (3) (x^2 + 3x - 2)(x^2 + 3x + 3)
         */
        @Test
        void 問3() {
            assertEquals(
                    "x^4 + 6x^3 + 10x^2 + 3x - 6",
                    expand("(x^2 + 3x - 2)(x^2 + 3x + 3)")
            );
        }
    }

    /**
     * 次の式を展開せよ。
     */
    @Nested
    class 練習7 {
        /**
         * (1) (a + 3b - c)^2
         */
        @Test
        void 問1() {
            assertEquals(
                    "a^2 + 9b^2 + c^2 + 6ab - 2ac - 6bc", //青本では、acでなくca（a^2 + 9b^2 + c^2 + 6ab - 6bc - 2ca）
                    parse("(a + 3b - c)^2").orderByDegreeOf('a', 'b', 'c').toString()
            );
        }

        /**
         * (2) (x + y + 7)(x + y - 7)
         */
        @Test
        void 問2() {
            assertEquals(
                    "x^2 + 2xy + y^2 - 49",
                    expand("(x + y + 7)(x + y - 7)")
            );
        }

        /**
         * (3) (x - 3y + 2z)(x + 3y - 2z)
         */
        @Test
        void 問3() {
            assertEquals(
                    "x^2 - 9y^2 - 4z^2 + 12yz",
                    parse("(x - 3y + 2z)(x + 3y - 2z)").orderByDegreeOf('x').toString() //TODO: refs #13 xに着目するのをやめる
            );
        }

        /**
         * (4) (x^2 - 3x + 1)(x^2 + 4x + 1)
         */
        @Test
        void 問4() {
            assertEquals(
                    "x^4 + x^3 - 10x^2 + x + 1",
                    expand("(x^2 - 3x + 1)(x^2 + 4x + 1)")
            );
        }
    }
}
