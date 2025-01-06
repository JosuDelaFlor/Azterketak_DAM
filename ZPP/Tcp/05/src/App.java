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
            System.out.print("Sartu sure izena: ");
            String name = consoleInput.readLine();
            System.out.print("Sartu sure abizena: ");
            String surname = consoleInput.readLine();

            User user = new User(name, surname);
            objectOut.writeObject(user);
            objectOut.flush();

            String line;
            while ((line = in.readLine()) != null) {
                if (line.equals("QUESTION")) {
                    while (!(line = in.readLine()).equals("ENDQUESTION")) {
                        System.out.println(line);
                    }

                    System.out.print("Sartu ekintza(0-5): ");
                    String choice = consoleInput.readLine();
                    out.println(choice);
                } else if (!line.equals("ENDCHOICE")) {
                    System.out.println(line);
                }
            }

            socket.close();
        } catch (IOException e) {
            System.err.println("Errore bat egon da, zerbitzaria konektatzerakoan: " + e.getMessage());
        }
    }
}
