package controller;


import server.Sender;
import server.controller.UserManager;
import server.model.Errors;
import server.model.Massage;
import server.model.User;


import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;



public class EventHandler {
    private String title;
    private Date date;
    private String stringContent;
    private String sendUserName;
    private String receiveUserName;
    private String fileName;

    public EventHandler(String title, Date date, String content,
                        String receiveUser,String sendUser,String fileName) {
        this.title = title;
        this.date = date;
        this.stringContent=content;
        this.fileName=fileName;
        this.sendUserName=sendUser;
        this.receiveUserName=receiveUser;
        System.out.println(Arrays.toString(UserManager.getOnlineUsers()));
    }

    public void ErrorHandler(){
    }
    //向客户端发送当前在线列表
    public void UpdateOnlineHandler() throws SocketException {
        String[] onlineUsers= UserManager.getOnlineUsers();
        Massage massage=new Massage("UpdateOnlineUsers",date,
                Arrays.toString(onlineUsers),"null","null",UserManager.getAllUsers().get(sendUserName).getName());
        String sMassage=massage.getMassageString();
        Sender sender=new Sender(UserManager.getAllUsers().get(sendUserName).getAddress(),sMassage.getBytes());
        System.out.println(UserManager.getAllUsers().get(sendUserName).getAddress());
        sender.start();
    }
    //转发消息
    public void MassageHandler() throws SocketException {
        Massage massage=new Massage("Massages",date,
                stringContent,"null",sendUserName,receiveUserName);
        String sMassage=massage.getMassageString();
        Sender sender=new Sender(UserManager.getAllUsers().get(receiveUserName).getAddress(),sMassage.getBytes());
        sender.start();
    }
    //转发文件
    public void FileHandler() throws SocketException {
        Massage massage=new Massage("File",date,
                stringContent,fileName,sendUserName,receiveUserName);
        String sMassage=massage.getMassageString();
        Sender sender=new Sender(UserManager.getAllUsers().get(receiveUserName).getAddress(),sMassage.getBytes());
        sender.start();
    }
    //转发图片
    public void ImageHandler() throws SocketException {
        Massage massage=new Massage("Image",date,stringContent,fileName,sendUserName,receiveUserName);
        String sMassage=massage.getMassageString();
        Sender sender=new Sender(UserManager.getAllUsers().get(receiveUserName).getAddress(),sMassage.getBytes());
        sender.start();
    }
    //用户上下线
    public void UserEventHandler(String option) throws SocketException {
        String name=this.sendUserName;
        Date time=this.date;
        String[] addr= fileName.split(":");
        InetSocketAddress address=new InetSocketAddress(addr[0],Integer.parseInt(addr[1]));
        switch (option){
            case "UserLogin":
                if(UserManager.getAllUsers().get(name)!=null){
                    UserManager.updateUser(name,"online");
                    System.out.println(time.toString()+" User: "+name+" logged in at:"+ address);
                }else {
                    //发送未注册错误
                    Massage massage=new Massage("Error",date,
                            Errors.UNSIGN_UP_USER.getErrorMassage(),"null","null",sendUserName);
                    String sMassage=massage.getMassageString();
                    Sender sender=new Sender(UserManager.getAllUsers().get(sendUserName).getAddress(),sMassage.getBytes());
                    sender.start();
                }
                break;
            case "UserSignUp":
                if(UserManager.getAllUsers().get(name)!=null){
                    //发送重名警告
                    Massage massage=new Massage("Error",date,
                            Errors.NAME_BEEN_TAKEN.getErrorMassage(),"null","null",sendUserName);
                    String sMassage=massage.getMassageString();
                    Sender sender=new Sender(UserManager.getAllUsers().get(sendUserName).getAddress(),sMassage.getBytes());
                    sender.start();
                }else {
                    UserManager.newUser(name,address,true);
                    System.out.println(time.toString()+" User:"+name+" signed up at "+ address);
                }
                break;
            case "UserOffline":
                UserManager.updateUser(name,"offline");
                UserManager.onlineUsers.remove(name);
                System.out.println(time.toString()+" User:"+name+" logged out");
                break;

        }
    }
    //群发消息
    public void GroupMassageHandler() throws SocketException {
        String receiveUsers=receiveUserName;
        String finalReceiver=receiveUsers.substring(1,receiveUsers.length()-1);
        System.out.println(finalReceiver);
        String[] receivers=finalReceiver.split(",");
        for (String r:receivers){
            String noblank=r.trim();
            if(noblank != "null"){
                Massage massage=new Massage("Massages",date,
                        stringContent,"null",sendUserName,"NULL");
                String sMassage=massage.getMassageString();
                System.out.println(UserManager.getAllUsers().get(noblank).getAddress());
                Sender sender=new Sender(UserManager.getAllUsers().get(noblank).getAddress(),sMassage.getBytes());
                sender.start();
            }else {
                break;
            }
        }
    }
}
