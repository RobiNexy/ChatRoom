package server.controller;

import server.model.User;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;



public class UserManager {
    public static ArrayList<String> onlineUsers= new ArrayList<>();
    public static HashMap<String,User> allUsers= new HashMap<>();
    public static HashMap<String, User> getAllUsers() {
        return allUsers;
    }
    //更新用户登录地址
    public static void updateUser(String name, String todo){
        if(todo.equals("online")){
            allUsers.get(name).setOnline(true);
        }else if(todo.equals("offline")){
            allUsers.get(name).setOnline(false);
        }
    }
    //获得在线用户列表
    public static String[] getOnlineUsers(){
        for(String name:allUsers.keySet()){
            if(!allUsers.isEmpty()){
                if (allUsers.get(name).isOnline()&&!onlineUsers.contains(name)){
                    onlineUsers.add(name);
                }
            }
        }
        return onlineUsers.toArray(new String[0]);
    }
    //新建用户
    public static void newUser(String name, InetSocketAddress address, boolean online){
        allUsers.put(name,new User(address, online, name));
    }
}
