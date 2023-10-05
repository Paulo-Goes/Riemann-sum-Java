package NoThread;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GerenciadorDeArquivos {
    private final File desktop = new File(FileSystemView.getFileSystemView().getHomeDirectory().toString());
    private final File folder = new File(desktop, "Calculo 2 Riemann - resultados");


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

    public File getTxtFile(int op, String s) {
        String nome = "";

        if (op == 1) {
            nome += "Esquerda";
        }
        if (op == 2) {
            nome += "Meio";
        }
        if (op == 3) {
            nome += "Direita";
        }

        nome += " f(x) = " + s + " " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yy hh-mm-ss")) + ".txt";
        return new File(folder, nome);
    }
}