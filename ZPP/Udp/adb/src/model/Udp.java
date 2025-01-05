package model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Udp {
    public static void sendMessage(String message, DatagramSocket socket, InetAddress address, int port) throws IOException {
        byte[] buffer = message.getBytes();
        DatagramPacket request = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(request);
    }

    public static String receiveMessage(DatagramSocket socket) throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        socket.receive(response);
        
        return new String(response.getData(), 0, response.getLength()).trim();
    }
}
