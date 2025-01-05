package model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class QuizHadler implements Runnable {
    private final DatagramSocket SOCKET;
    private final DatagramPacket REQUEST;
    private final String[][] QUESTIONS;

    public QuizHadler(DatagramSocket SOCKET, DatagramPacket REQUEST, String[][] QUESTIONS) {
        this.SOCKET = SOCKET;
        this.REQUEST = REQUEST;
        this.QUESTIONS = QUESTIONS;
    }

    @Override
    public void run() {
        try {
            int score = 0;

            StringBuilder feedBack = new StringBuilder();

            for (String[] questions : QUESTIONS) {
                StringBuilder questionData = new StringBuilder("QUESTION\n");
                for (int i = 0; i < 4; i++) {
                    questionData.append(questions[i] + "\n");
                }
                Udp.sendMessage(questionData.toString(), SOCKET, REQUEST.getAddress(), REQUEST.getPort());
                
                String clientAnswer = Udp.receiveMessage(SOCKET);
                if (clientAnswer != null && clientAnswer.equalsIgnoreCase(questions[4])) {
                    score++;
                    feedBack.append("Correct: " + questions[0] + "\n");
                } else {
                    feedBack.append("Incorrect: " + questions[0] + "\n");
                }
            }

            String result = "RESULT\nScore: " + score + "/" + QUESTIONS.length + "\n" + feedBack.toString() + "END";
            Udp.sendMessage(result, SOCKET, REQUEST.getAddress(), REQUEST.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
