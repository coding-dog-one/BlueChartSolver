package BlueChartSolver.app;

public class App {
    public static void main(String[] args) {
        Variable x = Variable.named("x");
        System.out.println(x);
        System.out.println(x.times(2));
        System.out.println(x.times(2).powerOf(3));
        System.out.println(x.powerOf(2).powerOf(3));
    }
}
