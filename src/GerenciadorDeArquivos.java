import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class GerenciadorDeArquivos {
    private final File desktop = new File(FileSystemView.getFileSystemView().getHomeDirectory().toString());//Área de trabalho
    private final File folder = new File(desktop, "Calculo 2 Riemann - resultados");//Pasta de arquivos
    private final File txt = getTxtFile();

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
}