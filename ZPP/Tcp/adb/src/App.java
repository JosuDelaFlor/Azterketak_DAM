import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class App {
    public static void main(String[] args) {
        final String SERVERADDRESS = "127.0.0.1";
        final int PORT = 1234;

        try (
            Socket socket = new Socket(SERVERADDRESS, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        ){
            System.out.println("Connected to server: " + SERVERADDRESS + ":" + PORT);

            String line;
            while ((line = in.readLine()) != null) {
                if (line.equals("QUESTION")) {
                    while (!(line = in.readLine()).equals("END")) {
                        System.out.println(line);
                    }

                    System.out.print("Your answer: ");
                    String answer = consoleInput.readLine();

                    out.println(answer);
                } else if (line.equals("RESULT")) {
                    while (!(line = in.readLine()).equals("END")) {
                        System.out.println(line);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
