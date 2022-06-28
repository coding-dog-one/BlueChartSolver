package BlueChartSolver.helpers;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static BlueChartSolver.helpers.ReadResult.ReaderState.CloseParenthesisFound;
import static BlueChartSolver.helpers.ReadResult.ReaderState.DigitFound;
import static BlueChartSolver.helpers.ReadResult.ReaderState.EndOfString;
import static BlueChartSolver.helpers.ReadResult.ReaderState.HatFound;
import static BlueChartSolver.helpers.ReadResult.ReaderState.HeadOfString;
import static BlueChartSolver.helpers.ReadResult.ReaderState.OpenParenthesisFound;
import static BlueChartSolver.helpers.ReadResult.ReaderState.Other;
import static BlueChartSolver.helpers.ReadResult.ReaderState.findState;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReadResultTest {
    private static final char[] text = "3(a + b)^10".toCharArray();
    @Nested
    class ReaderStateTest {
        @Test
        public void openParenthesisFound() {
            var idx = 1;
            assertEquals('(', text[idx]);
            assertEquals(OpenParenthesisFound, findState(text, idx));
        }

        @Test
        public void closeParenthesisFound() {
            var idx = 7;
            assertEquals(')', text[idx]);
            assertEquals(CloseParenthesisFound, findState(text, idx));
        }

        @Test
        public void hatFound() {
            var idx = 8;
            assertEquals('^', text[idx]);
            assertEquals(HatFound, findState(text, idx));
        }

        @Test
        public void digitFound() {
            var idx = 0;
            assertEquals('3', text[idx]);
            assertEquals(DigitFound, findState(text, idx));
        }

        @Test
        public void returnsNotStarted_whenIndexIsLessThanZero() {
            assertEquals(HeadOfString, findState(text, -1));
            assertEquals(HeadOfString, findState(text, -2));
            assertEquals(HeadOfString, findState(text, -100));
        }

        @Test
        public void returnsEndOfString_whenIndexIsGreaterThanOrEqualToTextLength() {
            assertEquals(EndOfString, findState(text, text.length));
            assertEquals(EndOfString, findState(text, text.length+1));
            assertEquals(EndOfString, findState(text, 1000));
        }

        @Test
        public void returnsOther_whenReadCharacterDoesNotMatchAnyCondition() {
            var idx = 2;
            assertEquals('a', text[idx]);
            assertEquals(Other, findState(text, idx));
        }
    }

    @Test
    public void returnsFoundCharacter_whenIndexIsWithinValidRange() {
        var r1 = ReadResult.of(text, 0);
        assertEquals(Optional.of('3'), r1.found());
        assertEquals(0, r1.at);
        assertEquals(DigitFound, r1.state);

        var r2 = ReadResult.of(text, 1);
        assertEquals(Optional.of('('), r2.found());
        assertEquals(1, r2.at);
        assertEquals(OpenParenthesisFound, r2.state);

        var r3 = ReadResult.of(text, 7);
        assertEquals(Optional.of(')'), r3.found());
        assertEquals(7, r3.at);
        assertEquals(CloseParenthesisFound, r3.state);

        var r4 = ReadResult.of(text, 8);
        assertEquals(Optional.of('^'), r4.found());
        assertEquals(8, r4.at);
        assertEquals(HatFound, r4.state);

        var r5 = ReadResult.of(text, 2);
        assertEquals(Optional.of('a'), r5.found());
        assertEquals(2, r5.at);
        assertEquals(Other, r5.state);
    }

    @Test
    public void returnsEmpty_whenIndexIsOutOfValidRange() {
        var r1 = ReadResult.of(text, -1);
        assertEquals(Optional.empty(), r1.found());
        assertEquals(-1, r1.at);
        assertEquals(HeadOfString, r1.state);

        var r2 = ReadResult.of(text, -100);
        assertEquals(Optional.empty(), r2.found());
        assertEquals(-100, r2.at);
        assertEquals(HeadOfString, r2.state);

        var r3 = ReadResult.of(text, text.length);
        assertEquals(Optional.empty(), r3.found());
        assertEquals(text.length, r3.at);
        assertEquals(EndOfString, r3.state);

        var r4 = ReadResult.of(text, 100);
        assertEquals(Optional.empty(), r4.found());
        assertEquals(100, r4.at);
        assertEquals(EndOfString, r4.state);
    }
}
