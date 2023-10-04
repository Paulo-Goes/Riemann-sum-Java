package NoThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.function.Function;

/*
* Documento de texto so funciona se notepad.exe tiver os seguintes parâmetros:
* Fonte: Consolas
* Estilo: Regular
* Tamanho: 11
* Escrita: Ocidental
* */

public class Main {
    public static double function(double x) {
        return Math.pow(x, 2);//x^2
    }

    public static void main(String[] args) throws IOException, InterruptedException, AWTException {
        long start = System.currentTimeMillis();
        Function<Double, Double> function = Main::function;
        String s = "x^2";

        double a = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite inferior da integral: "));
        double b = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite superior da integral: "));
        int n = Integer.parseInt(JOptionPane.showInputDialog("Digite o número de intervalos: "));

        Riemann r = new Riemann();
        r.calculate(function, a, b, n, s, 1);
        r = new Riemann();
        r.calculate(function, a, b, n, s, 2);
        r = new Riemann();
        r.calculate(function, a, b, n, s, 3);

        GerenciadorDeArquivos g = new GerenciadorDeArquivos();

        showFolder(g.getFolder());

        System.out.println((System.currentTimeMillis() - start)/1000);
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
}