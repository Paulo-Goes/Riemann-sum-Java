package Thread;

import java.io.File;
import java.util.function.Function;

public class Riemann implements Runnable {
    private Dados[] dados;
    private final Function<Double, Double> function;
    private final double a, b;
    private final int op, n;
    private final File txt;

    public Riemann(Dados[] dados, Function<Double, Double> function, double a, double b, int n, int op) {
        this.dados = dados;
        this.function = function;
        this.a = a;
        this.b = b;
        this.n = n;
        this.op = op;
        txt = new File("desktop");
    }

    private Dados calculate(int i) {
        double soma = 0;

        double deltaX = (b - a) / n;
        double x = 0;
        if(op == 1) {
            x = a + i * deltaX;
        }
        if(op == 2){
            x = a + (i + 0.5) * deltaX;
        }
        if(op == 3){
            x = a + (i + 1) * deltaX;
        }

        double area = function.apply(x) * deltaX;
        soma += area;
        return new Dados(x, area, soma);
    }

    @Override
    public void run() {
            for (int i = 0; i < n; i++) {
               dados[i] = calculate(i);
        }
    }
}