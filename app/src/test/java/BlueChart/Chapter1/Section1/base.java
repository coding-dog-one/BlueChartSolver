package BlueChart.Chapter1.Section1;

import blue_chart_solver.helpers.SimpleParser;
import blue_chart_solver.models.Polynomial;

public class base {
    private static final SimpleParser parser = new SimpleParser();

    Polynomial parse(String expression) {
        return parser.parse(expression);
    }

    String expand(String expression) {
        return parse(expression).toString();
    }
}
