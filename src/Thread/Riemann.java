package Thread;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.function.Function;

public class Riemann implements Runnable {
    private final Function<Double, Double> function;
    private final double a, b;
    private final int op, n;
    private final Escritor g;
    private double soma, previous;

    public Riemann(Function<Double, Double> function, String functionString, double a, double b, int n, int op, File subFolder) throws IOException {
        this.function = function;
        this.a = a;
        this.b = b;
        this.n = n;
        this.op = op;

        String s = "Esquerda.txt";

        if (op == 2) {
            s = "Meio.txt";
        }

        if (op == 3) {
            s = "Direita.txt";
        }

        g = new Escritor(new File(subFolder, s), functionString, a, b);
    }

    private Dados calculate(int i) {
        soma = 0;

        if(i != 0){
            soma = previous;
        }

        double deltaX = (b - a) / n;
        double x = 0;
        if (op == 1) {
            x = a + i * deltaX;
        }
        if (op == 2) {
            x = a + (i + 0.5) * deltaX;
        }
        if (op == 3) {
            x = a + (i + 1) * deltaX;
        }

        double area = function.apply(x) * deltaX;
        previous = area;
        soma += area;

        return new Dados(x, area, soma);
    }

    @Override
    public void run() {
        for (int i = 0; i < n; i++) {
            try {
                g.writeText(calculate(i), n, i);
            } catch (IOException | InterruptedException | AWTException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            g.writeResult(soma);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}