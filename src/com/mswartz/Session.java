package com.mswartz;

public class Session {
    User activeUser = new User();

    public boolean logUserIn(String userName, String passWord) {
        if(!userName.isEmpty()){
            return true;
        } else {
            return false;
        }
    }

}
