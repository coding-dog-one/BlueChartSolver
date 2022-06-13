package BlueChartSolver.app;

import BlueChartSolver.app.Parser.SimpleParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SimpleParserTest {
    private static final SimpleParser parser = new SimpleParser();

    @Test
    public void testAssertion() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse(""));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("+"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("+ 100"));
    }

    @Test
    public void parseSpaceSeparatedText() {
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
