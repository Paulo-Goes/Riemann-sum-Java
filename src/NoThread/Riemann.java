package NoThread;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.function.Function;

public class Riemann {
    /*
     * gerenciador: Responsável por criar a pasta de arquivos na Área de trabalho, criar e abrir os documentos de textos
     * writer: Escritor para escrever os dados da conta no documento de texto
     * */
    private final GerenciadorDeArquivos gerenciador = new GerenciadorDeArquivos();
    private final BufferedWriter writer;

    public Riemann() throws IOException, InterruptedException {
        gerenciador.createFolder();//Criar a pasta
        writer = new BufferedWriter(new FileWriter(gerenciador.getTxt()));//Inicia o escritor
    }

    private static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text + " ".repeat(Math.max(0, padding));
    }

    /*
     * Calculo da soma de Riemann usando os parâmetros recebidos da Main.java
     *
     * */
    public void calculate(Function<Double, Double> f, double a, double b, int n, String s) throws IOException, InterruptedException, AWTException {
        //Cabeçalho
        writer.write("Soma de Riemann\nf(x) = " + s + " | a = " + a + " | b = " + b + " | Intervalos = " + n + "\n");

        /*
         * valores: Armazena a área de cada intervalo para ser usado mais tarde
         * c: Contador para reiniciar o escritor
         * formato: Restringe o número de casas decimais
         * */
        String[] valores = new String[n];
        //Tamanho máximo de casas decimais é 16
        DecimalFormat formato = new DecimalFormat("#.################");

        double deltaX = (b - a) / n;
        double soma = 0;//Armazenará a soma das áreas de cada intervalo

        for (int i = 0; i < n; i++) {//Efetua a soma para cada intervalo
            double x1 = a + i * deltaX;
            double area = f.apply(x1) * deltaX;
            soma += area;

            valores[i] = formato.format(area); //Armazena o valor da área parcial

            if (i % 250 == 0) { //Reiniciar escritor toda a vez que c for maior que 250
                writer.flush();
            }
            System.out.println(i);

            writeText(formato, i, x1, area, soma); //Escreve os valores do intervalo para o documento de texto
        }

        System.out.println("Done!");
        Thread.sleep(1000);
        /*
         * Todos os intervalos foram calculados
         * Escrever valores das áreas parciais e resultado
         * */

        for (int i = 0; i < n; i++) {//Escreve valores áreas parciais
            if (i % 5 == 0 && i != 0) {
                writer.write("\n");
            }
            if (i == n - 1) {
                writer.write(valores[i] + " = " + soma); // Escreve resultado
            } else {
                writer.write(valores[i] + " + ");
            }
            if (i % 250 == 0) {
                writer.flush();
            }
            System.out.println(i);
        }
        System.out.println("Resultado final: " + formato.format(soma));
        writer.flush();
        writer.write("\n\nResultado final: " + soma);
        writer.close();

        gerenciador.showText();
    }

    private void writeText(DecimalFormat format, int i, double x1, double area, double soma) throws IOException {
        writer.write("=================================================\n");

        int aux = String.valueOf(i + 1).length();//Lógica para formatar o documento de texto
        int aux1 = 48;
        String s = "Intervalo:";
        if (aux % 2 == 0) {
            s += " ";
        }
        writer.write(String.format("|%s|%n", centerText(s + (i + 1), aux1)));


        aux = format.format(x1).length() - 2;//Lógica para formatar o documento de texto
        s = "X1:";
        if (aux % 2 != 0) {
            s += " ";
            aux1--;
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
            aux1--;
            s += " ";
        }
        writer.write(String.format("|%s|%n", centerText(s + format.format(soma), aux1)));
        writer.write("=================================================\n");
    }
}