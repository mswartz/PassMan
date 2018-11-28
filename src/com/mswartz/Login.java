package com.mswartz;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.time.Month;

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

    public void createNewLogin(){
        // build the login here
        TextIO textIO = TextIoFactory.getTextIO();

        String serviceName = textIO.newStringInputReader()
                .read("Service name");


        String userName = textIO.newStringInputReader()
                .withDefaultValue("admin")
                .read("Username");

        String passWord = textIO.newStringInputReader()
                .withMinLength(6)
                .withInputMasking(true)
                .read("Password");


        TextTerminal terminal = textIO.getTextTerminal();
        terminal.printf("\nGreat, building a new login for\n"+userName);
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
