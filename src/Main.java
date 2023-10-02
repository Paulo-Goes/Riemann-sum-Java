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

    public static void main(String[] args) throws InterruptedException {


        //writer.write("Soma de Riemann\nf(x) = " + s + " | a = " + a + " | b = " + b + " | Intervalos = " + n + "\n");
        ListaOrdenada lista1 = new ListaOrdenada();
        ListaOrdenada lista2 = null;

        Function<Double, Double> function = Main::function; //Recebe a função
        String s = "x^2";

        Thread t1, t2 = null;

        double a = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite inferior da integral: "));
        double b = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite superior da integral: "));
        int nTotal = Integer.parseInt(JOptionPane.showInputDialog("Digite o número de intervalos: "));

        if(nTotal >= 5000){
            t1 = new Thread(new Riemann(lista1 ,function, a, b, nTotal/2, 0));
            lista2 = new ListaOrdenada();
            t2 = new Thread(new Riemann(lista2 ,function, a, b, nTotal - (nTotal/2), nTotal/2));
            t2.start();
        }else{
            t1 = new Thread(new Riemann(lista1, function, a, b, nTotal, 0));
        }
        t1.start();

        t1.join();
        if(t2 != null){
            t2.join();

            for(int i = nTotal/2;i < nTotal - (nTotal/2);i++){
                lista1.addOrdered(lista2.search(i));
            }
        }
        for(int i = 0;i < nTotal;i++){
            System.out.println(lista1.search(i).getIntervalo());
        }
    }
}