package Thread;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class Escritor {
    private final BufferedWriter writer;
    private final DecimalFormat format;
    private final String functionString;
    private final double a, b;
    private boolean hasTitle;

    public Escritor(File txt, String functionString, double a, double b) throws IOException {
        writer = new BufferedWriter(new FileWriter(txt));
        format = new DecimalFormat("#.################");
        this.functionString = functionString;
        this.a = a;
        this.b = b;
        hasTitle = false;
    }

    private static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text + " ".repeat(Math.max(0, padding));
    }

    public void writeText(Dados dados, int n, int i) throws IOException, InterruptedException, AWTException {
        if (!hasTitle) {
            writer.write("Soma de Riemann\nf(x) = " + functionString + " | a = " + a + " | b = " + b + " | Intervalos = " + n + "\n");
            hasTitle = true;
        }

        writer.write("=================================================\n");

        int aux = String.valueOf(i + 1).length();
        int aux1 = 48;
        String s = "Intervalo:";
        if (aux % 2 == 0) {
            s += " ";
        }
        writer.write(String.format("|%s|%n", centerText(s + (i + 1), aux1)));


        aux = format.format(dados.getX1()).length() - 2;
        s = "X1:";
        if (aux % 2 != 0) {
            s += " ";
            aux1--;
        }
        writer.write(String.format("|%s|%n", centerText(s + format.format(dados.getX1()), aux1)));


        s = format.format(dados.getArea());
        aux = s.length() - 2;
        s = "Área parcial:";
        aux1 = 48;
        if (aux % 2 != 0) {
            aux1--;
            s += " ";
        }
        writer.write(String.format("|%s|%n", centerText(s + format.format(dados.getArea()), aux1)));


        s = format.format(dados.getSoma());
        aux = s.length() - 2;
        s = "Resultado parcial:";
        aux1 = 48;
        if (aux % 2 == 0) {
            aux1--;
            s += " ";
        }
        writer.write(String.format("|%s|%n", centerText(s + format.format(dados.getSoma()), aux1)));
        writer.write("=================================================\n");
        if (i % 3000 == 0) {
            writer.flush();
        }
    }

    public void writeResult(double soma) throws IOException {
        writer.flush();
        writer.write("\n\nResultado final: " + soma);
        writer.close();
    }
}