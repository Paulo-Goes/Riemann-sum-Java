package Thread;

import javax.swing.*;
import java.awt.*;
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

    public static void main(String[] args) throws InterruptedException, IOException, AWTException {
        ListaOrdenada lista1 = new ListaOrdenada();
        ListaOrdenada lista2 = new ListaOrdenada();

        Function<Double, Double> function = Main::function; //Recebe a função
        String s = "x^2";

        /*double a = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite inferior da integral: "));
        double b = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite superior da integral: "));

        int nTotal = Integer.parseInt(JOptionPane.showInputDialog("Digite o número de intervalos (acima de 1): "));
        while(nTotal <= 1){
            nTotal = Integer.parseInt(JOptionPane.showInputDialog("Digite o número de intervalos (acima de 1): "));
        }*/
        double a = 0;
        double b = 1;
        int nTotal = 100000;
        int nMetade = nTotal / 2;

        GerenciadorDeArquivos g = new GerenciadorDeArquivos(s, a, b, nTotal);

        Thread t1, t2;

        t1 = new Thread(new Riemann(lista1, function, a, b, nMetade, 0));
        t2 = new Thread(new Riemann(lista2, function, a, b, nTotal, nMetade));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Done");

        for (int i = nMetade + 1; i < nTotal + 1; i++) {
            lista1.addOrdered(lista2.search(i));
        }
        System.out.println("Ordered");
        System.out.println("Writing");
        g.writeText(nTotal, lista1);
        System.out.println("Done");
    }
}