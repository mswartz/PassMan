package com.mswartz;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.password.BasicPasswordEncryptor;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

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

//                if( activeSession.logUserIn(userName, passWord) ){
//                    this.appMenu(activeSession);
//                }

                //load properties file for user
                try {
                    File file = new File(userName+".properties");
                    FileInputStream fileInput = new FileInputStream(file);
                    Properties properties = new Properties();
                    properties.load(fileInput);
                    fileInput.close();

                    Enumeration enuKeys = properties.keys();
                    while (enuKeys.hasMoreElements()) {
                        String key = (String) enuKeys.nextElement();
                        String value = properties.getProperty(key);
                        terminal.printf(key + ": " + value);
                    }
                } catch (FileNotFoundException e) {
                    // if user does not exist, ask them to make a new one.
                    terminal.printf("No user found, try creating a new user");
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Create user":

                StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

                String newUserName = textIO.newStringInputReader()
                        .read("Username");

                String newPassWord = textIO.newStringInputReader()
                        .withInputMasking(true)
                        .read("Password");
                encryptor.setPassword(newPassWord);

                BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
                String encryptedPassword = passwordEncryptor.encryptPassword("password");
                terminal.printf(encryptedPassword);

                if (passwordEncryptor.checkPassword(newPassWord, encryptedPassword)) {
                    terminal.printf("thats correct");
                } else {
                    terminal.printf("shit nope");
                }

                String encryptedText = encryptor.encrypt(newPassWord);
                System.out.println("Encrypted text is: " + encryptedText);

                StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();

                try {
                    File file = new File(newUserName+".properties");
                    FileInputStream fileInput = new FileInputStream(file);
                    Properties properties = new Properties();
                    properties.load(fileInput);
                    fileInput.close();

                    // if user is taken, don't let them make a new one.
                    terminal.printf("Sorry, that user is already taken.");

                } catch (FileNotFoundException e) {
                    try {
                        Properties properties = new Properties();
                        properties.setProperty("password", encryptedPassword);
                        properties.setProperty("username", newUserName);

                        File file = new File(newUserName+".properties");
                        FileOutputStream fileOut = new FileOutputStream(file);
                        properties.store(fileOut, "Properties File");
                        fileOut.close();
                    } catch (FileNotFoundException e2) {
                        e2.printStackTrace();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

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