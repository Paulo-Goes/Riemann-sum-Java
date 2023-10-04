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
 */

public class Main {
    public static double function(double x) {
        return Math.pow(x, 2);//x^2
    }

    /*
     * Selecionar a soma de Riemann pela esquerda, meio, direita ou todos
     * Armazenar os dados de todos os intervalos em seus respectivos arrays
     * Modificar GerenciadorDeArquivos para criar uma subpasta para cada operação
     * Modificar GerenciadorDeArquivos para usar um novo formato do nome das pastas e documentos de texto
     * Melhorar GerenciadorDeArquivos para funcionar com Threads permitindo que os resultados sejam escritos de forma mais rápida
     */


    public static void main(String[] args) throws InterruptedException, IOException, AWTException {
        int op = Integer.parseInt(JOptionPane.showInputDialog("1 - Esquerda / 2 - Meio / 3 - Direita / 4 - Todos"));

        while (op > 4 || op < 1) {
            op = Integer.parseInt(JOptionPane.showInputDialog("1 - Esquerda / 2 - Meio / 3 - Direita / 4 - Todos"));
        }

        Thread t1 = null, t2 = null, t3 = null;

        Dados[] dadosL, dadosM, dadosD;

        Function<Double, Double> function = Main::function;

        String s = "x^2";

        double a = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite inferior da integral: "));
        double b = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite superior da integral: "));

        int n = Integer.parseInt(JOptionPane.showInputDialog("Digite o número de intervalos (acima de 1): "));
        while (n <= 1) {
            n = Integer.parseInt(JOptionPane.showInputDialog("Digite o número de intervalos (acima de 1): "));
        }



        if (op < 2) {
            dadosL = new Dados[n];
            t1 = new Thread(new Riemann(dadosL, function, a, b, n, 1));
            t1.start();
        }

        if (op < 3) {
            dadosM = new Dados[n];
            t2 = new Thread(new Riemann(dadosM, function, a, b, n, 2));
            t2.start();
        }

        if (op < 4) {
            dadosD = new Dados[n];
            t3 = new Thread(new Riemann(dadosD, function, a, b, n, 3));
            t3.start();
        }

        if (t1 != null) {
            t1.join();
        }
        if (t2 != null) {
            t2.join();
        }
        if (t3 != null) {
            t3.join();
        }
    }
}