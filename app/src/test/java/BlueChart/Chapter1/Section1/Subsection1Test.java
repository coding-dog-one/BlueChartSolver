package BlueChart.Chapter1.Section1;

import blue_chart_solver.models.Variable;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 基本：同類項の整理と次数・定数項
 */
@SuppressWarnings({"NonAsciiCharacters", "OptionalGetWithoutIsPresent"})
public class Subsection1Test extends base {

    /**
     * 次の数式の同類項をまとめて整理せよ。<br>
     * また、(2)、(3)の整式において、[ ]内の文字に着目したとき、その次数と定数項をいえ。
     */
    @Nested
    class 例題1 {
        /**
         * (1) 3x^2 + 2x - 6 - 4x^2 + 3x + 2
         */
        @Test
        void 問1() {
            var organized = parse("3x^2 + 2x - 6 - 4x^2 + 3x + 2");
            assertEquals(
                    "-x^2 + 5x - 4",
                    organized.toString()
            );
        }

        /**
         * (2) 2a^2 - ab - b^2 + 4ab + 3a^2 + 2b^2 [b]
         */
        @Test
        void 問2() {
            var organized = parse("2a^2 - ab - b^2 + 4ab + 3a^2 + 2b^2");
            assertEquals(
                    "5a^2 + 3ab + b^2",
                    organized.orderByDegreeOf('a').toString()
            );

            // bに着目したときの次数と定数項
            assertEquals(2, organized.orderByDegreeOf('b').degree());
            assertEquals("5a^2", organized.orderByDegreeOf('b').constant().get().toString());
        }

        /**
         * (3) x^3 - 2ax^2y + 4xy - 3by + y^2 + 2xy - 2by + 4a [xとy]、[y]
         */
        @Test
        void 問3() {
            var organized = parse("x^3 - 2ax^2y + 4xy - 3by + y^2 + 2xy - 2by + 4a");
            assertEquals(
                    "x^3 - 2ax^2y + 6xy + y^2 - 5by + 4a",
                    organized.orderByDegreeOf('x').toString()
            );

            // xとyに着目したときの次数と定数項
            assertEquals(3, organized.orderByDegreeOf('x', 'y').degree());
            assertEquals("4a", organized.orderByDegreeOf('x', 'y').constant().get().toString());

            // yに着目したときの次数と定数項
            assertEquals(2, organized.orderByDegreeOf('y').degree());
            assertEquals("x^3 + 4a", organized.orderByDegreeOf('y').constant().get().toString());
        }
    }

    @Nested
    class 練習1 {
        /**
         * (1) 整式 -2x + 3y + x^2 + 5x - yの同類項をまとめよ。
         */
        @Test
        void 問1() {
            var organized = parse("-2x + 3y + x^2 + 5x - y");
            assertEquals(
                    "x^2 + 3x + 2y",
                    organized.orderByDegreeOf(Variable.named('x')).toString()
            );
        }

        /**
         * (2) 次の整式において、[ ]内の文字に着目したとき、その次数と定数項をいえ。<br>
         *   (ア) x - 2xy + 3y^2 + 4 - 2x - 7xy + 2y^2 - 1 [y]
         */
        @Test
        void 問2ア() {
            var organized = parse("x - 2xy + 3y^2 + 4 - 2x - 7xy + 2y^2 - 1");

            // yに着目したときの次数と定数項
            assertEquals(2, organized.orderByDegreeOf('y').degree());
            assertEquals("-x + 3", organized.orderByDegreeOf('y').constant().get().toString());
        }

        /**
         * (2) 次の整式において、[ ]内の文字に着目したとき、その次数と定数項をいえ。<br>
         *   (イ) a^2b^2 - ab + 3ab - 2a^2b^2 + 7c^2 + 4a - 5b - 3a + 1 [b]、[aとb]
         */
        @Test
        void 問2イ() {
            var organized = parse("a^2b^2 - ab + 3ab - 2a^2b^2 + 7c^2 + 4a - 5b - 3a + 1");

            // bに着目したときの次数と定数項
            assertEquals(2, organized.orderByDegreeOf('b').degree());
            assertEquals(
                    "7c^2 + a + 1",
                    organized.orderByDegreeOf('b').constant().get().toString()
            );

            // aとbに着目したときの次数と定数項
            assertEquals(4, organized.orderByDegreeOf('a', 'b').degree());
            assertEquals(
                    "7c^2 + 1",
                    organized.orderByDegreeOf('a', 'b').constant().get().toString()
            );
        }
    }
}
