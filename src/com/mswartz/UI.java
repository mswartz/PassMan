package com.mswartz;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.password.BasicPasswordEncryptor;
import java.awt.datatransfer.*;
import java.awt.Toolkit;

import java.io.*;
import java.util.*;

public class UI {
    TextIO textIO = TextIoFactory.getTextIO();
    TextTerminal terminal = textIO.getTextTerminal();
    BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
    Properties properties = new Properties();
    String encryptedPassword;
    User activeUser = new User();
    int pwAttempts = 3;

    private boolean sessionStarted = false;
    private boolean userFound = false;

    public void greetUser(){
        terminal.printf(
                "  ___  _   ___ ___ __  __   _   _  _ \n" +
                " | _ \\/_\\ / __/ __|  \\/  | /_\\ | \\| |\n" +
                " |  _/ _ \\\\__ \\__ \\ |\\/| |/ _ \\| .` |\n" +
                " |_|/_/ \\_\\___/___/_|  |_/_/ \\_\\_|\\_|\n" +
                "                                     \n");

        terminal.printf("Version 0.1 🔥\n");
    }

    public void mainMenu(){
        do {
            String menuChoice = textIO.newStringInputReader()
                    .withNumberedPossibleValues("Login", "Create user")
                    .read("Login");

            switch (menuChoice) {
                case "Login":
                    String userName = textIO.newStringInputReader()
                            .read("Username");

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
                            terminal.printf("\nLogging in...");
                            sessionStarted = true;
                            Session currentSession = new Session(userName);
                            activeUser.setUserName(userName);
                            currentSession.setActiveUser(activeUser);

                            this.appMenu(currentSession);
                        } else {
                            if(pwAttempts > 1) {
                                pwAttempts -= 1;
                                terminal.printf("Incorrect password." + pwAttempts + " attempts left.");
                            } else {
                                System.exit(0);
                            }
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

    public void appMenu(Session currentSession){
        List activeList = new List();
        activeList.setLogins(List.importListFromFile(activeUser));
        currentSession.setKeychain(activeList);

        while (currentSession.getSessionStatus().equals("active")) {
            String menuChoice = textIO.newStringInputReader()
                    .withNumberedPossibleValues("Find login", "Add new login", "Logout")
                    .read();

            switch (menuChoice) {
                case "Find login":
                    serviceMenu(currentSession);
                    break;
                case "Add new login":
                    terminal.printf("A new login");
                case "Logout":
                    currentSession.setSessionStatus("inactive");
                    mainMenu();
            }
        }
    }

    public void serviceMenu(Session currentSession) {
        String menuChoice = textIO.newStringInputReader()
                .withNumberedPossibleValues("google", "yahoo")
                .read();

        for (Login login : currentSession.getKeychain().getLogins()
        ) {
            if(menuChoice.equals(login.getServiceName())){
                terminal.print("Copied to clipboard");
                String myString = login.getPassWord();
                StringSelection stringSelection = new StringSelection(myString);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        }
    }
}