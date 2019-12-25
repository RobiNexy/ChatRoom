package server.model;

import java.net.InetSocketAddress;

public class User {
    private InetSocketAddress address;
    private boolean Online;
    private String name;
    public User(InetSocketAddress address, boolean online, String name) {
        this.address = address;
        Online = online;
        this.name = name;
    }
    public InetSocketAddress getAddress() {
        return address;
    }
    public boolean isOnline() {
        return Online;
    }
    public User setOnline(boolean online) {
        Online = online;
        return this;
    }
    public String getName() {
        return name;
    }
}
