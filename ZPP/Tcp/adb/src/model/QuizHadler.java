package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class QuizHadler implements Runnable {
    private final Socket CLIENTSOCKET;
    private final String[][] QUESTIONS;

    public QuizHadler(Socket CLIENTSOCKET, String[][] QUESTIONS) {
        this.CLIENTSOCKET = CLIENTSOCKET;
        this.QUESTIONS = QUESTIONS;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(CLIENTSOCKET.getInputStream()));
            PrintWriter out = new PrintWriter(CLIENTSOCKET.getOutputStream(), true);
        ){
            int score = 0;
            StringBuilder stringBuilder = new StringBuilder();

            for (String[] questions : QUESTIONS) {
                out.println("QUESTION");
                for (int i = 0; i < 4; i++) {
                    out.println(questions[i]);
                }
                out.println("END");

                String clientAnswer = in.readLine();
                if (clientAnswer != null && clientAnswer.equalsIgnoreCase(questions[4])) {
                    score++;
                    stringBuilder.append("Correct: " + questions[0] + "\n");
                } else {
                    stringBuilder.append("Incorrect: " + questions[0] + "\n");
                }
            }

            out.println("RESULT\nScore: " + score + "/" + QUESTIONS.length + "\n" + stringBuilder.toString() + "END");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
