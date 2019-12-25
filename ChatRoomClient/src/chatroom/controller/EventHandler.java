package chatroom.controller;

import chatroom.MainApp;
import chatroom.Sender;
import chatroom.model.Consts;
import chatroom.model.Massage;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.SocketException;
import java.util.Base64;
import java.util.Date;


public class EventHandler {
    private String title;
    private Date date;
    private String stringContent;
    private String sendUser;
    private String receiveUser;
    private String fileName;


    public EventHandler(String title, Date date, String content,
                        String receiveUser, String sendUser, String fileName) {
        this.title = title;
        this.date = date;
        this.stringContent = content;
        this.fileName = fileName;
        this.sendUser = sendUser;
        this.receiveUser = receiveUser;
    }

    //处理服务器发来的错误信息
    public void ErrorHandler() {
        Platform.runLater(() -> {
            //弹窗
            Alert alert = new Alert(Alert.AlertType.ERROR);
            switch (stringContent) {
                case "NAME_BEEN_TAKEN":
                    alert.initOwner(MainApp.getPrimaryStage());
                    alert.setTitle("ERROR SIGN UP");
                    alert.setHeaderText("Please choose another name");
                    alert.setContentText("USER_NAME_HAS_BEEN_TAKEN");
                    alert.showAndWait();
                    break;
                case "UNSIGN_UP_USER":
                    alert.initOwner(MainApp.getPrimaryStage());
                    alert.setTitle("ERROR SIGN IN");
                    alert.setHeaderText("Please sign up first");
                    alert.setContentText("UNSIGNED_USERNAME");
                    alert.showAndWait();
                    break;
            }
        });
    }
    //刷新在线列表并显示出来
    public void RefreshOnlineHandler() {
        String s = stringContent.substring(1, stringContent.length() - 1);
        String[] onlineUsers = s.split(",");
        for (String name : onlineUsers) {
            if (!Consts.friendData.contains(name)) {
                Consts.friendData.add(name);
            }
        }
        System.out.println("received friendlist");
    }
    //收到文件，解码后放到用户文件夹
    public void FileHandler() throws IOException {
        String senderName = sendUser;
        String sfile= stringContent;
        String nameOfFile=fileName;
        String time = date.toString();
        File receiveFile = new File("/home/robin/ChatroomFile/"+MainApp.you+"'s File/"+nameOfFile);
        OutputStream fos = new FileOutputStream(receiveFile);
        fos.write(sfile.getBytes());
        fos.close();
        String line = Consts.setReceiveFile(nameOfFile, time, senderName);
        Consts.chatMassage.add(line);
    }

    //收到图片后解码图片，放到用户目录
    public void ImageHandler() throws IOException {
        String senderName = sendUser;
        String sfile= stringContent;
        String nameOfFile=fileName;
        String time = date.toString();
        File receiveFile = new File("/home/robin/ChatroomFile/"+MainApp.you+"'s File/"+nameOfFile);
        String kind=nameOfFile.substring(nameOfFile.lastIndexOf(".")+1);
        Base64.Decoder decoder=Base64.getDecoder();
        byte[] imagebb=decoder.decode(sfile);
        ByteArrayInputStream bais=new ByteArrayInputStream(imagebb);
        BufferedImage o= ImageIO.read(bais);
        ImageIO.write(o,kind,receiveFile);
        bais.close();
        String line = Consts.setReceiveImage(nameOfFile, time, senderName);
        Consts.chatMassage.add(line);
    }

    //显示收到的信息
    public void MassageHandler() throws SocketException {
        String senderName = sendUser;
        String massage = stringContent;
        String time = date.toString();
        String line = Consts.setReceiveMassage(massage, time, senderName);
        Consts.chatMassage.add(line);
    }

    //显示收到的群发消息
    public void GroupMassageHandler() {
        String senderName = sendUser;
        String massage = stringContent;
        String time = date.toString();
        String line = Consts.setReceiveMassage(massage, time, senderName);
        Consts.chatMassage.add(line);
    }
}
