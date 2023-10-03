package Thread;

import java.util.function.Function;

public class Riemann implements Runnable {
    private final Dados[] dados;
    private final Function<Double, Double> function;
    private final double a, b;
    private final int total, n, actualTotal;

    public Riemann(Dados[] dados, Function<Double, Double> function, double a, double b, int n, int total, int actualTotal) {
        this.dados = dados;
        this.function = function;
        this.a = a;
        this.b = b;
        this.n = n;
        this.total = total;
        this.actualTotal = actualTotal;
    }

    private Dados calculate(int i) {
        double soma = 0;
        if(i != 0 && i < actualTotal/2){
            soma = dados[i - 1].getSoma();
        }
        double deltaX = (b - a) / actualTotal;
        double x1 = a + i * deltaX;
        double area = function.apply(x1) * deltaX;
        soma += area;

        return new Dados((i + 1), x1, area, soma);
    }

    @Override
    public void run() {
            for (int i = n; i < total; i++) {
                dados[i] = calculate(i);
        }
    }
}