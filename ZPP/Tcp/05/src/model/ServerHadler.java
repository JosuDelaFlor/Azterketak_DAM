package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import model.base.User;

public class ServerHadler implements Runnable {
    private final List<String> QUESTIONS, ANSWERS;
    private final Socket CLIENTSOCKET;

    public ServerHadler(Socket CLIENTSOCKET, List<String> QUESTIONS, List<String> ANSWERS) {
        this.CLIENTSOCKET = CLIENTSOCKET;
        this.QUESTIONS = QUESTIONS;
        this.ANSWERS = ANSWERS;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(CLIENTSOCKET.getInputStream()));
            PrintWriter out = new PrintWriter(CLIENTSOCKET.getOutputStream(), true);
            ObjectInputStream objectIn = new ObjectInputStream(CLIENTSOCKET.getInputStream());
        ){
            User user = (User) objectIn.readObject();
            System.out.println("Connected user: " + user + "\n");

            int choice = 1;
            do {
                out.println("QUESTION");
                for (int i = 0; i < QUESTIONS.size(); i++) {
                    out.println("(" + (i + 1) + ")" + QUESTIONS.get(i));
                }
                out.println("ENDQUESTION");

                try {
                    choice = Integer.parseInt(in.readLine());

                    switch (choice) {
                        case 1 -> out.println("\n" + ANSWERS.get(0) + "\n");
                        case 2 -> out.println("\n" + ANSWERS.get(1) + "\n");
                        case 3 -> out.println("\n" + ANSWERS.get(2) + "\n");
                        case 4 -> out.println("\n" + ANSWERS.get(3) + "\n");
                        case 5 -> out.println("\n" + ANSWERS.get(4) + "\n");
                        case 0 -> out.println("Agur!!!");
                        default -> out.println("Bakarrik (0-5)");
                    }

                    out.println("ENDCHOICE");
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } while (choice != 0);
            
            CLIENTSOCKET.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
