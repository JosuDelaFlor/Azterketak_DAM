import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import model.QuizHadler;

public class Server {
    private static final String[][] QUESTIONS = {
        {"Which of the following is a connection-oriented transport protocol?", "a) TCP", "b) UDP", "c) IP", "a"},
        {"What type of applications benefit most from the UDP protocol?", "a) File transfer", "b) Video conferencing and voice transmission", "c) Web browsing", "b"},
        {"What is the function of the \"Acknowledgment\" (ACK) field in the TCP header?", "a) Confirming data delivery", "b) Establishing the initial connection", "c) Managing routing", "a"},
        {"Which of the following is a connectionless transport protocol?", "a) TCP", "b) UDP", "c) FTP", "b"},
        {"What type of service does TCP offer compared to UDP?", "a) Unreliable service", "b) Reliable and connection-oriented service", "c) Broadcast-oriented service", "b"}
    };

    private static final int PORT = 1234, MAXCLIENTS = 99;

    public static void main(String[] args) {
        int currentClients = 0;
        
        try (DatagramSocket serverSocket = new DatagramSocket(PORT)){
            while (currentClients < MAXCLIENTS) {
                byte[] buffer = new byte[1024];
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(request);
                System.out.println("Client connected: " + request.getAddress() + ":" + request.getPort());

                new Thread(new QuizHadler(serverSocket, request, QUESTIONS)).start();
                currentClients++;
            }

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
