package com.mswartz;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Main {

    public static void main(String[] args) {

        UI userInterface = new UI();
        Session activeSession = new Session();
        userInterface.greetUser();
        userInterface.mainMenu();
        // Log user out if inactive
    }
}
