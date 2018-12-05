package com.mswartz;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.time.Month;
import java.util.ArrayList;
import java.util.Random;

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

    public static char[] generatePassword(int length){
            String caps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String lowers = "abcdefghijklmnopqrstuvwxyz";
            String nums = "0123456789";
            String symbols = "!@#$%^&*_=+-/.?<>)";

            String values = caps + lowers + nums + symbols;

            // Using random method
            Random scrambler = new Random();

            char[] password = new char[length];

            for (int i = 0; i < length; i++)
            {
                password[i] = values.charAt(scrambler.nextInt(values.length()));

            }
            return password;
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
