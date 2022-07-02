package blue_chart_solver.helpers;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static blue_chart_solver.helpers.ReadResult.ReaderState.CLOSE_PARENTHESIS_FOUND;
import static blue_chart_solver.helpers.ReadResult.ReaderState.DIGIT_FOUND;
import static blue_chart_solver.helpers.ReadResult.ReaderState.END_OF_STRING;
import static blue_chart_solver.helpers.ReadResult.ReaderState.HAT_FOUND;
import static blue_chart_solver.helpers.ReadResult.ReaderState.BEGINNING_OF_STRING;
import static blue_chart_solver.helpers.ReadResult.ReaderState.MINUS_FOUND;
import static blue_chart_solver.helpers.ReadResult.ReaderState.OPEN_PARENTHESIS_FOUND;
import static blue_chart_solver.helpers.ReadResult.ReaderState.OPERATOR_FOUND;
import static blue_chart_solver.helpers.ReadResult.ReaderState.OTHER;
import static blue_chart_solver.helpers.ReadResult.ReaderState.WHITE_SPACE_FOUND;
import static blue_chart_solver.helpers.ReadResult.ReaderState.findState;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReadResultTest {
    private static final char[] text = "-3(a + b)^10".toCharArray();
    @Nested
    class ReaderStateTest {
        @Test
        void openParenthesisFound() {
            var idx = 2;
            assertEquals('(', text[idx]);
            assertEquals(OPEN_PARENTHESIS_FOUND, findState(text, idx));
        }

        @Test
        void closeParenthesisFound() {
            var idx = 8;
            assertEquals(')', text[idx]);
            assertEquals(CLOSE_PARENTHESIS_FOUND, findState(text, idx));
        }

        @Test
        void hatFound() {
            var idx = 9;
            assertEquals('^', text[idx]);
            assertEquals(HAT_FOUND, findState(text, idx));
        }

        @Test
        void digitFound() {
            var idx = 1;
            assertEquals('3', text[idx]);
            assertEquals(DIGIT_FOUND, findState(text, idx));
        }

        @Test
        void whiteSpaceFound() {
            var idx = 4;
            assertEquals(' ', text[idx]);
            assertEquals(WHITE_SPACE_FOUND, findState(text, idx));
        }

        @Test
        void minusFound() {
            var idx = 0;
            assertEquals('-', text[idx]);
            assertEquals(MINUS_FOUND, findState(text, idx));
        }

        @Test
        void operatorFound() {
            var idx = 5;
            assertEquals('+', text[idx]);
            assertEquals(OPERATOR_FOUND, findState(text, idx));
        }

        @Test
        void returnsNotStarted_whenIndexIsLessThanZero() {
            assertEquals(BEGINNING_OF_STRING, findState(text, -1));
            assertEquals(BEGINNING_OF_STRING, findState(text, -2));
            assertEquals(BEGINNING_OF_STRING, findState(text, -100));
        }

        @Test
        void returnsEndOfString_whenIndexIsGreaterThanOrEqualToTextLength() {
            assertEquals(END_OF_STRING, findState(text, text.length));
            assertEquals(END_OF_STRING, findState(text, text.length+1));
            assertEquals(END_OF_STRING, findState(text, 1000));
        }

        @Test
        void returnsOther_whenReadCharacterDoesNotMatchAnyCondition() {
            var idx = 3;
            assertEquals('a', text[idx]);
            assertEquals(OTHER, findState(text, idx));
        }
    }

    @Test
    void returnsFoundCharacter_whenIndexIsWithinValidRange() {
        var r1 = ReadResult.of(text, 1);
        assertEquals(Optional.of('3'), r1.found());
        assertEquals(1, r1.at);
        assertEquals(DIGIT_FOUND, r1.state);

        var r2 = ReadResult.of(text, 2);
        assertEquals(Optional.of('('), r2.found());
        assertEquals(2, r2.at);
        assertEquals(OPEN_PARENTHESIS_FOUND, r2.state);

        var r3 = ReadResult.of(text, 8);
        assertEquals(Optional.of(')'), r3.found());
        assertEquals(8, r3.at);
        assertEquals(CLOSE_PARENTHESIS_FOUND, r3.state);

        var r4 = ReadResult.of(text, 9);
        assertEquals(Optional.of('^'), r4.found());
        assertEquals(9, r4.at);
        assertEquals(HAT_FOUND, r4.state);

        var r5 = ReadResult.of(text, 3);
        assertEquals(Optional.of('a'), r5.found());
        assertEquals(3, r5.at);
        assertEquals(OTHER, r5.state);
    }

    @Test
    void returnsEmpty_whenIndexIsOutOfValidRange() {
        var r1 = ReadResult.of(text, -1);
        assertEquals(Optional.empty(), r1.found());
        assertEquals(-1, r1.at);
        assertEquals(BEGINNING_OF_STRING, r1.state);

        var r2 = ReadResult.of(text, -100);
        assertEquals(Optional.empty(), r2.found());
        assertEquals(-100, r2.at);
        assertEquals(BEGINNING_OF_STRING, r2.state);

        var r3 = ReadResult.of(text, text.length);
        assertEquals(Optional.empty(), r3.found());
        assertEquals(text.length, r3.at);
        assertEquals(END_OF_STRING, r3.state);

        var r4 = ReadResult.of(text, 100);
        assertEquals(Optional.empty(), r4.found());
        assertEquals(100, r4.at);
        assertEquals(END_OF_STRING, r4.state);
    }
}
