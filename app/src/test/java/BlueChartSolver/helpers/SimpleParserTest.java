package BlueChartSolver.helpers;

import BlueChartSolver.models.Polynomial;
import BlueChartSolver.models.Variable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SimpleParserTest {
    private static final SimpleParser parser = new SimpleParser();

    @Test
    public void throwIllegalArgumentException_WhenSplitInputLengthIsEven() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse(""));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("   "));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("+"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("+ 100"));
    }

    @Test
    public void parseMonomial() {
        assertEquals(Polynomial.from(10), parser.parse("10"));

        var x = Variable.named('x');
        assertEquals(x.powerOf(2).times(-5), parser.parse("-5x^2"));
    }
    @Test
    public void parsePolynomial() {
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
}
