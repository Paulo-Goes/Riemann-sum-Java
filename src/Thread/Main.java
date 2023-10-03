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
        Dados[] dados;

        Function<Double, Double> function = Main::function;
        String s = "x^2";

        double a = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite inferior da integral: "));
        double b = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite superior da integral: "));

        int n = Integer.parseInt(JOptionPane.showInputDialog("Digite o número de intervalos (acima de 1): "));
        while(n <= 1){
            n = Integer.parseInt(JOptionPane.showInputDialog("Digite o número de intervalos (acima de 1): "));
        }

        dados = new Dados[n];

        GerenciadorDeArquivos g = new GerenciadorDeArquivos(s, a, b);

        Thread t1, t2;

        t1 = new Thread(new Riemann(dados, function, a, b, 0, (n/2), n));
        t2 = new Thread(new Riemann(dados, function, a, b, (n/2), n, n));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Done");

        double soma = dados[(n/2) - 1].getSoma();
        for(int i = n/2;i < n;i++){
            soma += dados[i].getSoma();
            dados[i].setSoma(soma);
        }

        System.out.println("Ordered");
        System.out.println("Writing");
        g.writeText(n, dados);
        System.out.println("Done");
    }
}