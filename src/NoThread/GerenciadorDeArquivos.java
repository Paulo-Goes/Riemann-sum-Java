package NoThread;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class GerenciadorDeArquivos {
    private final File desktop = new File(FileSystemView.getFileSystemView().getHomeDirectory().toString());
    private final File folder = new File(desktop, "Calculo 2 Riemann - resultados");
    private final File txt = getTxtFile();


    public File getFolder() {
        return folder;
    }

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

    public File getTxt() {
        return txt;
    }
}