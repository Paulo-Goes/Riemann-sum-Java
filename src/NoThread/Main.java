package NoThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.function.Function;

import static java.lang.Character.isDigit;

/*
 * Documento de texto so funciona se notepad.exe tiver os seguintes parâmetros:
 * Fonte: Consolas
 * Estilo: Regular
 * Tamanho: 11
 * Escrita: Ocidental
 * */

public class Main {
    private static boolean enable1, enable2, enable3;

    //Função pré-definida
    public static double function(double x) {
        return Math.pow(x, 2);
    }

    public static void main(String[] args) throws IOException, InterruptedException, AWTException {
        Function<Double, Double> function = Main::function;
        String s = "x^2";

        setOp();

        double a = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite inferior da integral: "));
        double b = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite superior da integral: "));
        int n = getInterval();

        //Medir tempo de execução
        long start = System.currentTimeMillis();

        //Calcular Riemann pela esquerda, meio e direita
        Riemann r;
        if (enable1) {
            r = new Riemann(1, s);
            r.calculate(function, a, b, n, s, 1);
        }

        if (enable2) {
            r = new Riemann(2, s);
            r.calculate(function, a, b, n, s, 2);
        }

        if (enable3) {
            r = new Riemann(3, s);
            r.calculate(function, a, b, n, s, 3);
        }

        GerenciadorDeArquivos g = new GerenciadorDeArquivos();

        //Abrir pasta
        showFolder(g.getFolder());

        //Mostrar tempo de execução em segundos
        System.out.println((System.currentTimeMillis() - start) / 1000);
    }

    public static void showFolder(File folder) throws IOException, InterruptedException, AWTException {
        ProcessBuilder pb = new ProcessBuilder("explorer.exe", folder.getAbsolutePath());
        pb.start();

        Thread.sleep(1000);

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyPress(KeyEvent.VK_UP);
        robot.keyRelease(KeyEvent.VK_UP);
        robot.keyRelease(KeyEvent.VK_WINDOWS);

        System.out.println("Maximizado");
    }

    private static int getInterval() {
        int n = Integer.parseInt(JOptionPane.showInputDialog("Digite o número de intervalos (acima de 1): "));
        while (n <= 1) {
            n = Integer.parseInt(JOptionPane.showInputDialog("Digite o número de intervalos (acima de 1): "));
        }
        return n;
    }

    private static boolean isFormatted(String input) {
        if (input.isEmpty()) return false;
        for (int i = 0; i < input.length(); i++) {
            if (!isDigit(input.charAt(i))) {
                return false;
            } else {
                int aux = Integer.parseInt(String.valueOf(input.charAt(i)));
                if (aux >= 4 || aux == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void setOp() {
        String op = JOptionPane.showInputDialog("1 - Esquerda / 2 - Meio / 3 - Direita (1,2,3)(1,3)").replace(" ", "");

        op = op.replace(" ", "");
        op = op.replace(",", "");

        while (!isFormatted(op)) {
            op = JOptionPane.showInputDialog("1 - Esquerda / 2 - Meio / 3 - Direita (1,2,3)(1,3)");
            op = op.replace(" ", "");
            op = op.replace(",", "");
        }

        for (int i = 0; i < op.length(); i++) {
            int aux = Integer.parseInt(String.valueOf(op.charAt(i)));
            if (aux == 1) {
                enable1 = true;
            }

            if (aux == 2) {
                enable2 = true;
            }

            if (aux == 3) {
                enable3 = true;
            }
        }
    }
}