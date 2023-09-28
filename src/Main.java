import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.function.Function;

public class Main {
    //Função já definida no código
    static double function(double x) {
        return Math.pow(x, 2);//x^2
    }

    public static void main(String[] args) throws IOException, InterruptedException, AWTException {
        Function<Double, Double> function = Main::function; //Recebe a função
        String s = "x^2";

        /*
         * a: Limite inferior
         * b: Limite superior
         * n: Intervalos
         * */
        double a = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite inferior da integral: "));
        double b = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite superior da integral: "));
        int n = Integer.parseInt(JOptionPane.showInputDialog("Digite o número de intervalos: "));

        /*
         * Cria e calcula a soma de Riemann passando os parâmetros
         * */
        Riemann r = new Riemann();
        r.calculate(function, a, b, n, s);
    }
}