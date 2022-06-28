package BlueChartSolver.helpers;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

class ReadResult {
    private final Character found;
    public final int at;
    public final ReaderState state;

    private ReadResult(Character c, int idx, ReaderState state) {
        found = c;
        at = idx;
        this.state = state;
    }

    public static ReadResult of(char[] text, int idx) {;
        return new ReadResult(
                idx < 0 || idx >= text.length ? null : text[idx],
                idx,
                ReaderState.findState(text, idx)
        );
    }

    public Optional<Character> found() {
        return Optional.ofNullable(found);
    }


    enum ReaderState {
        OpenParenthesisFound(c -> c == '('),
        CloseParenthesisFound(c -> c == ')'),
        HatFound(c -> c == '^'),
        DigitFound(Character::isDigit),
        HeadOfString(null),
        EndOfString(null),
        Other(null);

        private final Predicate<Character> condition;
        ReaderState(Predicate<Character> condition) {
            this.condition = condition;
        }

        public static ReaderState findState(char[] text, int idx) {
            if (idx < 0) {
                return HeadOfString;
            }

            if (idx >= text.length) {
                return EndOfString;
            }

            return Arrays.stream(ReaderState.values())
                    .filter(v -> v.condition != null)
                    .filter(v -> v.condition.test(text[idx]))
                    .findFirst().orElse(Other);
        }
    }
}
