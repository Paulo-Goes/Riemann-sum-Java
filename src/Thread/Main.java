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

import static java.lang.Character.isDigit;

/*
 * Recomenda-se usar um editor de texto de terceiros:
 * Sublime Text
 * Notepad++
 * EmEditor
 * Vim
 * nano
 * UltraEdit
 * Geany
 * ...
 *
 * Documento de texto so funciona se a letra tiver os seguintes parâmetros:
 * Fonte: Consolas
 * Estilo: Regular
 * Tamanho: 11
 * Escrita: Ocidental
 */

public class Main {
    private static boolean enable1, enable2, enable3;

    public static double function(double x) {
        return Math.pow(x, 2);//x^2
    }

    private static String functionString() {
        return "x^2";
    }

    public static void main(String[] args) throws InterruptedException, IOException, AWTException {
        File desktop = new File(FileSystemView.getFileSystemView().getHomeDirectory().toString());
        File folder = new File(desktop, "Calculo 2 Riemann - resultados");
        createFolder(folder);

        Function<Double, Double> function = Main::function;
        String s = functionString();

        Thread t1 = null, t2 = null, t3 = null;

        setOp();

        String nome = "";

        if (enable1 && enable2 && enable3) {
            nome = "Todos";
        } else {
            if (enable1) {
                nome += "Esquerda";
            }
            if (enable2) {
                if (!nome.isEmpty()) {
                    nome += ", ";
                }
                nome += "Meio";
            }
            if (enable3) {
                if (!nome.isEmpty()) {
                    nome += ", ";
                }
                nome += "Direita";
            }
        }

        nome += " f(x) = " + s + " " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yy hh-mm-ss"));

        File subFolder = new File(folder, nome);
        createFolder(subFolder);

        double a = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite inferior da integral: "));
        double b = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite superior da integral: "));

        int n = getInterval();

        long time = System.currentTimeMillis();

        if (enable1) {
            t1 = new Thread(new Riemann(function, s, a, b, n, 1, subFolder));
            t1.start();
        }

        if (enable2) {
            t2 = new Thread(new Riemann(function, s, a, b, n, 2, subFolder));
            t2.start();
        }

        if (enable3) {
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

        System.out.println((System.currentTimeMillis() - time) / 1000);
    }

    private static int getInterval() {
        int n = Integer.parseInt(JOptionPane.showInputDialog("Digite o número de intervalos (acima de 1): "));
        while (n <= 1) {
            n = Integer.parseInt(JOptionPane.showInputDialog("Digite o número de intervalos (acima de 1): "));
        }
        return n;
    }

    private static boolean isFormatted(String input) {
        if (input.isEmpty()) return false;
        for (int i = 0; i < input.length(); i++) {
            if (!isDigit(input.charAt(i))) {
                return false;
            } else {
                int aux = Integer.parseInt(String.valueOf(input.charAt(i)));
                if (aux >= 4 || aux == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void setOp() {
        String op = JOptionPane.showInputDialog("1 - Esquerda / 2 - Meio / 3 - Direita (1,2,3)(1,3)").replace(" ", "");

        op = op.replace(" ", "");
        op = op.replace(",", "");

        while (!isFormatted(op)) {
            op = JOptionPane.showInputDialog("1 - Esquerda / 2 - Meio / 3 - Direita (1,2,3)(1,3)");
            op = op.replace(" ", "");
            op = op.replace(",", "");
        }

        for (int i = 0; i < op.length(); i++) {
            int aux = Integer.parseInt(String.valueOf(op.charAt(i)));
            if (aux == 1) {
                enable1 = true;
            }
            if (aux == 2) {
                enable2 = true;
            }
            if (aux == 3) {
                enable3 = true;
            }
        }
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