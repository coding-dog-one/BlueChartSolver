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

    public static ReadResult of(char[] text, int idx) {
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
        OPEN_PARENTHESIS_FOUND(c -> c == '('),
        CLOSE_PARENTHESIS_FOUND(c -> c == ')'),
        HAT_FOUND(c -> c == '^'),
        DIGIT_FOUND(Character::isDigit),
        BEGINNING_OF_STRING(null),
        END_OF_STRING(null),
        OTHER(null);

        private final Predicate<Character> condition;
        ReaderState(Predicate<Character> condition) {
            this.condition = condition;
        }

        public static ReaderState findState(char[] text, int idx) {
            if (idx < 0) {
                return BEGINNING_OF_STRING;
            }

            if (idx >= text.length) {
                return END_OF_STRING;
            }

            return Arrays.stream(ReaderState.values())
                    .filter(v -> v.condition != null)
                    .filter(v -> v.condition.test(text[idx]))
                    .findFirst().orElse(OTHER);
        }
    }
}
