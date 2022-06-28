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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringReaderTest {
    @Nested
    class ProgressiveReadingTest {
        @Test
        public void readReadsNextCharacterWhileIncrementingIndex() {
            var reader = new StringReader("-5a(a + b)^10");
            assertEquals(Other, reader.read().state);
            assertEquals(DigitFound, reader.read().state);
            assertEquals(Other, reader.read().state);
            assertEquals(OpenParenthesisFound, reader.read().state);
            assertEquals(Other, reader.read().state);
            assertEquals(Other, reader.read().state);
            assertEquals(Other, reader.read().state);
            assertEquals(Other, reader.read().state);
            assertEquals(Other, reader.read().state);
            assertEquals(CloseParenthesisFound, reader.read().state);
            assertEquals(HatFound, reader.read().state);
            assertEquals(DigitFound, reader.read().state);
            assertEquals(DigitFound, reader.read().state);

            var result1 = reader.read();
            assertEquals(13, result1.at);
            assertEquals(EndOfString, result1.state);

            // After the end of the string is reached, the increment stops.
            var result2 = reader.read();
            assertEquals(13, result2.at);
            assertEquals(EndOfString, result2.state);
        }

        @Test
        public void readBackwardsReadsPreviousCharacterWhileDecrementingIndex() {
            var reader = new StringReader("123");

            // The index is not decremented before starting read.
            var result1 = reader.readBackwards();
            assertEquals(Optional.empty(), result1.found());
            assertEquals(-1, result1.at);
            assertEquals(HeadOfString, result1.state);

            assertEquals(Optional.of('1'), reader.read().found());
            assertEquals(Optional.of('2'), reader.read().found());
            assertEquals(Optional.of('3'), reader.read().found());
            assertEquals(Optional.of('2'), reader.readBackwards().found());
            assertEquals(Optional.of('1'), reader.readBackwards().found());

            var result2 = reader.readBackwards();
            assertEquals(-1, result2.at);
            assertEquals(HeadOfString, result2.state);

            // After the beginning of the string is reached, the decrement stops.
            var result3 = reader.readBackwards();
            assertEquals(-1, result3.at);
            assertEquals(HeadOfString, result3.state);
        }
    }

    @Nested
    class HighSpeedReadingTest {
        private final String testString = "03(1234)5678";
        @Test
        public void readUntil() {
            var reader = new StringReader(testString);

            // Reads "03(" and stops because the condition is met.
            var result1 = reader.readUntil(OpenParenthesisFound);
            assertEquals(2, result1.at);
            assertEquals(Optional.of('('), result1.found());

            // Reads "1234)" and stops because the condition is met.
            var result2 = reader.readUntil(CloseParenthesisFound);
            assertEquals(7, result2.at);
            assertEquals(Optional.of(')'), result2.found());
        }

        @Test
        public void readUntilGoesToEndOfString_whenExpectedConditionNeverMatches() {
            var reader = new StringReader(testString);

            var result = reader.readUntil(HatFound);
            assertEquals(EndOfString, result.state);
            assertEquals(12, result.at);
            assertEquals(Optional.empty(), result.found());
        }

        @Test
        public void readWhile() {
            var reader = new StringReader(testString);

            // Don't move because the next is not match the condition.
            var result0 = reader.readWhile(OpenParenthesisFound);
            assertEquals(-1, result0.at);
            assertEquals(Optional.empty(), result0.found());

            // Read "03" and return the final digit = '3'.
            var result1 = reader.readWhile(DigitFound);
            assertEquals(1, result1.at);
            assertEquals(Optional.of('3'), result1.found());

            // Don't move because the next is not match the condition.
            var result2 = reader.readWhile(DigitFound);
            assertEquals(1, result2.at);
            assertEquals(Optional.of('3'), result2.found());

            reader.read(); // Skip '('.

            // Read "1234" and return the final digit = '4'.
            var result3 = reader.readWhile(DigitFound);
            assertEquals(6, result3.at);
            assertEquals(Optional.of('4'), result3.found());

            // Don't move because the next is not match the condition.
            var result4 = reader.readWhile(DigitFound);
            assertEquals(6, result4.at);
            assertEquals(Optional.of('4'), result4.found());

            reader.read(); // Skip ')'.

            // Read "5678" and return the final digit = '8'.
            var result5 = reader.readWhile(DigitFound);
            assertEquals(11, result5.at);
            assertEquals(Optional.of('8'), result5.found());
        }
    }

    @Nested
    class MarkingTest {
        @Test
        public void markLeft() {
            var reader = new StringReader("hello world!"); // "←hello world"
            reader.read(); // 'h'
            reader.markLeft(); // put a mark on the left side of 'h'.
            reader.read(); // 'e'
            reader.read(); // 'l'
            reader.read(); // 'l'
            reader.read(); // 'o'
            reader.read(); // ' '
            reader.markLeft(); // put a mark on the left side of ' '.
            assertEquals("hello", reader.getMarked());
        }

        @Test
        public void markRight() {
            var reader = new StringReader("hello world!");
            reader.markRight(); // put a mark on the right side of '' (= start position).
            reader.read(); // 'h'
            reader.read(); // 'e'
            reader.read(); // 'l'
            reader.read(); // 'l'
            reader.read(); // 'o'
            reader.markRight(); // put a mark on the right side of 'o'.
            assertEquals("hello", reader.getMarked());
        }

        @Test
        public void throwsIllegalStateException_whenTextIsNotMarkedEnough() {
            var reader = new StringReader("hello world!");
            assertThrows(IllegalStateException.class, reader::getMarked);

            reader.markRight();
            assertThrows(IllegalStateException.class, reader::getMarked);

            reader.read();
            reader.markRight();
            assertEquals("h", reader.getMarked());
        }

        @Test
        public void usedMarksIsDeletedWhenGetMarkedIsCalled() {
            var reader = new StringReader("hello world!");
            reader.markRight();
            reader.read();
            reader.markRight();
            assertEquals("h", reader.getMarked());
            assertThrows(IllegalStateException.class, reader::getMarked);
        }
    }
}
