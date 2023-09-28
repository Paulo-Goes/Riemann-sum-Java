import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.function.Function;

public class Main {

    //Função a ser usada
    static double function(double x) {//x*x
        return Math.pow(x, 2);
    }

    public static void main(String[] args) throws IOException, InterruptedException, AWTException {
        Function<Double, Double> function = Main::function; //Função pré-feita
        String functionString = "x^2";


        //Endereço da área de trabalho
        File desktop = new File(FileSystemView.getFileSystemView().getHomeDirectory() + "\\resultado.txt");

        //Limite inferior
        double limInf = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite inferior da integral: "));

        //Limite superior
        double limSup = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite superior da integral: "));

        //Número de intervalos
        int intervalos = Integer.parseInt(JOptionPane.showInputDialog("Digite o número de intervalos para a aproximação: "));

        //Calcula a soma de Riemann passando os dados
        riemann(function, limInf, limSup, intervalos, desktop, functionString);

        System.out.println("Abrindo arquivo...");

        //Comando para abrir o arquivo de texto
        ProcessBuilder pb = new ProcessBuilder("notepad.exe", desktop.toString());
        pb.start();

        Thread.sleep(2000);//Espera 2 segundos para que o arquivo abra

        Robot robot = new Robot(); //Maximiza o arquivo de texto
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyPress(KeyEvent.VK_UP);
        robot.keyRelease(KeyEvent.VK_UP);
        robot.keyRelease(KeyEvent.VK_WINDOWS);
    }

    static void riemann(Function<Double, Double> f, double a, double b, int n, File desktop, String function) throws IOException {//Soma de Riemann pelo ponto médio
        String[] valores = new String[n]; //Segura a área de cada intervalo

        BufferedWriter writer = new BufferedWriter(new FileWriter(desktop)); //Cria arquivo de texto
        writer.write("Soma de Riemann\nf(x) = "+function+" | a = " + a + " | b = " + b + " | Intervalos = " + n + "\n");

        int c = 1;//Contador para reiniciar o buffer do arquivo de texto

        double deltaX = (b - a) / n; //Calcula o DeltaX
        double soma = 0; //Variável de soma para adquirir o resultado final

        DecimalFormat format = new DecimalFormat("#.##################"); //Limita o tamanho dos números decimais

        for (int i = 0; i < n; i++) {//Para cada intervalo n
            double x1 = a + i * deltaX;
            double area = f.apply(x1) * deltaX; //Area do retângulo
            valores[i] = format.format(area); //Salva o valor para depois
            soma += area; //Soma com o resto dos valores


            writer.write("=================================================\n");

            int aux = String.valueOf(i + 1).length();//Lógica para formatar o documento de texto
            String s = "Intervalo:";
            if (aux % 2 == 0) {
                s += " ";
            }
            writer.write(String.format("|%s|%n", centerText(s + (i + 1), 48)));


            aux = format.format(x1).length() - 2;//Lógica para formatar o documento de texto
            s = "X1:";
            int aux1 = 48;
            if (aux % 2 != 0) {
                s += " ";
                aux1 --;
            }
            writer.write(String.format("|%s|%n", centerText(s + format.format(x1), aux1)));


            s = format.format(area);//Lógica para formatar o documento de texto
            aux = s.length() - 2;
            s = "Área parcial:";
            aux1 = 48;
            if (aux % 2 != 0) {
                aux1--;
                s += " ";
            }
            writer.write(String.format("|%s|%n", centerText(s + format.format(area), aux1)));


            s = format.format(soma);//Lógica para formatar o documento de texto
            aux = s.length() - 2;
            s = "Resultado parcial:";
            aux1 = 48;
            if (aux % 2 == 0) {
                aux1 --;
                s += " ";
            }
            writer.write(String.format("|%s|%n", centerText(s + format.format(soma), aux1)));
            writer.write("=================================================\n");


            System.out.println(c);//Mostra o valor do contador no terminal para reiniciar buffer
            if (c > 250) {//Se o contador atingiu a condição, reiniciar buffer e colocar contador como 0
                writer.flush();
                c = 0;
            }
            c++;//c = c + 1
        }
        for (int i = 0; i < n; i++) {//Lógica para mostrar a conta de todas as áreas de forma organizada
            if (i % 5 == 0 && i != 0) {
                writer.write("\n");
            }
            if (i == n - 1) {
                writer.write(valores[i] + " = " + soma);
            } else {
                writer.write(valores[i] + " + ");
            }
        }
        writer.flush();//Reinicia o buffer pela ultima vez
        writer.write("\n\nResultado final: " + soma); //Mostra o resultado final
        writer.close();//Fecha o buffer e salva o arquivo de texto
    }

    public static String centerText(String text, int width) {//Lógica para alinhar o texto no arquivo de texto
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) +
                text +
                " ".repeat(Math.max(0, padding));
    }
}