package Thread;

import java.util.function.Function;

public class Riemann implements Runnable {
    private final ListaOrdenada lista;
    private final Function<Double, Double> function;
    private final double a, b;
    private final int nTotal, n;

    public Riemann(ListaOrdenada lista ,Function<Double, Double> function, double a, double b, int nTotal, int n) {
        this.lista = lista;
        this.function = function;
        this.a = a;
        this.b = b;
        this.nTotal = nTotal;
        this.n = n;
    }

    private Dados calculate(int i) {
        double deltaX = (b - a) / nTotal;
        double soma = 0;

        double x1 = a + i * deltaX;
        double area = function.apply(x1) * deltaX;
        soma += area;

        return new Dados((i + 1), x1, area, soma);
    }

    @Override
    public void run() {
        for (int i = n; i < nTotal; i++) {
            lista.addOrdered(calculate(i));
        }
    }
}