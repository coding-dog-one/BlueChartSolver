package blue_chart_solver.helpers;

import blue_chart_solver.models.Polynomial;
import blue_chart_solver.models.Variable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimpleParserTest {
    private static final SimpleParser parser = new SimpleParser();

    @Test
    void throwNullPointerException_WhenInputIsNull() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    void throwIllegalArgumentException_WhenSplitInputLengthIsEven() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("  "));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("+ 100"));
    }

    @Test
    void parseMonomial() {
        assertEquals(Polynomial.from(10), parser.parse("10"));

        var x = Variable.named('x');
        assertEquals(x.powerOf(2).times(-5), parser.parse("-5x^2"));
    }
    @Test
    void parsePolynomial() {
        var x = Variable.named('x');
        var f1 = x.powerOf(2).times(-1)
                .plus(x.times(5))
                .minus(4);
        assertEquals(f1, parser.parse("-x^2 + 5x - 4"));

        var y = Variable.named('y');
        var f2 = x.powerOf(2).times(-5)
                .plus(x.times(4).times(y))
                .minus(y.powerOf(2).times(4));
        assertEquals(f2, parser.parse("-5x^2 + 4xy - 4y^2"));
    }

    @Test
    void parsePolynomial_withParentheses() {
        var a = Variable.named('a');
        var b = Variable.named('b');

        assertEquals(
                (a.plus(b)).times(a.times(-3)),
                parser.parse("-3a(a + b)")
        );

        assertEquals(
                a.plus(b).times(a.plus(b)),
                parser.parse("(a + b)(a + b)")
        );

        assertEquals(
                a.plus(b).times(a.plus(b)).times(a.plus(b)),
                parser.parse("(a + b)(a + b)(a + b)")
        );

        assertEquals(
                a.plus(b).times(a.plus(b)).times(a.plus(b)).times(a.plus(b)),
                parser.parse("(a + b)(a + b)(a + b)(a + b)")
        );
    }

    @Test
    void parsePolynomial_WithParentheses_WithExponent() {
        var a = Variable.named('a');
        var b = Variable.named('b');

        assertEquals(
                (a.plus(b)).powerOf(2),
                parser.parse("(a + b)^2")
        );

        assertEquals(
                a.plus(b).powerOf(2).times(a.minus(b)),
                parser.parse("(a + b)^2(a - b)")
        );

        assertEquals(
                a.plus(b).powerOf(2).times(a.plus(b).powerOf(3)),
                parser.parse("(a + b)^2(a + b)^3")
        );
    }

    @Test
    void parsePolynomial_WithParentheses_WithOperator() {
        var x = Variable.named('x');
        var a = Variable.named('a');
        var b = Variable.named('b');
        var c = Variable.named('c');

        assertEquals(
                (x.minus(a).times(x.minus(b)).times(a.minus(c)))
                        .minus(x.minus(b).times(x.minus(c)).times(b.minus(a)))
                        .minus(x.minus(c).times(x.minus(a)).times(c.minus(b))),
                parser.parse("(x - a)(x - b)(a - c) - (x - b)(x - c)(b - a) - (x - c)(x - a)(c - b)")
        );
    }

    @Test
    void parsePolynomial_WithParentheses_WithExponent_WithOperator() {
        var a = Variable.named('a');
        var b = Variable.named('b');

        assertEquals(
                a.plus(b).powerOf(2)
                        .plus(a.minus(b).powerOf(3))
                        .plus(a.plus(b).powerOf(3)),
                parser.parse("(a + b)^2 + (a - b)^3 + (a + b)^3")
        );

        assertEquals(
                a.plus(b).powerOf(2)
                        .minus(a.minus(b).powerOf(3))
                        .plus(a.plus(b).powerOf(3))
                        .minus(a.minus(b).powerOf(3)),
                parser.parse("(a + b)^2 - (a - b)^3 + (a + b)^3 - (a - b)^3")
        );
    }
}
