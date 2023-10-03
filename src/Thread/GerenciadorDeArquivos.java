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
    private final File desktop;
    private final File folder;
    private final File txt;
    private final BufferedWriter writer;
    private final DecimalFormat format;
    private final String s;
    private final double a, b;
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
        hasTitle = false;
    }

    private void createFolder() throws InterruptedException {
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

    private File getTxtFile() {
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
        ProcessBuilder pb = new ProcessBuilder("notepad.exe", txt.getAbsolutePath());
        pb.start();

        Thread.sleep(1000);

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyPress(KeyEvent.VK_UP);
        robot.keyRelease(KeyEvent.VK_UP);
        robot.keyRelease(KeyEvent.VK_WINDOWS);

        System.out.println("Maximizado");
    }

    public void writeText(int n, Dados[] dados) throws IOException, InterruptedException, AWTException {
        if (!hasTitle) {
            writer.write("Soma de Riemann\nf(x) = " + s + " | a = " + a + " | b = " + b + " | Intervalos = " + n + "\n");
            hasTitle = true;
        }

        for (int i = 0; i < n; i++) {
            writer.write("=================================================\n");

            int aux = String.valueOf(i + 1).length();
            int aux1 = 48;
            String s = "Intervalo:";
            if (aux % 2 == 0) {
                s += " ";
            }
            writer.write(String.format("|%s|%n", centerText(s + (i + 1), aux1)));


            aux = format.format(dados[i].getX1()).length() - 2;
            s = "X1:";
            if (aux % 2 != 0) {
                s += " ";
                aux1--;
            }
            writer.write(String.format("|%s|%n", centerText(s + format.format(dados[i].getX1()), aux1)));


            s = format.format(dados[i].getArea());
            aux = s.length() - 2;
            s = "Área parcial:";
            aux1 = 48;
            if (aux % 2 != 0) {
                aux1--;
                s += " ";
            }
            writer.write(String.format("|%s|%n", centerText(s + format.format(dados[i].getArea()), aux1)));


            s = format.format(dados[i].getSoma());
            aux = s.length() - 2;
            s = "Resultado parcial:";
            aux1 = 48;
            if (aux % 2 == 0) {
                aux1--;
                s += " ";
            }
            writer.write(String.format("|%s|%n", centerText(s + format.format(dados[i].getSoma()), aux1)));
            writer.write("=================================================\n");
            if(i % 3000 == 0){
                writer.flush();
            }
        }
        writer.flush();
        writer.write("\n\nResultado final: " + dados[n - 1].getSoma());
        writer.close();
        showText();
    }

    private static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text + " ".repeat(Math.max(0, padding));
    }
}