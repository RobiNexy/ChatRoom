package chatroom;

import chatroom.controller.PacketAnalyser;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class ClientListener extends Thread {
    public DatagramSocket clientSocket;
    public String[] address;

    public ClientListener(String[] addr) throws SocketException {
        this.address = addr;
        this.clientSocket = new DatagramSocket(new InetSocketAddress(address[0], Integer.parseInt(address[1])));
    }

    @Override
    public void run() {
        try {
            byte[] receiveData = new byte[65508];
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                PacketAnalyser handler = new PacketAnalyser(receivePacket);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
