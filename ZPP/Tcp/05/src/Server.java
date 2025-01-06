import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import model.ServerHadler;
import model.base.Question;

public class Server {
    private static final List<String> QUESTIONS = Question.getQuestions(), ANSWERS = Question.getAnswers();
    private static final int PORT = 1234, MAXCLIENTS = 99;

    public static void main(String[] args) {
        int currentClients = 0;

        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            while (currentClients < MAXCLIENTS) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

                currentClients++;
                new Thread(new ServerHadler(clientSocket, QUESTIONS, ANSWERS)).start();
            }

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
