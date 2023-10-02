package NoThread;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.function.Function;

public class Riemann {
    private final GerenciadorDeArquivos gerenciador = new GerenciadorDeArquivos();
    private final BufferedWriter writer;

    public Riemann() throws IOException, InterruptedException {
        gerenciador.createFolder();
        writer = new BufferedWriter(new FileWriter(gerenciador.getTxt()));
    }

    private static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text + " ".repeat(Math.max(0, padding));
    }

    public void calculate(Function<Double, Double> f, double a, double b, int n, String s) throws IOException, InterruptedException, AWTException {
        writer.write("Soma de Thread.Riemann\nf(x) = " + s + " | a = " + a + " | b = " + b + " | Intervalos = " + n + "\n");
        DecimalFormat formato = new DecimalFormat("#.################");

        double deltaX = (b - a) / n;
        double soma = 0;

        for (int i = 0; i < n; i++) {
            double x1 = a + i * deltaX;
            double area = f.apply(x1) * deltaX;
            soma += area;

            if (i % 250 == 0) {
                writer.flush();
            }
            System.out.println(i);

            writeText(formato, i, x1, area, soma);
        }
        writer.flush();
        writer.write("\n\nResultado final: " + soma);
        writer.close();

        gerenciador.showText();
    }

    private void writeText(DecimalFormat format, int i, double x1, double area, double soma) throws IOException {
        writer.write("=================================================\n");

        int aux = String.valueOf(i + 1).length();
        int aux1 = 48;
        String s = "Intervalo:";
        if (aux % 2 == 0) {
            s += " ";
        }
        writer.write(String.format("|%s|%n", centerText(s + (i + 1), aux1)));


        aux = format.format(x1).length() - 2;
        s = "X1:";
        if (aux % 2 != 0) {
            s += " ";
            aux1--;
        }
        writer.write(String.format("|%s|%n", centerText(s + format.format(x1), aux1)));


        s = format.format(area);
        aux = s.length() - 2;
        s = "Área parcial:";
        aux1 = 48;
        if (aux % 2 != 0) {
            aux1--;
            s += " ";
        }
        writer.write(String.format("|%s|%n", centerText(s + format.format(area), aux1)));


        s = format.format(soma);
        aux = s.length() - 2;
        s = "Resultado parcial:";
        aux1 = 48;
        if (aux % 2 == 0) {
            aux1--;
            s += " ";
        }
        writer.write(String.format("|%s|%n", centerText(s + format.format(soma), aux1)));
        writer.write("=================================================\n");
    }
}