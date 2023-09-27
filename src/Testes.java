import java.util.function.Function;

public class Testes {
    public static void main(String[] args){
        Function<Double, Double> funcao = Main::function;
        double a = 0;
        double b = 1;
        int n = 20;

        double deltaX = (b - a)/n;

    }
    static double function(double x){
        return Math.pow(x,2);
    }
}