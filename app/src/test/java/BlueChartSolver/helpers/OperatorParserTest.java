package BlueChartSolver.helpers;

import BlueChartSolver.models.operators.Addition;
import BlueChartSolver.models.operators.Multiply;
import BlueChartSolver.models.operators.Subtraction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OperatorParserTest {
    private static final OperatorParser parser = new OperatorParser();

    @Test
    public void parseAdditionSymbol() {
        var input = "+";
        var output = parser.parse(input);
        assertEquals(Addition.class, output.getClass());
    }

    @Test
    public void parseSubtractionSymbol() {
        var input = "-";
        var output = parser.parse(input);
        assertEquals(Subtraction.class, output.getClass());
    }

    @Test
    public void parseMultiplySymbol() {
        var input = "*";
        var output = parser.parse(input);
        assertEquals(Multiply.class, output.getClass());
    }

    @Test
    public void throwIllegalArgumentException_WhenInputIsInvalid() {
        var input = "@@@";
        assertThrows(IllegalArgumentException.class, () -> parser.parse(input));
    }
}
