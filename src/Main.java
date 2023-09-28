import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.function.Function;

public class Main {

    static double function(double x) {//x*x
        return Math.pow(x, 2);
    }

    public static void main(String[] args) throws IOException, InterruptedException, AWTException {
        Function<Double, Double> function = Main::function; //Função pré-feita

        File desktop = new File(FileSystemView.getFileSystemView().getHomeDirectory() + "\\resultado.txt"); //Pega a

        double limInf = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite inferior da integral: "));
        double limSup = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite superior da integral: "));
        int intervalos = Integer.parseInt(JOptionPane.showInputDialog("Digite o número de intervalos para a aproximação: "));

        riemann(function, limInf, limSup, intervalos, desktop);
        System.out.println("Abrindo arquivo...");

        ProcessBuilder pb = new ProcessBuilder("notepad.exe", desktop.toString());
        pb.start();

        Thread.sleep(2000);

        Robot robot = new Robot(); //Maximiza o .txt
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyPress(KeyEvent.VK_UP);
        robot.keyRelease(KeyEvent.VK_UP);
        robot.keyRelease(KeyEvent.VK_WINDOWS);
    }

    static void riemann(Function<Double, Double> f, double a, double b, int n, File desktop) throws IOException {
        String[] valores = new String[n];

        BufferedWriter writer = new BufferedWriter(new FileWriter(desktop));
        writer.write("Soma de Riemann\nf(x) = x^2 | a = " + a + " | b = " + b + " | Intervalos = " + n + "\n");

        int c = 1;

        double deltaX = (b - a) / n;
        double soma = 0;

        DecimalFormat format = new DecimalFormat("#.##################");

        for (int i = 0; i < n; i++) {
            double x1 = a + i * deltaX;
            double x2 = a + (i + 1) * deltaX;
            double area = (f.apply(x1) + f.apply(x2)) * deltaX / 2;
            valores[i] = format.format(area);
            soma += area;


            writer.write("=================================================\n");

            int aux = String.valueOf(i + 1).length();
            String s = "Intervalo:";
            if (aux % 2 == 0) {
                s += " ";
            }
            writer.write(String.format("|%s|%n", centerText(s + (i + 1), 48)));


            aux = format.format(x1).length() - 2;
            s = "X1:";
            int aux1 = 48;
            if (aux % 2 != 0) {
                s += " ";
                aux1 --;
            }
            writer.write(String.format("|%s|%n", centerText(s + format.format(x1), aux1)));

            /*
             * Adicionar write x2 formatado aqui
             * -Paulo
             */
            aux = format.format(x2).length() - 2;
            s = "X2:";
            aux1 = 48;
            if (aux % 2 != 0) {
                s += " ";
                aux1 --;
            }
            writer.write(String.format("|%s|%n", centerText(s + format.format(x2), aux1)));

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
                aux1 --;
                s += " ";
            }
            writer.write(String.format("|%s|%n", centerText(s + format.format(soma), aux1)));
            writer.write("=================================================\n");

            System.out.println(c);
            if (c > 250) {
                writer.flush();
                c = 0;
            }
            c++;
        }
        for (int i = 0; i < n; i++) {
            if (i % 5 == 0 && i != 0) {
                writer.write("\n");
            }
            if (i == n - 1) {
                writer.write(valores[i] + " = " + soma);
            } else {
                writer.write(valores[i] + " + ");
            }
        }
        writer.flush();
        writer.write("\nResultado final: " + soma);
        writer.close();
    }

    public static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) +
                text +
                " ".repeat(Math.max(0, padding));
    }
}