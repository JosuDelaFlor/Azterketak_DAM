package paagbi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.commons.net.ftp.FTPFile;

import paagbi.model.Ftp;

public class Main {
    private static Ftp ftp = new Ftp("Admin", "Admin123", "127.0.0.1", 21);
    private static Scanner in = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        cls();

        try {
            ftp.connect();
            
            int choice = 1;
            do {
                System.out.print("\n"+ftp+"\n==========FTP MENU==========\n1.| Fitxategiak Biztaratu\n2.| Fitxategiak Eskegi\n3.| Fitxategiak Deskargatu\n" +
                    "0.| Atera\nSartu ekintza: ");    
                
                try {
                    choice = in.nextInt();
                    cls();

                    switch (choice) {
                        case 1 -> showFileList();
                        case 2 -> uploadFile();
                        case 3 -> downloadFile();
                        case 0 -> System.out.println("Agur!!!");
                        default -> System.out.println("Bakarrik (0-3)");
                    }
                } catch (InputMismatchException e) {
                    cls();
                    System.err.println("Bakarrik balore numerikoak sartu ahal dituzu");
                    in.nextLine();
                }
            } while (choice != 0);

        } catch (IOException e) {
            cls();
            System.err.println("Arazo bat egon da FTP zerbitzariarekin: " + e.getMessage());
        } finally {
            in.close();
            ftp.disconnect();
        }
    }

    private static void showFileList() {
        try {
            for (FTPFile files : ftp.getListFile()) {
                System.out.println(files.getName() + "\tbytes -> " + files.getSize());
            }
        } catch (IOException e) {
            System.err.println("Arazo bat egon da Fitxategiak ikusterakoan: " + e.getMessage());
        }
    }

    private static void uploadFile() {
        try {
            System.out.print("Sartu igo nahi duzun fitxategiaren bidea(fitxategiaren hedapenarekin): ");
            FileInputStream path = new FileInputStream(in.next().trim());
            System.out.print("Sartu fitxategiaren nahi duzun izena(fitxategiaren hedapenarekin): ");
            String name = in.next().trim();

            if (ftp.uploadFile(path, name)) {
                System.out.println(name + "fitxategia ondo igo da");
            } else {
                System.err.println(name + "fitxategia ezin izan da igo");
            }
        } catch (IOException e) {
            System.err.println("Arazo bat egon da fitxategia igotzean: " + e.getMessage());
        }
    }

    private static void downloadFile() {
        try {
            System.out.print("Sartu deskargatu nahi duzun fitxategiaren izena(fitxategiaren hedapenarekin): ");
            String name = in.next().trim();
            System.out.print("Sartu fitxategiaren nahi duzun kokapen bidea(fitxategiaren hedapenarekin): ");
            FileOutputStream destinationPath = new FileOutputStream(in.next().trim());

            if (ftp.downloadFile(destinationPath, name)) {
                System.out.println(name + "fitxategia ondo deskagatu da");
            } else {
                System.err.println(name + "fitxategia ezin izan da deskargatu");
            }
        } catch (IOException e) {
            System.err.println("Arazo bat egon da fitxategia deskagatzean: " + e.getMessage());
        }
    }

    private static void cls() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}