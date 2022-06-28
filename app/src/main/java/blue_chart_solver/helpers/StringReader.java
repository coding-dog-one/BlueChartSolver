package blue_chart_solver.helpers;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class StringReader {
    private final char[] text;
    private int idx = -1;
    private final Deque<Integer> marks = new ArrayDeque<>();

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
            if (result.state.equals(expected) || result.state.equals(ReadResult.ReaderState.END_OF_STRING)) {
                return result;
            }
        }
    }

    public ReadResult readWhile(ReadResult.ReaderState expected) {
        while (true) {
            var result = read();
            if (!result.state.equals(expected) || result.state.equals(ReadResult.ReaderState.END_OF_STRING)) {
                return readBackwards();
            }
        }
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
        return String.valueOf(Arrays.copyOfRange(text, start, end));
    }
}
