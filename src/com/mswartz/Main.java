package com.mswartz;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Main {

    public static void main(String[] args) {

        UI userInterface = new UI();
        Session activeSession = new Session();
        userInterface.greetUser();
        userInterface.mainMenu();

        // Session with user for this service

        // User with stored data and keychain stuff.

        // Menu class to read prompts etc

        // Log user out if inactive
    }
}
