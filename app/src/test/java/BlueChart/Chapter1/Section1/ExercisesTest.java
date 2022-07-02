package BlueChart.Chapter1.Section1;

import blue_chart_solver.models.Term;
import blue_chart_solver.models.Variable;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings({"NonAsciiCharacters", "OptionalGetWithoutIsPresent"})
class ExercisesTest extends base {

    /**
     * P = -2x^2 + 2x - 5、<br>
     * Q = 3x^2 - x、<br>
     * R = -x^2 - x + 5のとき、次の式を計算せよ。<br>
     * 3P - (2(Q - (2R - P)) - 3(Q - R))
     */
    @Test
    void 大問1() {
        var P = parse("-2x^2 + 2x - 5");
        var Q = parse("3x^2 - x");
        var R = parse("-x^2 - x + 5");
        var result = P.times(3).minus(
                (Q.minus(R.times(2).minus(P)))
                        .times(2).minus(
                                Q.minus(R).times(3)
                        )
        );
        assertEquals("0", result.toString());
    }

    @Nested
    class 大問2 {
        /**
         * 3x^2 - 2x + 1との和がx^2 - xになる式を求めよ。
         */
        @Test
        void 問1() {
            assertEquals(
                    "-2x^2 + x - 1",
                    expand("(x^2 - x) - (3x^2 - 2x + 1)")
            );
        }

        /**
         * ある多項式にa^3 + 2a^2b - 5ab^2 + 5b^3を加えるところを誤って引いたので、答えが-a^3 - 4a^2b + 10ab^2 - 9b^3になった。正しい答えを求めよ。
         */
        @Test
        void 問2() {
            assertEquals(
                    "a^3 + b^3",
                    expand("(-a^3 - 4a^2b + 10ab^2 - 9b^3) + (a^3 + 2a^2b - 5ab^2 + 5b^3) + (a^3 + 2a^2b - 5ab^2 + 5b^3)")
            );
        }
    }

    /**
     * 次の計算をせよ。
     */
    @Nested
    class 大問3 {
        /**
         * (1) 5xy^2(-2x^2y)^3
         */
        @Test
        void 問1() {
            assertEquals("-40x^7y^5", expand("5xy^2(-2x^2y)^3"));
        }

        /**
         * (2) 2a^2b(-3ab)^2(-a^2b^2)^3
         */
        @Test
        void 問2() {
            assertEquals("-18a^10b^9", expand("2a^2b(-3ab)^2(-a^2b^2)^3"));
        }

        /**
         * (3) (-2a^2b)^3(3a^3b^2)^2
         */
        @Test
        void 問3() {
            assertEquals("-72a^12b^7", expand("(-2a^2b)^3(3a^3b^2)^2"));
        }

        /**
         * (4) (-2ax^3y)^2(-3ab^2xy^3)
         */
        @Test
        void 問4() {
            assertEquals("-12a^3b^2x^7y^5", expand("(-2ax^3y)^2(-3ab^2xy^3)"));
        }
    }

    /**
     * 次の式を展開せよ。
     */
    @Nested
    class 大問4 {
        /**
         * (1) (a - b + c)(a - b - c)
         */
        @Test
        void 問1() {
            assertEquals(
                    "a^2 - 2ab + b^2 - c^2",
                    expand("(a - b + c)(a - b - c)")
            );
        }

        /**
         * (2) (2x^2 - x + 1)(x^2 + 3x - 3)
         */
        @Test
        void 問2() {
            assertEquals(
                    "2x^4 + 5x^3 - 8x^2 + 6x - 3",
                    expand("(2x^2 - x + 1)(x^2 + 3x - 3)")
            );
        }

        /**
         * (3) (2a - 5b)^3
         */
        @Test
        void 問3() {
            assertEquals(
                    "8a^3 - 60a^2b + 150ab^2 - 125b^3",
                    parse("(2a - 5b)^3").orderByDegreeOf('a').toString() //TODO: refs #13 aに着目するのをやめる
            );
        }

        /**
         * (4) (x^3 + x - 3)(x^2 - 2x + 2)
         */
        @Test
        void 問4() {
            assertEquals(
                    "x^5 - 2x^4 + 3x^3 - 5x^2 + 8x - 6",
                    expand("(x^3 + x - 3)(x^2 - 2x + 2)")
            );
        }

        /**
         * (5) (x^2 - 2xy + 4y^2)(x^2 + 2xy + 4y^2)
         */
        @Test
        void 問5() {
            assertEquals(
                    "x^4 + 4x^2y^2 + 16y^4",
                    parse("(x^2 - 2xy + 4y^2)(x^2 + 2xy + 4y^2)").orderByDegreeOf('x').toString() //TODO:: refs #13 xに着目するのをやめる
            );
        }

        /**
         * (6) (x + y)(x - y)(x^2 + y^2)(x^4 + y^4)
         */
        @Test
        void 問6() {
            assertEquals(
                    "x^8 - y^8",
                    expand("(x + y)(x - y)(x^2 + y^2)(x^4 + y^4)")
            );
        }

        /**
         * (7) (1 + a)(1 - a^3 + a^6)(1 - a + a^2)
         */
        @Test
        void 問7() {
            assertEquals(
                    "a^9 + 1", //青本では、1 + a^9
                    expand("(1 + a)(1 - a^3 + a^6)(1 - a + a^2)")
            );
        }
    }

    @Nested
    class 大問5 {
        /**
         * (x^3 + 3x^2 + 2x + 7)(x^3 + 2x^2 - x + 1)を展開すると。x^5の係数は[ア]、x^3の係数は[イ]となる。
         */
        @Test
        void 問1() {
            var expanded = parse("(x^3 + 3x^2 + 2x + 7)(x^3 + 2x^2 - x + 1)");
            var ア = expanded.findTerm('x', 5).get().coefficient();
            var イ = expanded.findTerm('x', 3).get().coefficient();
            assertEquals(5, ア);
            assertEquals(9, イ);
        }

        /**
         * 式(2x + 3y + z)(x + 2y + 3z)(3x + y + 2z)を展開したときのxyzの係数は[ ]である。
         */
        @Test
        void 問2() {
            var xyz = Term.from(Variable.named('x'))
                    .times(Term.from(Variable.named('y')))
                    .times(Term.from(Variable.named('z')));
            assertEquals(54, parse("(2x + 3y + z)(x + 2y + 3z)(3x + y + 2z)").findTerm(xyz).get().coefficient());
        }
    }

    /**
     * 次の式を計算せよ。
     */
    @Nested
    class 大問6 {
        /**
         * (1) (x - b)(x - c)(b - c) + (x - c)(x - a)(c - a) + (x - a)(x - b)(a - b)
         */
        @Test
        void 問1() {
            assertEquals(
                    "a^2b - a^2c - ab^2 + ac^2 + b^2c - bc^2", //青本では、acでなくcaの順(a^2b - ab^2 + b^2c - bc^2 + c^2a - ca^2）
                    expand("(x - b)(x - c)(b - c) + (x - c)(x - a)(c - a) + (x - a)(x - b)(a - b)"));
        }

        /**
         * (2) (x + y + 2z)^3 - (y + 2z - x)^3 - (2z + x - y)^3 - (x + y - 2z)^3
         */
        @Test
        void 問2() {
            assertEquals(
                    "48xyz",
                    expand("(x + y + 2z)^3 - (y + 2z - x)^3 - (2z + x - y)^3 - (x + y - 2z)^3")
            );
        }
    }
}
