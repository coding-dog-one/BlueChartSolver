package blue_chart_solver.helpers;

import blue_chart_solver.models.Polynomial;
import blue_chart_solver.models.operators.Addition;
import blue_chart_solver.models.operators.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.regex.Pattern;

public class SimpleParser {
    private static final Logger logger = LoggerFactory.getLogger(SimpleParser.class);
    private static final TermParser termParser = new TermParser();
    private static final OperatorParser operatorParser = new OperatorParser();
    private static final Pattern containsParenthesisPattern = Pattern.compile(".*[({].*");

    public Polynomial parse(String text) {
        logger.info("Started. Input: {}", text == null ? null : "\"" + text + "\"");

        if (containsParenthesisPattern.matcher(Objects.requireNonNull(text)).matches()) {
            logger.debug("Input contains parentheses.");
        }

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
            logger.debug("Analyzing \"{}\"...", s);
            if (i % 2 == 0) {
                logger.trace("Call TermParser.");
                result = operator.operate(result, termParser.parse(s));
            } else {
                logger.trace("Call OperatorParser.");
                operator = operatorParser.parse(s);
            }
        }
        logger.info("End. Result: {}", result);
        return result;
    }
}
