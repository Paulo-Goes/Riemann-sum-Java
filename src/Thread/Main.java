package Thread;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

/*
 * Documento de texto so funciona se notepad.exe tiver os seguintes parâmetros:
 * Fonte: Consolas
 * Estilo: Regular
 * Tamanho: 11
 * Escrita: Ocidental
 */

public class Main {
    /*
     * Todo:
     * Criar uma tela para selecionar quais tipos de soma para fazer
     */
    public static double function(double x) {
        return Math.pow(x, 2);//x^2
    }

    private static String functionString() {
        return "x^2";
    }

    public static void main(String[] args) throws InterruptedException, IOException, AWTException {
        long time = System.currentTimeMillis();
        File desktop = new File(FileSystemView.getFileSystemView().getHomeDirectory().toString());
        File folder = new File(desktop, "Calculo 2 Riemann - resultados");
        createFolder(folder);

        Function<Double, Double> function = Main::function;
        String s = functionString();

        Thread t1 = null, t2 = null, t3 = null;

        int op = getOp();

        String nome = "Esquerda";

        if (op == 2) {
            nome = "Meio";
        }

        if (op == 3) {
            nome = "Direita";
        }

        if (op == 4) {
            nome = "Todos";
        }

        nome += " f(x) = " + s + " " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yy hh-mm-ss"));

        File subFolder = new File(folder, nome);
        createFolder(subFolder);

        double a = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite inferior da integral: "));
        double b = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite superior da integral: "));

        int n = getInterval();

        if (op >= 1) {
            t1 = new Thread(new Riemann(function, s, a, b, n, 1, subFolder));
            t1.start();
        }

        if (op >= 2) {
            t2 = new Thread(new Riemann(function, s, a, b, n, 2, subFolder));
            t2.start();
        }

        if (op >= 3) {
            t3 = new Thread(new Riemann(function, s, a, b, n, 3, subFolder));
            t3.start();
        }

        if (t1 != null) {
            t1.join();
        }
        if (t2 != null) {
            t2.join();
        }
        if (t3 != null) {
            t3.join();
        }
        showFolder(subFolder);

        System.out.println((System.currentTimeMillis() - time)/1000);
    }

    private static int getInterval() {
        int n = Integer.parseInt(JOptionPane.showInputDialog("Digite o número de intervalos (acima de 1): "));
        while (n <= 1) {
            n = Integer.parseInt(JOptionPane.showInputDialog("Digite o número de intervalos (acima de 1): "));
        }
        return n;
    }

    private static int getOp() {
        int op = Integer.parseInt(JOptionPane.showInputDialog("1 - Esquerda / 2 - Meio / 3 - Direita / 4 - Todos"));

        while (op > 4 || op < 1) {
            op = Integer.parseInt(JOptionPane.showInputDialog("1 - Esquerda / 2 - Meio / 3 - Direita / 4 - Todos"));
        }
        return op;
    }

    private static void createFolder(File folder) {
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
    }

    public static void showFolder(File subFolder) throws IOException, InterruptedException, AWTException {
        ProcessBuilder pb = new ProcessBuilder("explorer.exe", subFolder.getAbsolutePath());
        pb.start();

        Thread.sleep(1000);

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyPress(KeyEvent.VK_UP);
        robot.keyRelease(KeyEvent.VK_UP);
        robot.keyRelease(KeyEvent.VK_WINDOWS);

        System.out.println("Maximizado");
    }
}