import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;

import model.Udp;

public class App {
    public static void main(String[] args) {
        final String SERVERADDRESS = "127.0.0.1";
        final int PORT = 1234;

        try (
            DatagramSocket clientSocket = new DatagramSocket();
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        ){
            System.out.println("Connected to server: " + SERVERADDRESS + ":" + PORT);
            InetAddress serverInetAddress = InetAddress.getByName(SERVERADDRESS);

            String line;
            while ((line = Udp.receiveMessage(clientSocket)) != null) {
                if (line.startsWith("QUESTION")) {
                    System.out.println(line.replace("QUESTION\n", ""));
                    System.out.print("Your answer: ");
                    String answer = consoleInput.readLine();

                    Udp.sendMessage(answer, clientSocket, serverInetAddress, PORT);
                } else if (line.startsWith("RESULT")) {
                    System.out.println(line.replace("RESULT\n", ""));
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
