package BlueChart.Chapter1.Section1;

import BlueChartSolver.app.Variable;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings({"NonAsciiCharacters", "OptionalGetWithoutIsPresent", "NewClassNamingConvention"})
public class Subsection1Test {
    @Nested
    class 例題1 {
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
            assertEquals(2, p.orderByDegreeOf(b).degree());
            assertEquals("5a^2", p.orderByDegreeOf(b).constant().get().toString());
        }

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
            assertEquals(3, p.orderByDegreeOf(Set.of(x, y)).degree());
            assertEquals("4a", p.orderByDegreeOf(Set.of(x, y)).constant().get().toString());
            assertEquals(2, p.orderByDegreeOf(y).degree());
            assertEquals("x^3 + 4a", p.orderByDegreeOf(y).constant().get().toString());
        }
    }

    @Nested
    class 練習1 {
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
            assertEquals(2, p.orderByDegreeOf(y).degree());
            assertEquals("-x + 3", p.orderByDegreeOf(y).constant().get().toString());
        }

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
            assertEquals(2, p.orderByDegreeOf(b).degree());
            assertEquals("7c^2 + a + 1", p.orderByDegreeOf(b).constant().get().toString());
            assertEquals(4, p.orderByDegreeOf(Set.of(a, b)).degree());
            assertEquals("7c^2 + 1", p.orderByDegreeOf(Set.of(a, b)).constant().get().toString());
        }
    }
}
