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
    BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
    Properties properties = new Properties();
    String encryptedPassword;

    private boolean sessionStarted = false;
    private boolean userFound = false;

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
        do {
            String menuChoice = textIO.newStringInputReader()
                    .withNumberedPossibleValues("Login", "Create user")
                    .read("Login");

            switch (menuChoice) {
                case "Login":
                    String userName = textIO.newStringInputReader()
                            .read("Username");

//                if( activeSession.logUserIn(userName, passWord) ){
//                    this.appMenu(activeSession);
//                }

                    //load properties file for user
                    try {
                        File file = new File(userName + ".properties");
                        FileInputStream fileInput = new FileInputStream(file);
                        properties.load(fileInput);
                        fileInput.close();

                        userFound = true;

                        Enumeration enuKeys = properties.keys();
                        while (enuKeys.hasMoreElements()) {
                            String key = (String) enuKeys.nextElement();
                            String value = properties.getProperty(key);
                            terminal.printf(key + ": " + value);
                        }

                        encryptedPassword = properties.getProperty("password");
                    } catch (FileNotFoundException e) {
                        // if user does not exist, ask them to make a new one.
                        terminal.printf("No user found, try creating a new user");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(userFound) {
                        String inputPassword = textIO.newStringInputReader()
                                .withInputMasking(true)
                                .read("Password");
                        if (passwordEncryptor.checkPassword(inputPassword, encryptedPassword)) {
                            terminal.printf("correct!");
                        } else {
                            terminal.printf("Incorrect password");
                        }
                    }

                    break;
                case "Create user":

                    String newUserName = textIO.newStringInputReader()
                            .read("Username");

                    String newPassWord = textIO.newStringInputReader()
                            .withInputMasking(true)
                            .read("Password");

                    String newEncryptedPassword = passwordEncryptor.encryptPassword(newPassWord);
                    terminal.printf(newEncryptedPassword);

                    try {
                        Properties properties = new Properties();
                        properties.setProperty("password", newEncryptedPassword);
                        properties.setProperty("username", newUserName);

                        File file = new File(newUserName + ".properties");
                        FileOutputStream fileOut = new FileOutputStream(file);
                        properties.store(fileOut, "Properties File");
                        fileOut.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    terminal.printf("No choice made");
                    break;
            }
        } while (!sessionStarted);
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