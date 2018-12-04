package com.mswartz;

public class User {
    private String userName;

    //default Constructor
    public User() {}

    public User(String userName) {
        userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}