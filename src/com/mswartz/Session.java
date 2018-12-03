package com.mswartz;

public class Session {
    public User activeUser = new User();
    public String sessionStatus;
    public List keychain = new List();

    public Session () {}

    public Session(String activeUser){
        sessionStatus = "active";
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public String getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(String sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public List getKeychain() {
        return keychain;
    }

    public void setKeychain(List keychain) {
        this.keychain = keychain;
    }
}
