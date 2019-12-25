package server;

import server.controller.PacketAnalyser;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;


public class ServerListener extends Thread{
    public DatagramSocket serverSocket=new DatagramSocket(new InetSocketAddress("localhost", 42052));

    public ServerListener() throws SocketException {
    }
    @Override
    public void run(){
        try{
            byte[] receiveData=new byte[65508];
            while(true){
                DatagramPacket receivePacket=new DatagramPacket(receiveData,receiveData.length);
                serverSocket.receive(receivePacket);
                PacketAnalyser handler= new PacketAnalyser(receivePacket);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
