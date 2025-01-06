import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import model.base.User;

public class App {
    public static void main(String[] args) {
        final String SERVERADDRESS = "127.0.0.1";
        final int PORT = 1234;

        try (
            Socket socket = new Socket(SERVERADDRESS, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
            ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
        ){
            System.out.println("Connected to server: " + SERVERADDRESS + ":" + PORT);
            System.out.print("Sartu zure Izena: ");
            String name = consoleInput.readLine();
            System.out.print("Sartu zure Abizena: ");
            String surname = consoleInput.readLine();

            User user = new User(name, surname);
            objectOut.writeObject(user);
            objectOut.flush();

            String line;
            while ((line = in.readLine()) != null) {
                if (line.equals("WAITING_TO_ANSWER")) {
                    System.out.print("Sartu zenbaki bat (1-100): ");
                    String choice = consoleInput.readLine();
                    out.println(choice);

                    while (!(line = in.readLine()).equals("WRITING_ANSWER")) {
                        System.out.println(line);
                    }
                } else if (!line.equals("SCORE")) {
                    System.out.println(line);
                }
            }

            socket.close();
        } catch (IOException e) {
            System.err.println("Errore bat egon da, zerbitzaria konektatzerakoan: " + e.getMessage());
        }
    }
}
