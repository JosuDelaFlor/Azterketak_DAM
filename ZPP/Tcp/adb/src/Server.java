import java.net.ServerSocket;
import java.net.Socket;

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

        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            while (currentClients < MAXCLIENTS) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

                new Thread(new QuizHadler(clientSocket, QUESTIONS)).start();
                currentClients++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
