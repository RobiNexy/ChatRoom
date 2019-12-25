package chatroom.controller;

import chatroom.model.Massage;
import chatroom.view.LoginController;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.text.ParseException;
import java.util.Date;

public class PacketAnalyser extends Thread {
    //收到的包
    private DatagramPacket packet;

    public PacketAnalyser(DatagramPacket packet) {
        this.packet = packet;
    }

    @Override
    public void run() {
        byte[] data = packet.getData();
        String massageJSON = new String(data).trim();
        Massage massage = null;
        try {
            massage = Massage.getMassage(massageJSON);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String title = massage.getTitle();
        String content = massage.getContent();
        Date date = massage.getDate();
        String sendUserName = massage.getSendUser();
        String receiveUserName = massage.getReceiveUser();
        String fileName = massage.getFileName();
        //处理函数
        EventHandler handler = new EventHandler(title, date, content, receiveUserName,
                sendUserName, fileName);
        switch (title) {
            case "Error":
                LoginController.noError=false;
                handler.ErrorHandler();
                break;
            case "UpdateOnlineUsers":
                handler.RefreshOnlineHandler();
                break;
            case "Massages":
                try {
                    handler.MassageHandler();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                break;
            case "GroupMassages":
                handler.GroupMassageHandler();
                break;
            case "Image":
                try {
                    handler.ImageHandler();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "File":
                try {
                    handler.FileHandler();
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
