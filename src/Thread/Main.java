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
    //Função já definida no código
    public static double function(double x) {
        return Math.pow(x, 2);//x^2
    }

    public static void main(String[] args) throws InterruptedException, IOException, AWTException {
        ListaOrdenada lista1 = new ListaOrdenada();
        ListaOrdenada lista2 = new ListaOrdenada();

        Function<Double, Double> function = Main::function; //Recebe a função
        String s = "x^2";

        double a = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite inferior da integral: "));
        double b = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite superior da integral: "));

        int nTotal = Integer.parseInt(JOptionPane.showInputDialog("Digite o número de intervalos (acima de 1): "));
        while(nTotal <= 1){
            nTotal = Integer.parseInt(JOptionPane.showInputDialog("Digite o número de intervalos (acima de 1): "));
        }
        int nMetade = nTotal / 2;

        GerenciadorDeArquivos g = new GerenciadorDeArquivos(s, a, b, nTotal);

        Thread t1, t2;

        t1 = new Thread(new Riemann(lista1, function, a, b, nMetade, 0));
        t2 = new Thread(new Riemann(lista2, function, a, b, nTotal, nMetade));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        for (int i = nMetade + 1; i < nTotal + 1; i++) {
            lista1.addOrdered(lista2.search(i));
        }

        for (int i = 1; i < nTotal + 1; i++) {
            System.out.println(lista1.search(i).getIntervalo());
        }
        g.writeText(nTotal, lista1);
    }
}