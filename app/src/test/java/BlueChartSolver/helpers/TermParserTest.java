package BlueChartSolver.helpers;

import BlueChartSolver.models.Polynomial;
import BlueChartSolver.models.Variable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TermParserTest {
    private static final TermParser parser = new TermParser();

    @Test
    public void parseInt() {
        assertEquals(Polynomial.from(1), parser.parse("1"));
        assertEquals(Polynomial.from(999), parser.parse("999"));
        assertEquals(Polynomial.from(-5), parser.parse("-5"));
    }

    @Test
    public void parseVariable() {
        var x = Variable.named('x');
        assertEquals(Polynomial.from(x), parser.parse("x"));

        var h = Variable.named('h');
        var o = Variable.named('o');
        var g = Variable.named('g');
        var e = Variable.named('e');
        assertEquals(h.times(o).times(g).times(e), parser.parse("hoge"));
    }

    @Test
    public void parseVariableWithCoefficient() {
        var x = Variable.named('x');
        assertEquals(x.times(5), parser.parse("5x"));
        assertEquals(x.times(50), parser.parse("50x"));

        assertEquals(x.times(-1), parser.parse("-x"));
        assertEquals(x.times(-1), parser.parse("-1x"));
        assertEquals(x.times(-500), parser.parse("-500x"));

        var y = Variable.named('y');
        assertEquals(x.times(y).times(3), parser.parse("3xy"));
        assertEquals(x.times(y).times(-10), parser.parse("-10xy"));
    }

    @Test
    public void parseVariableWithExponent() {
        var x = Variable.named('x');
        assertEquals(x.powerOf(2), parser.parse("x^2"));

        var y = Variable.named('y');
        assertEquals(x.powerOf(2).times(y), parser.parse("x^2y"));

        var z = Variable.named('z');
        assertEquals(x.powerOf(2).times(y).times(z.powerOf(99)), parser.parse("x^2yz^99"));
    }

    @Test
    public void parseVariableWithCoefficientAndExponent() {
        var x = Variable.named('x');
        assertEquals(x.powerOf(2).times(5), parser.parse("5x^2"));
        assertEquals(x.powerOf(2).times(-5), parser.parse("-5x^2"));

        var y = Variable.named('y');
        assertEquals(x.powerOf(2).times(y).times(10), parser.parse("10x^2y"));

        var z = Variable.named('z');
        assertEquals(x.powerOf(2).times(y).times(z.powerOf(99)).times(345), parser.parse("345x^2yz^99"));
    }

    @Test
    public void throwIllegalArgumentException_WhenInputIsNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse(null));
        assertThrows(IllegalArgumentException.class, () -> parser.parse(""));
    }

    @Test
    public void throwIllegalArgumentException_WhenInputDoesNotMatchAnyPattern() {
        // Input is not empty but blank
        assertThrows(IllegalArgumentException.class, () -> parser.parse("   "));

        // Input violates negativePattern
        assertThrows(IllegalArgumentException.class, () -> parser.parse("-"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("-0"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("--10"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("-5@"));

        // Input violates coefficientPattern
        assertThrows(IllegalArgumentException.class, () -> parser.parse("0a"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("0123"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("8-"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("5^5"));

        // Input violates variableAndExponentPattern
        assertThrows(IllegalArgumentException.class, () -> parser.parse("a^b"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("a^-2"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("a^0123"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("a^2-"));

        // Input violates variableOnlyPattern
        assertThrows(IllegalArgumentException.class, () -> parser.parse("a4"));
    }
}
