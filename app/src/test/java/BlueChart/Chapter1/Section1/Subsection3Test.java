package BlueChart.Chapter1.Section1;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 基本：（単項式）×（単項式）、（単項式）×（多項式）
 */
@SuppressWarnings("NonAsciiCharacters")
public class Subsection3Test extends base {

    /**
     * 次の計算をせよ。
     */
    @Nested
    class 例題3 {
        /**
         * (1) (-xy^2)^2(-3x^2y)
         */
        @Test
        void 問1() {
            assertEquals(
                    "-3x^4y^5",
                    expand("(-xy^2)^2(-3x^2y)")
            );
        }

        /**
         * (2) -a^2b(-3a^2bc^2)^3
         */
        @Test
        void 問2() {
            assertEquals(
                    "27a^8b^4c^6",
                    expand("-a^2b(-3a^2bc^2)^3")
            );
        }

        /**
         * (3) 3abc(a + 4b - 2c)
         */
        @Test
        void 問3() {
            assertEquals(
                    "3a^2bc + 12ab^2c - 6abc^2",
                    expand("3abc(a + 4b - 2c)")
            );
        }

        /**
         * (4) (-xy)^2(3x^2 - 2y - 4)
         */
        @Test
        void 問4() {
            assertEquals(
                    "3x^4y^2 - 2x^2y^3 - 4x^2y^2",
                    expand("(-xy)^2(3x^2 - 2y - 4)")
            );
        }
    }

    /**
     * 次の計算をせよ。
     */
    @Nested
    class 練習3 {
        /**
         * (1) (-ab)^2(-2a^3b)
         */
        @Test
        void 問1() {
            assertEquals(
                    "-2a^5b^3",
                    expand("(-ab)^2(-2a^3b)")
            );
        }

        /**
         * (2) (-2x^4y^2^z3)(-3x^2y^2z^4)
         */
        @Test
        void 問2() {
            assertEquals(
                    "6x^6y^4z^7",
                    expand("(-2x^4y^2z^3)(-3x^2y^2z^4)")
            );
        }

        /**
         * (3) 2a^2bc(a - 3b^2 + 2c)
         */
        @Test
        void 問3() {
            assertEquals(
                    "-6a^2b^3c + 2a^3bc + 4a^2bc^2",
                    parse("2a^2bc(a - 3b^2 + 2c)").orderByDegreeOf('b').toString()  //TODO: refs #13 bに着目するのをやめる
            );
        }

        /**
         * (4) (-2x)^3(3x^2 - 2x + 4)
         */
        @Test
        void 問4() {
            assertEquals(
                    "-24x^5 + 16x^4 - 32x^3",
                    expand("(-2x)^3(3x^2 - 2x + 4)")
            );
        }
    }
}
