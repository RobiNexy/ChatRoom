package server.controller;

import controller.EventHandler;
import server.model.Massage;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.text.ParseException;
import java.util.Date;

public class PacketAnalyser extends Thread{
    private DatagramPacket packet;

    public PacketAnalyser(DatagramPacket packet) {
        this.packet = packet;
    }
    @Override
    public void run(){
        byte[] data= packet.getData();
        String sMassage=new String(data).trim();
        Massage massage= null;
        try {
            massage = Massage.getMassage(sMassage);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String title=massage.getTitle();
        String content=massage.getContent();;
        Date date=massage.getDate();
        String sendUserName=massage.getSendUser();
        String receiveUserName=massage.getReceiveUser().trim();
        String fileName=massage.getFileName();
        EventHandler handler=new EventHandler(title,date,content,receiveUserName,sendUserName,fileName);
        switch (massage.getTitle()){
            case "Error":
                handler.ErrorHandler();
                break;
            case "RefreshOnlineUsers":
                try {
                    handler.UpdateOnlineHandler();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                break;
            case "Massages":
                try {
                    handler.MassageHandler();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                break;
            case "GroupMassages":
                try {
                    handler.GroupMassageHandler();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                break;
            case "Image":
                try {
                    handler.ImageHandler();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                break;
            case "File":
                try {
                    handler.FileHandler();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                break;
            case "UserLogin":
                try {
                    handler.UserEventHandler("UserLogin");
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                break;
            case "UserSignUp":
                try {
                    handler.UserEventHandler("UserSignUp");
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                break;
            case "UserOffline":
                try {
                    handler.UserEventHandler("UserOffline");
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
