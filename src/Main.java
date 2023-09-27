import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) throws IOException {
        Function<Double, Double> funcao = Main::function;
        double inf = 0;
        double sup = 1;
        int n = 10000;
        riemann(funcao,inf,sup,n);
    }

    static void riemann(Function<Double, Double> f, double a, double b, int n) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("resultado.txt"));
        int c = 1;
        double deltaX = (b - a)/n;
        double soma = 0;

        for(int i = 0; i < n;i++){
            double x1 = a + i * deltaX;
            double x2 = a + (i + 1) * deltaX;
            double area = (f.apply(x1) + f.apply(x2)) * deltaX / 2;
            soma += area;


            int aux = String.valueOf(i + 1).length();
            String s = "Intervalo:";
            if(aux % 2 == 0){
                s += " ";
            }
            writer.write("===========================================\n");
            writer.write(String.format("|%s|%n", centerText(s + (i + 1), 42)));
            writer.write(String.format("|%s|%n", centerText("X1: " + String.format("%.15f", x1), 42)));
            writer.write(String.format("|%s|%n", centerText("Área parcial: " + String.format("%.15f",area), 42)));
            writer.write(String.format("|%s|%n", centerText("Resultado parcial: " + String.format("%.16f",soma), 42)));
            writer.write("===========================================\n");
            System.out.println(c);
            if(c % (n/16) == 0){
                writer.flush();
                c = 0;
            }
            c++;
        }
        writer.flush();
        writer.write("Resultado final: " + soma);
        writer.close();
    }
    static double function(double x){
        return Math.pow(x,2);
    }

    public static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) +
                text +
                " ".repeat(Math.max(0, padding));
    }
}