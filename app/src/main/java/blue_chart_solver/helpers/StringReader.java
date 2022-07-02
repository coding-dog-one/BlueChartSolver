package blue_chart_solver.helpers;

import blue_chart_solver.helpers.ReadResult.ReaderState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import static blue_chart_solver.helpers.ReadResult.ReaderState.END_OF_STRING;
import static blue_chart_solver.helpers.ReadResult.ReaderState.WHITE_SPACE_FOUND;

public class StringReader {
    private static final Logger logger = LoggerFactory.getLogger(StringReader.class);
    private final char[] text;
    private int idx = -1;
    private final Deque<Integer> marks = new ArrayDeque<>();
    private final Deque<ReadResult> history = new ArrayDeque<>();

    StringReader(String text) {
        this.text = text.toCharArray();
    }

    public ReadResult read() {
        idx = Math.min(idx+1, text.length);
        history.addFirst(ReadResult.of(text, idx));
        return history.getFirst();
    }

    public ReadResult readBackwards() {
        logger.debug("Step back.");
        idx = Math.max(idx-1, -1);
        history.addFirst(ReadResult.of(text, idx));
        return history.getFirst();
    }

    public ReadResult readUntil(ReaderState expected) {
        logger.debug("Read until {}", expected);
        while (true) {
            var result = read();
            logger.trace("Found: '{}'", result.found());
            if (isInAny(expected, END_OF_STRING)) {
                logger.debug("readUntil finished. The final state: {}", result.state);
                return result;
            }
        }
    }

    public ReadResult readWhile(ReaderState expected) {
        logger.debug("Read while {}", expected);
        while (true) {
            var result = read();
            logger.trace("Found: '{}'", result.found());
            if (isNotIn(expected) || isIn(END_OF_STRING)) {
                logger.debug("readWhile finished. The final state: {}", result.state);
                return readBackwards();
            }
        }
    }

    public ReadResult skipWhiteSpace() {
        logger.debug("Skip white spaces.");
        while (true) {
            var result = read();
            logger.trace("Found: '{}'", result.found());
            if (!isIn(WHITE_SPACE_FOUND)) {
                logger.debug("skipWhiteSpace finished. The final state: {}", result.state);
                return result;
            }
        }
    }

    public ReadResult getLastResult() {
        return history.getFirst();
    }

    public boolean isIn(ReaderState expected) {
        return getLastResult().state.equals(expected);
    }

    public boolean isNotIn(ReaderState expected) {
        return !isIn(expected);
    }

    public boolean isInAny(ReaderState... expected) {
        return Arrays.asList(expected).contains(getLastResult().state);
    }

    public void markRight() {
        marks.addFirst(idx + 1);
    }

    public void markLeft() {
        marks.addFirst(idx);
    }

    public String getMarked() {
        if (marks.size() < 2) {
            throw new IllegalStateException("At least two must be marked. (" + marks.size() + " marked.)");
        }
        int end = marks.removeFirst();
        int start = marks.removeFirst();
        var result = String.valueOf(Arrays.copyOfRange(text, start, end));
        logger.debug("Return marked text from {} to {}, that is \"{}\"", start, end, result);
        return result;
    }
}
