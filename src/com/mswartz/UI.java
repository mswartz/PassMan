package com.mswartz;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class UI {
    TextIO textIO = TextIoFactory.getTextIO();
    TextTerminal terminal = textIO.getTextTerminal();

    public static enum OPTIONS {
        LOGIN, NEWUSER
    }

    public void greetUser(){

        terminal.printf(
                "  ___  _   ___ ___ __  __   _   _  _ \n" +
                " | _ \\/_\\ / __/ __|  \\/  | /_\\ | \\| |\n" +
                " |  _/ _ \\\\__ \\__ \\ |\\/| |/ _ \\| .` |\n" +
                " |_|/_/ \\_\\___/___/_|  |_/_/ \\_\\_|\\_|\n" +
                "                                     \n");

        terminal.printf("Version 0.1 🔥\n");

    }

    public void mainMenu(Session activeSession){
        String menuChoice = textIO.newStringInputReader()
                .withNumberedPossibleValues("Login", "Create user")
                .read("Login");

        switch (menuChoice) {
            case "Login":
                String userName = textIO.newStringInputReader()
                        .read("Username");
                String passWord = textIO.newStringInputReader()
                        .withInputMasking(true)
                        .read("Password");
                if( activeSession.logUserIn(userName, passWord) ){
                    this.appMenu(activeSession);
                }
                break;
            case "Create user":
                terminal.printf("New user now!");
                StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

                String newPassWord = textIO.newStringInputReader()
                        .withInputMasking(true)
                        .read("Password");
                encryptor.setPassword(newPassWord);

                String encryptedText = encryptor.encrypt(newPassWord);
                System.out.println("Encrypted text is: " + encryptedText);

                StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();

                decryptor.setPassword(newPassWord);
                String decryptedText = decryptor.decrypt(encryptedText);
                System.out.println("Decrypted text is: " + decryptedText);
                break;
                default :
                    terminal.printf("No choice made");
                    break;
        }
    }

    public void appMenu(Session activeSession){
        String menuChoice = textIO.newStringInputReader()
                .withNumberedPossibleValues("Find login", "Add new login")
                .read();

        switch (menuChoice) {
            case "Find login":
                terminal.printf("all the passwords");
                break;
            case "Add new login":
                terminal.printf("A new login");
        }
    }
}