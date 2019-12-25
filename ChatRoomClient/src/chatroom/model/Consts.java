package chatroom.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Consts {
    public static String[] serverAddress;
    public static String[] localAddress = new String[]{};


    public static String getLocalAddress() {
        return localAddress[0] + ":" + localAddress[1];
    }

    public static ObservableList<String> friendData = FXCollections.observableArrayList();

    public static ObservableList<String> getFriendData() {
        return friendData;
    }

    public static ObservableList<String> chatMassage = FXCollections.observableArrayList();

    public static String setSendMassage(String massage, String time, String name) {
        return "<-" + "[" + time + "] " + "你向【" + name + "】发送了： " + massage;
    }

    public static String setReceiveMassage(String massage, String time, String name) {
        return "->" + "[" + time + "] " + name + "向你发送了： " + massage;
    }

    public static ObservableList<String> fileList = FXCollections.observableArrayList();

    public static String setSendFile(String fileName, String time, String name){
        return "<-" + "[" + time + "] " + "你向【" + name + "】发送了文件： " + fileName;
    }
    public static String setReceiveFile(String fileName, String time, String name) {
        return "->" + "[" + time + "] " + name + "向你发送了文件： " + fileName+" 请查收";
    }
    public static ObservableList<String> imageList = FXCollections.observableArrayList();
    public static String setSendImage(String fileName, String time, String name){
        return "<-" + "[" + time + "] " + "你向【" + name + "】发送了图片： " + fileName;
    }
    public static String setReceiveImage(String fileName, String time, String name) {
        return "->" + "[" + time + "] " + name + "向你发送了图片： " + fileName+" 请查收";
    }
    public static ObservableList<String> groupList = FXCollections.observableArrayList();
    public static void updateGroupList(String name){
        if (!groupList.contains(name)){
            groupList.add(name);
        }
    }
}
