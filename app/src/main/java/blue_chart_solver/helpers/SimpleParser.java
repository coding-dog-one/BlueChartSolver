package blue_chart_solver.helpers;

import blue_chart_solver.models.Polynomial;
import blue_chart_solver.models.operators.Addition;
import blue_chart_solver.models.operators.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Objects;

import static blue_chart_solver.helpers.ReadResult.ReaderState.CLOSE_PARENTHESIS_FOUND;
import static blue_chart_solver.helpers.ReadResult.ReaderState.DIGIT_FOUND;
import static blue_chart_solver.helpers.ReadResult.ReaderState.END_OF_STRING;
import static blue_chart_solver.helpers.ReadResult.ReaderState.HAT_FOUND;
import static blue_chart_solver.helpers.ReadResult.ReaderState.OPEN_PARENTHESIS_FOUND;

public class SimpleParser {
    private static final Logger logger = LoggerFactory.getLogger(SimpleParser.class);
    private static final TermParser termParser = new TermParser();
    private static final OperatorParser operatorParser = new OperatorParser();

    public Polynomial parse(String text) {
        logger.info("Started. Input: {}", text == null ? null : "\"" + text + "\"");
        var reader = new StringReader(Objects.requireNonNull(text));
        var buffer = new ArrayDeque<Polynomial>();
        Polynomial result = null;

        reader.read();
        if (!reader.is(OPEN_PARENTHESIS_FOUND)) {
            logger.trace("Input has a block NOT enclosed in parentheses.");
            reader.markLeft();
            reader.readUntil(OPEN_PARENTHESIS_FOUND);
            reader.markLeft();
            result = parseNoParenthesisText(reader.getMarked());
        }

        if (reader.is(END_OF_STRING)) {
            logger.info("End. Result: {}", result);
            return result;
        }

        logger.trace("The beginning of a block enclosed in parentheses.");
        reader.markRight();
        reader.readUntil(CLOSE_PARENTHESIS_FOUND);
        reader.markLeft();
        buffer.addFirst(parseNoParenthesisText(reader.getMarked()));
        reader.read();

        if (reader.is(HAT_FOUND)) {
            logger.trace("This block has exponent.");
            reader.markRight();
            reader.readWhile(DIGIT_FOUND);
            reader.markRight();
            buffer.addFirst(
                    buffer.removeFirst().powerOf(
                            Integer.parseInt(reader.getMarked())
                    )
            );
            reader.read();
        }
        logger.trace("The end of the block.");

        if (reader.is(OPEN_PARENTHESIS_FOUND)) {
            logger.trace("The beginning of an another block enclosed in parentheses.");
            reader.markRight();
            reader.readUntil(CLOSE_PARENTHESIS_FOUND);
            reader.markLeft();
            result = result != null ? result.times(buffer.removeFirst()) : buffer.removeFirst();
            buffer.addFirst(parseNoParenthesisText(reader.getMarked()));
            reader.read();
            logger.trace("The end of the block.");
        }

        if (reader.is(END_OF_STRING)) {
            result = result != null ? result.times(buffer.removeFirst()) : buffer.removeFirst();
            logger.info("End. Result: {}", result);
            return result;
        }

        logger.error("Quit. Input is not supported format.");
        throw new IllegalArgumentException("Input text is invalid format.");
    }

    private Polynomial parseNoParenthesisText(String text) {
        assert text != null;
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
