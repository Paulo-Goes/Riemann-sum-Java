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
    private BufferedWriter writer;
    private DecimalFormat formato;

    public GerenciadorDeArquivos() throws IOException {
        desktop = new File(FileSystemView.getFileSystemView().getHomeDirectory().toString());
        folder = new File(desktop, "Calculo 2 Riemann - resultados");
        txt = getTxtFile();
        writer = new BufferedWriter(new FileWriter(txt));
        formato = new DecimalFormat("#.################");
    }

    public void createFolder() throws InterruptedException { //Cria a pasta de respostas caso não exista
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

    private File getTxtFile() { //Cria um novo documento de texto
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

    public File getTxt() {
        return txt;
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

    private static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text + " ".repeat(Math.max(0, padding));
    }
}