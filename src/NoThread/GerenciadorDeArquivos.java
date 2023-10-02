package NoThread;

import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class GerenciadorDeArquivos {
    private final File desktop = new File(FileSystemView.getFileSystemView().getHomeDirectory().toString());
    private final File folder = new File(desktop, "Calculo 2 Thread.Riemann - resultados");
    private final File txt = getTxtFile();

    public void createFolder() throws InterruptedException {
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

    public File getTxt() {
        return txt;
    }
}