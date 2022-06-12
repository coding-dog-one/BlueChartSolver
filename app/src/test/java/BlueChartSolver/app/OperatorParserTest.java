package BlueChartSolver.app;

import BlueChartSolver.app.Operator.Addition;
import BlueChartSolver.app.Operator.Multiply;
import BlueChartSolver.app.Operator.Operator;
import BlueChartSolver.app.Operator.Subtraction;
import BlueChartSolver.app.Parser.OperatorParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OperatorParserTest {
    private static final OperatorParser parser = new OperatorParser();

    @Test
    public void parseAdditionSymbol() {
        String input = "+";
        Operator output = parser.parse(input);
        assertEquals(Addition.class, output.getClass());
    }

    @Test
    public void parseSubtractionSymbol() {
        String input = "-";
        Operator output = parser.parse(input);
        assertEquals(Subtraction.class, output.getClass());
    }

    @Test
    public void parseMultiplySymbol() {
        String input = "*";
        Operator output = parser.parse(input);
        assertEquals(Multiply.class, output.getClass());
    }

    @Test
    public void throwIllegalArgumentExceptionWhenInputWasInvalid() {
        String input = "@@@";
        assertThrows(IllegalArgumentException.class, () -> parser.parse(input));
    }
}
