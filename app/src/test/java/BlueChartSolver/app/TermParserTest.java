package BlueChartSolver.app;

import BlueChartSolver.app.Parser.TermParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TermParserTest {
    private static final TermParser parser = new TermParser();

    @Test
    public void test() {
        assertTrue(parser.canParse("10"));
        assertTrue(parser.canParse("-5"));
        assertTrue(parser.canParse("-10"));
        assertTrue(parser.canParse("-10abc"));
        assertTrue(parser.canParse("-1abc"));
        assertTrue(parser.canParse("-10a^5"));
        assertTrue(parser.canParse("-10a^5b^10"));
        assertTrue(parser.canParse("a"));
        assertTrue(parser.canParse("abc"));
        assertTrue(parser.canParse("a^5"));
        assertTrue(parser.canParse("-a^5"));
        assertTrue(parser.canParse("a^5b^10"));

        assertFalse(parser.canParse("1*2+3"));
        assertFalse(parser.canParse("a^b-5^c"));
        assertFalse(parser.canParse("--10"));
        assertFalse(parser.canParse("-0"));
        assertFalse(parser.canParse("-a^0123"));
        assertFalse(parser.canParse("a^2b5"));
    }
}
