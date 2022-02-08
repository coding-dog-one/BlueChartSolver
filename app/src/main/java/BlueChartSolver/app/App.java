package BlueChartSolver.app;

import java.util.regex.Pattern;

public class App {
    public static void main(String[] args) {
        MonomialFunction mf = Variable.named('x').powerOf(3).times(2);
//                .times(Variable.named("b"))
//                .times(Variable.named("a"))
//                .times(Variable.named("b").powerOf(2))
//                .times(5);
        System.out.println(mf);

    }
}
