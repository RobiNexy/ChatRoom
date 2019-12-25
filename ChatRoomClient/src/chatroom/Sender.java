package chatroom;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class Sender extends Thread {
    public DatagramSocket sendSocket = new DatagramSocket();
    private String[] sendAddress;
    private byte[] sendData;

    public Sender(String[] sendAddress, byte[] data) throws SocketException {
        this.sendAddress = sendAddress;
        this.sendData = data;
    }

    @Override
    public void run() {
        try {
            DatagramPacket receivePacket = new DatagramPacket(sendData, sendData.length,
                    new InetSocketAddress(sendAddress[0], Integer.parseInt(sendAddress[1])));
            sendSocket.send(receivePacket);
            sendSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
