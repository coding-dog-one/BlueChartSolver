package BlueChartSolver.models;

import BlueChartSolver.models.Variable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("NonAsciiCharacters")
public class VariableTest {

    @Test
    public void 変数はアルファベット一文字で表す() {
        Variable x = Variable.named('x');
        assertEquals("x", x.toString());

        Variable F = Variable.named('F');
        assertEquals("F", F.toString());

        assertThrows(AssertionError.class, () -> Variable.named('1'));

        assertThrows(AssertionError.class, () -> Variable.named('%'));
    }

    @Test
    public void 名前が同じ変数は等価である() {
        Variable x = Variable.named('x');
        assertEquals(x, Variable.named('x'));
        assertNotEquals(x, Variable.named('y'));
    }
}
