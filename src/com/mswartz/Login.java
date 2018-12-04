package com.mswartz;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.time.Month;
import java.util.ArrayList;

public class Login {
    private String serviceName;
    private String userName;
    private String passWord;

    Login(){}

    Login(String serviceName, String userName, String passWord){
        this.serviceName = serviceName;
        this.userName = userName;
        this.passWord = passWord;
    }

    public void generatePassword(){

    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
