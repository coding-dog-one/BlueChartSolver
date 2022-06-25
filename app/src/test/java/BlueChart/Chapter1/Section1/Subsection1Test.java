package BlueChart.Chapter1.Section1;

import BlueChartSolver.models.Variable;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings({"NonAsciiCharacters", "OptionalGetWithoutIsPresent"})
public class Subsection1Test {
    /**
     * <h1>同類項の整理と次数・定数項</h1>
     * 次の数式の同類項をまとめて整理せよ。<br>
     * また、(2)、(3)の整式において、[ ]内の文字に着目したとき、その次数と定数項をいえ。
     */
    @Nested
    class 例題1 {
        /**
         * (1) 3x^2 + 2x - 6 - 4x^2 + 3x + 2
         */
        @Test
        public void test1() {
            var x = Variable.named('x');
            assertEquals("-x^2 + 5x - 4",
                    x.powerOf(2).times(3)
                            .plus(x.times(2))
                            .minus(6)
                            .minus(x.powerOf(2).times(4))
                            .plus(x.times(3))
                            .plus(2)
                            .orderByDegreeOf(x)
                            .toString()
            );
        }

        /**
         * (2) 2a^2 - ab - b^2 + 4ab + 3a^2 + 2b^2 [b]
         */
        @Test
        public void test2() {
            var a = Variable.named('a');
            var b = Variable.named('b');
            var p = a.powerOf(2).times(2)
                    .minus(a.times(b))
                    .minus(b.powerOf(2))
                    .plus(a.times(b).times(4))
                    .plus(a.powerOf(2).times(3))
                    .plus(b.powerOf(2).times(2));

            assertEquals("5a^2 + 3ab + b^2", p.orderByDegreeOf(a).toString());

            // bに着目したときの次数と定数項
            assertEquals(2, p.orderByDegreeOf(b).degree());
            assertEquals("5a^2", p.orderByDegreeOf(b).constant().get().toString());
        }

        /**
         * (3) x^3 - 2ax^2y + 4xy - 3by + y^2 + 2xy - 2by + 4a [xとy]、[y]
         */
        @Test
        public void test3() {
            var a = Variable.named('a');
            var b = Variable.named('b');
            var x = Variable.named('x');
            var y = Variable.named('y');
            var p = x.powerOf(3)
                    .minus(x.powerOf(2).times(a).times(y).times(2))
                    .plus(x.times(y).times(4))
                    .minus(y.times(b).times(3))
                    .plus(y.powerOf(2))
                    .plus(x.times(y).times(2))
                    .minus(y.times(b).times(2))
                    .plus(a.times(4));

            assertEquals("x^3 - 2ax^2y + 6xy + y^2 - 5by + 4a", p.orderByDegreeOf(x).toString());

            // xとyに着目したときの次数と定数項
            assertEquals(3, p.orderByDegreeOf(Set.of(x, y)).degree());
            assertEquals("4a", p.orderByDegreeOf(Set.of(x, y)).constant().get().toString());

            // yに着目したときの次数と定数項
            assertEquals(2, p.orderByDegreeOf(y).degree());
            assertEquals("x^3 + 4a", p.orderByDegreeOf(y).constant().get().toString());
        }
    }

    @Nested
    class 練習1 {
        /**
         * (1) 整式 -2x + 3y + x^2 + 5x - yの同類項をまとめよ。
         */
        @Test
        public void test1() {
            var x = Variable.named('x');
            var y = Variable.named('y');
            var p = x.times(-2)
                    .plus(y.times(3))
                    .plus(x.powerOf(2))
                    .plus(x.times(5))
                    .minus(y);

            assertEquals("x^2 + 3x + 2y", p.orderByDegreeOf(x).toString());
        }

        /**
         * (2) 次の整式において、[ ]内の文字に着目したとき、その次数と定数項をいえ。<br>
         *   (ア) x - 2xy + 3y^2 + 4 - 2x - 7xy + 2y^2 - 1 [y]
         */
        @Test
        public void test2a() {
            var x = Variable.named('x');
            var y = Variable.named('y');
            var p = x.minus(x.times(y))
                    .plus(y.powerOf(2).times(3))
                    .plus(4)
                    .minus(x.times(2))
                    .minus(x.times(y).times(7))
                    .plus(y.powerOf(2).times(2))
                    .minus(1);

            // yに着目したときの次数と定数項
            assertEquals(2, p.orderByDegreeOf(y).degree());
            assertEquals("-x + 3", p.orderByDegreeOf(y).constant().get().toString());
        }

        /**
         * (2) 次の整式において、[ ]内の文字に着目したとき、その次数と定数項をいえ。<br>
         *   (イ) a^2b^2 - ab + 3ab - 2a^2b^2 + 7c^2 + 4a - 5b - 3a + 1 [b]、[aとb]
         */
        @Test
        public void test2b() {
            var a = Variable.named('a');
            var b = Variable.named('b');
            var c = Variable.named('c');
            var p = a.times(b).powerOf(2)
                    .minus(a.times(b))
                    .plus(a.times(b).times(3))
                    .minus(a.times(b).powerOf(2).times(2))
                    .plus(c.powerOf(2).times(7))
                    .plus(a.times(4))
                    .minus(b.times(5))
                    .minus(a.times(3))
                    .plus(1);

            // bに着目したときの次数と定数項
            assertEquals(2, p.orderByDegreeOf(b).degree());
            assertEquals("7c^2 + a + 1", p.orderByDegreeOf(b).constant().get().toString());

            // aとbに着目したときの次数と定数項
            assertEquals(4, p.orderByDegreeOf(Set.of(a, b)).degree());
            assertEquals("7c^2 + 1", p.orderByDegreeOf(Set.of(a, b)).constant().get().toString());
        }
    }
}
