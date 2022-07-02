package blue_chart_solver.helpers;

import blue_chart_solver.models.Polynomial;
import blue_chart_solver.models.operators.Addition;
import blue_chart_solver.models.operators.Multiply;
import blue_chart_solver.models.operators.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static blue_chart_solver.helpers.ReadResult.ReaderState.CLOSE_PARENTHESIS_FOUND;
import static blue_chart_solver.helpers.ReadResult.ReaderState.DIGIT_FOUND;
import static blue_chart_solver.helpers.ReadResult.ReaderState.END_OF_STRING;
import static blue_chart_solver.helpers.ReadResult.ReaderState.HAT_FOUND;
import static blue_chart_solver.helpers.ReadResult.ReaderState.MINUS_FOUND;
import static blue_chart_solver.helpers.ReadResult.ReaderState.OPEN_PARENTHESIS_FOUND;
import static blue_chart_solver.helpers.ReadResult.ReaderState.OPERATOR_FOUND;
import static blue_chart_solver.helpers.ReadResult.ReaderState.WHITE_SPACE_FOUND;

public class SimpleParser {
    private static final Logger logger = LoggerFactory.getLogger(SimpleParser.class);
    private static final TermParser termParser = new TermParser();
    private static final OperatorParser operatorParser = new OperatorParser();

    public Polynomial parse(String text) {
        logger.info("Started. Input: {}", text == null ? null : "\"" + text + "\"");
        var reader = new StringReader(Objects.requireNonNull(text));

        reader.read();
        var result = parseUntilOpenParenthesisFound(reader);
        var operator = result != null
                ? (Operator) new Multiply()
                : null;

        while (reader.isNotIn(END_OF_STRING)) {
            var buffer = parseChunkOfParentheses(reader);
            result = result != null
                    ? operator.operate(result, buffer)
                    : buffer;

            operator = parseOperatorIfExists(reader);
            if (operator == null) { break; }
        }

        logger.info("End. Result: {}", result);
        return result;
    }

    /**
     * parse text like "(x - a)(x - b)(x - c)"
     */
    private Polynomial parseChunkOfParentheses(StringReader reader) {
        Polynomial result = null;
        while (reader.isIn(OPEN_PARENTHESIS_FOUND)) {
            logger.trace("The beginning of a block enclosed in parentheses.");
            var buffer = parseBetweenParentheses(reader);
            var exponent = parseExponentIfExists(reader);
            logger.trace("The end of the block.");

            buffer = exponent != null
                    ? buffer.powerOf(exponent)
                    : buffer;

            result = result != null
                    ? result.times(buffer)
                    : buffer;
        }
        return result;
    }

    /**
     * Parse text until an open parenthesis found.<br>
     * If "-3a(x - y)" was given, then return -3a.<br>
     * If "x - y" was given, then return x - y.<br>
     * If "(x - y)" was given, then return null.
     * @return Term before an open parenthesis (return null if term does not exist.)
     */
    private Polynomial parseUntilOpenParenthesisFound(StringReader reader) {
        if (reader.isIn(OPEN_PARENTHESIS_FOUND)) {
            return null;
        }

        logger.trace("Input has a block NOT enclosed in parentheses.");
        reader.markLeft();
        reader.readUntil(OPEN_PARENTHESIS_FOUND);
        reader.markLeft();
        logger.trace("The end of the block.");
        return parseNoParenthesisText(reader.getMarked());
    }

    /**
     * Parse text like "(x + a)(x - a)" or "(x + a) + (x + b)", etc.
     * @return Operator between the blocks (return null if operator does not exist.)
     */
    private Operator parseOperatorIfExists(StringReader reader) {
        // "(x + a)(x - a)" pattern
        //         ↑ Expect the reader to be here.
        if (reader.isIn(OPEN_PARENTHESIS_FOUND)) {
            logger.trace("The next block starts immediately without operator.");
            return new Multiply(); // return * because "()()" means "() * ()".
        }

        // "(x + a) + (x + b)" pattern
        //         ↑ Expect the reader to be here.
        if (reader.isIn(WHITE_SPACE_FOUND)) {
            var result = reader.read();
            if (reader.isInAny(OPERATOR_FOUND, MINUS_FOUND)) {
                logger.trace("There is an operator between this block and the next.");
                reader.markLeft();
                reader.markRight();
                reader.skipWhiteSpace();
                return operatorParser.parse(reader.getMarked());

            } else {
                logger.error("Quit. Input is not supported format.");
                throw new IllegalArgumentException("Input text is invalid format. Operator expected but " + result.found() + " found");
            }
        }

        return null;
    }

    /**
     * Parse text like "(x - a)" and return x - a.
     */
    private Polynomial parseBetweenParentheses(StringReader reader) {
        assert reader.isIn(OPEN_PARENTHESIS_FOUND);

        reader.markRight();
        reader.readUntil(CLOSE_PARENTHESIS_FOUND);
        reader.markLeft();
        reader.read();
        return parseNoParenthesisText(reader.getMarked());
    }

    /**
     * Parse text like "^10" and return 10.
     * @return exponent (return null if text does not start with "^".)
     */
    private Integer parseExponentIfExists(StringReader reader) {
        if (!reader.isIn(HAT_FOUND)) {
            return null;
        }

        logger.trace("This block has exponent.");
        reader.markRight();
        reader.readWhile(DIGIT_FOUND);
        reader.markRight();
        reader.read();
        return Integer.parseInt(reader.getMarked());
    }

    private Polynomial parseNoParenthesisText(String text) {
        assert text != null;
        assert !(text.contains("(") || text.contains(")"));
        logger.debug("Analyzing \"{}\"...", text);

        String[] splitText = text.split(" ");
        var length = splitText.length;
        if (length % 2 == 0) {
            logger.error("Quit. The length of split text should be odd but was {}", length);
            throw new IllegalArgumentException("Input text is invalid format.");
        }

        var result = Polynomial.from(0);
        var operator = (Operator) new Addition();
        for (int i = 0; i < splitText.length; i++) {
            var s = splitText[i];
            if (i % 2 == 0) {
                logger.trace("Call TermParser to analyze \"{}\".", s);
                result = operator.operate(result, termParser.parse(s));
            } else {
                logger.trace("Call OperatorParser to analyze \"{}\".", s);
                operator = operatorParser.parse(s);
            }
        }
        logger.debug("Analyzing finished. Result: {}", result);
        return result;
    }
}
