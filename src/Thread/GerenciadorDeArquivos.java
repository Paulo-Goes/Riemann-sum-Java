package Thread;

import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class GerenciadorDeArquivos {
    private final File desktop; //Área de trabalho
    private final File folder; //Pasta de arquivos
    private final File txt;
    private final BufferedWriter writer;
    private final DecimalFormat format;
    private final String s;
    private final double a, b;
    private final int n;
    private boolean hasTitle;

    public GerenciadorDeArquivos(String s, double a, double b, int n) throws IOException, InterruptedException {
        desktop = new File(FileSystemView.getFileSystemView().getHomeDirectory().toString());
        folder = new File(desktop, "Calculo 2 Riemann - resultados");
        txt = getTxtFile();
        createFolder();
        writer = new BufferedWriter(new FileWriter(txt));
        format = new DecimalFormat("#.################");
        this.s = s;
        this.a = a;
        this.b = b;
        this.n = n;
        hasTitle = false;
    }

    private void createFolder() throws InterruptedException { //Cria a pasta de respostas caso não exista
        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            if (created) {
                System.out.println("Pasta criada.");
            } else {
                System.err.println("Não foi possível criar a pasta.");
            }
        } else {
            System.out.println("A pasta já existe.");
        }
        Thread.sleep(1000);
    }

    private File getTxtFile() throws IOException { //Cria um novo documento de texto
        String baseFileName = "resultado.txt";
        int fileNumber = 0;
        File txtFile;
        do {
            String fileName = baseFileName;
            if (fileNumber > 0) {
                fileName = baseFileName.replace(".txt", "(" + fileNumber + ").txt");
            }
            txtFile = new File(folder, fileName);
            fileNumber++;
        } while (txtFile.exists());
        return txtFile;
    }

    public void showText() throws IOException, InterruptedException, AWTException {
        //Comando para abrir o documento de texto
        ProcessBuilder pb = new ProcessBuilder("notepad.exe", txt.getAbsolutePath());
        pb.start();

        Thread.sleep(1000);//Espera 2 segundos para que o documento de texto abra

        Robot robot = new Robot(); //Maximiza a janela
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyPress(KeyEvent.VK_UP);
        robot.keyRelease(KeyEvent.VK_UP);
        robot.keyRelease(KeyEvent.VK_WINDOWS);

        System.out.println("Maximizado");
    }

    public void writeText(int n, ListaOrdenada l) throws IOException {
        if (!hasTitle) {
            writer.write("Soma de Riemann\nf(x) = " + s + " | a = " + a + " | b = " + b + " | Intervalos = " + n + "\n");
            hasTitle = true;
        }
        for (int i = 1; i < n; i++) {
            writer.write("=================================================\n");

            int aux = String.valueOf(i).length();//Lógica para formatar o documento de texto
            int aux1 = 48;
            String s = "Intervalo:";
            if (aux % 2 == 0) {
                s += " ";
            }
            writer.write(String.format("|%s|%n", centerText(s + (i), aux1)));


            aux = format.format(l.search(i).getX1()).length() - 2;//Lógica para formatar o documento de texto
            s = "X1:";
            if (aux % 2 != 0) {
                s += " ";
                aux1--;
            }
            writer.write(String.format("|%s|%n", centerText(s + format.format(l.search(i).getX1()), aux1)));


            s = format.format(l.search(i).getArea());//Lógica para formatar o documento de texto
            aux = s.length() - 2;
            s = "Área parcial:";
            aux1 = 48;
            if (aux % 2 != 0) {
                aux1--;
                s += " ";
            }
            writer.write(String.format("|%s|%n", centerText(s + format.format(l.search(i).getArea()), aux1)));


            s = format.format(l.search(i).getSoma());//Lógica para formatar o documento de texto
            aux = s.length() - 2;
            s = "Resultado parcial:";
            aux1 = 48;
            if (aux % 2 == 0) {
                aux1--;
                s += " ";
            }
            writer.write(String.format("|%s|%n", centerText(s + format.format(l.search(i).getSoma()), aux1)));
            writer.write("=================================================\n");
            if(i % 3000 == 0){
                writer.flush();
            }
        }
        System.out.println("Resultado final: " + format.format(l.search(n).getSoma()));
        writer.flush();
        writer.write("\n\nResultado final: " + l.search(n).getSoma());
        writer.close();
    }

    private static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text + " ".repeat(Math.max(0, padding));
    }
}