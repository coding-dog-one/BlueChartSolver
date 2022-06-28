package BlueChartSolver.helpers;

import BlueChartSolver.models.operators.Addition;
import BlueChartSolver.models.operators.Multiply;
import BlueChartSolver.models.operators.Subtraction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OperatorParserTest {
    private static final OperatorParser parser = new OperatorParser();

    @Test
    void throwNullPointerException_WhenInputIsNull() {
        var input = "@@@";
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    void throwIllegalArgumentException_WhenInputDoesNotMatchAnyOperator() {
        var input = "@@@";
        assertThrows(IllegalArgumentException.class, () -> parser.parse(input));
    }

    @Test
    void parseAdditionSymbol() {
        var input = "+";
        var output = parser.parse(input);
        assertEquals(Addition.class, output.getClass());
    }

    @Test
    void parseSubtractionSymbol() {
        var input = "-";
        var output = parser.parse(input);
        assertEquals(Subtraction.class, output.getClass());
    }

    @Test
    void parseMultiplySymbol() {
        var input = "*";
        var output = parser.parse(input);
        assertEquals(Multiply.class, output.getClass());
    }
}
