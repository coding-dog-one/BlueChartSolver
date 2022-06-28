package BlueChartSolver.helpers;

import java.util.Arrays;
import java.util.Stack;

import static BlueChartSolver.helpers.ReadResult.ReaderState.EndOfString;

public class StringReader {
    private final char[] text;
    private int idx = -1;
    private final Stack<Integer> marks = new Stack<>();

    StringReader(String text) {
        this.text = text.toCharArray();
    }

    public ReadResult read() {
        idx = Math.min(idx+1, text.length);
        return ReadResult.of(text, idx);
    }

    public ReadResult readBackwards() {
        idx = Math.max(idx-1, -1);
        return ReadResult.of(text, idx);
    }

    public ReadResult readUntil(ReadResult.ReaderState expected) {
        while (true) {
            var result = read();
            if (result.state.equals(expected) || result.state.equals(EndOfString)) {
                return result;
            }
        }
    }

    public ReadResult readWhile(ReadResult.ReaderState expected) {
        while (true) {
            var result = read();
            if (!result.state.equals(expected) || result.state.equals(EndOfString)) {
                return readBackwards();
            }
        }
    }

    public void markRight() {
        marks.push(idx + 1);
    }

    public void markLeft() {
        marks.push(idx);
    }

    public String getMarked() {
        if (marks.size() < 2) {
            throw new IllegalStateException("At least two must be marked. (" + marks.size() + " marked.)");
        }
        int end = marks.pop();
        int start = marks.pop();
        return String.valueOf(Arrays.copyOfRange(text, start, end));
    }
}
