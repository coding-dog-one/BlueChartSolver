package BlueChartSolver.app;

import BlueChartSolver.app.Parser.SimpleParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SimpleParserTest {
    private static final SimpleParser parser = new SimpleParser();

    @Test
    public void testAssertion() {
        assertThrows(AssertionError.class, () -> parser.parse(""));
        assertThrows(AssertionError.class, () -> parser.parse("+"));
        assertThrows(AssertionError.class, () -> parser.parse("+ 100"));
        assertDoesNotThrow(() -> parser.parse("5"));
        assertDoesNotThrow(() -> parser.parse("-5"));
        assertDoesNotThrow(() -> parser.parse("-5abc"));
        assertDoesNotThrow(() -> parser.parse("-5abc^2"));
        assertDoesNotThrow(() -> parser.parse("-5abc^2 + 100"));
    }

    @Test
    public void test() {
        assertEquals("2x", parser.parse("x + x").toString());
    }
}
