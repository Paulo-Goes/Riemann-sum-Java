import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) throws IOException {
        Function<Double, Double> funcao = Main::function;
        double limInf = 0;
        double limSup = 1;
        int intervalos = 20;
        riemann(funcao,limInf,limSup,intervalos);
    }

    static void riemann(Function<Double, Double> f, double a, double b, int n) throws IOException {
        String[] valores = new String[n];

        BufferedWriter writer = new BufferedWriter(new FileWriter("resultado.txt"));
        int c = 1;

        double deltaX = (b - a)/n;
        double soma = 0;

        DecimalFormat format = new DecimalFormat("#.##########");

        for(int i = 0; i < n;i++){
            double x1 = a + i * deltaX;
            double x2 = a + (i + 1) * deltaX;
            double area = (f.apply(x1) + f.apply(x2)) * deltaX / 2;
            valores[i] = format.format(area);
            soma += area;

            writer.write("===========================================\n");

            int aux = String.valueOf(i + 1).length();
            String s = "Intervalo:";
            if(aux % 2 == 0){
                s += " ";
            }
            writer.write(String.format("|%s|%n", centerText(s + (i + 1), 42)));

            aux = format.format(x1).length() - 2;
            s = "X1:";
            int aux1 = 42;
            if(aux % 2 != 0){
                s += " ";
                aux1 = 41;
            }
            writer.write(String.format("|%s|%n", centerText(s + format.format(x1), aux1)));
            writer.write(String.format("|%s|%n", centerText("Área parcial: " + format.format(area), 42)));

            s = format.format(soma);
            aux = s.length()-2;
            s = "Resultado parcial:";
            aux1 = 42;
            if(aux % 2 == 0){
                aux1 = 41;
                s += " ";
            }
            writer.write(String.format("|%s|%n", centerText(s + format.format(soma), aux1)));

            writer.write("===========================================\n");
            System.out.println(c);
            if(c % (n/16) == 0){
                writer.flush();
                c = 0;
            }
            c++;
        }
        for(int i = 0;i < n;i++){
            if(i % 5 == 0 && i != 0){
                writer.write("\n");
            }
            if(i == n - 1){
                writer.write(valores[i] + " = "+ soma);
            }else{
                writer.write(valores[i] + " + ");
            }
        }
        writer.flush();
        writer.write("\nResultado final: " + soma);
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