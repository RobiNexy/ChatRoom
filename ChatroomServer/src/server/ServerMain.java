package server;

import server.controller.UserManager;

import java.net.SocketException;


public class ServerMain {
    public static void main(String[] args) throws SocketException {
        ServerListener serverListener=new ServerListener();
        serverListener.start();
    }
}
