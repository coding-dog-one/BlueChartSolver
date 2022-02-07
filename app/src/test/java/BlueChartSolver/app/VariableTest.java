package BlueChartSolver.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VariableTest {

    @Test
    public void 変数の生成テスト() {
        Variable x = Variable.named("x");
        assertEquals("x", x.toString());
        assertEquals("2x", x.times(2).toString());
        assertEquals("2x^2", x.times(2).powerOf(2).toString());
        assertEquals("6x^5", x.times(2).times(3).powerOf(2).powerOf(3).toString());
        assertEquals("x", x.toString());  //Immutable
    }

    @Test
    public void 変数の等価性テスト() {
        Variable x = Variable.named("x");
        assertEquals(x, Variable.named("x"));
        assertNotEquals(x, Variable.named("xx"));
        assertNotEquals(x, x.times(2));
        assertNotEquals(x, x.powerOf(2));
    }
}
