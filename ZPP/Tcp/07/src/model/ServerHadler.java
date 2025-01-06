package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import model.base.User;

public class ServerHadler implements Runnable {
    private final Socket CLIENTSOCKET;
    private int attempts = 1, rnumber = 0;

    public ServerHadler(Socket cLIENTSOCKET) {
        CLIENTSOCKET = cLIENTSOCKET;
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
            
            Random random = new Random();
            rnumber = random.nextInt(100);

            int choice = 0;
            do {
                out.println("WAITING_TO_ANSWER");
                try {
                    choice = Integer.parseInt(in.readLine());
                    out.println(contest(choice));

                    out.println("WRITING_ANSWER");
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } while (choice != rnumber && attempts < 7);

            out.println("SCORE");
            out.println(user.getName() + ", " + user.getSurname() + ": Score--> " + getScore());

            CLIENTSOCKET.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private String contest(int n) {
        if (n < rnumber) {
            attempts++;
            return "<";
        } else if (n > rnumber) {
            attempts++;
            return ">";
        }

        return "=";
    }

    private int getScore() {
        int n = 0;

        switch (attempts) {
            case 1 -> n = 6;
            case 2 -> n = 5;
            case 3 -> n = 4;
            case 4 -> n = 3;
            case 5 -> n = 2;
            case 6 -> n = 1;
        }

        return n;
    }
}
