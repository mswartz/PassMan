package com.mswartz;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class List {
    public User listOwner;
    private ArrayList<Login> logins = new ArrayList<Login>();


    public List(){}

    public List(User activeUser){
        this.setListOwner(activeUser);
    }

    public User getListOwner() {
        return listOwner;
    }

    public void setListOwner(User listOwner) {
        this.listOwner = listOwner;
    }

//    public ArrayList<Login> importListFromFile(){
//        this.logins.add(new Login("google","mswartz","password"));
//        this.logins.add(new Login("yahoo","mswartz","password"));
//        User testuser = new User();
//        testuser.setUserName("mswartz");
//        this.setListOwner(testuser);
//        List.writeListToFile(this);
//        return this.getLogins();
//    }

    public static ArrayList<Login> importListFromFile(User activeUser){
        ArrayList<Login> loginsFromFile = new ArrayList<Login>();

        try {
            Path path = Paths.get(activeUser.getUserName()+".txt");
            Scanner scanner = new Scanner(path);
            //read line by line
            int i = 0;
            while (scanner.hasNextLine()) {
                //process each line
                if(scanner.hasNext()){
                    String service = scanner.next();
                    String userName = scanner.next();
                    String password = scanner.next();

                    Login l = new Login();
                    l.setServiceName(service);
                    l.setUserName(userName);
                    l.setPassWord(password);

                    loginsFromFile.add(l);

                    i++;
                } else {
                    break;
                }
            }

            scanner.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
        return loginsFromFile;
    }

    public static void writeListToFile(List list) {
        File file = new File(list.listOwner.getUserName()+".txt");
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            for (Login login : list.getLogins()
            ) {
                fw.write(login.getServiceName()+" ");
                fw.write(login.getUserName()+" ");
                fw.write(login.getPassWord()+" ");
                fw.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //close resources
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Login> getLogins() {
        return logins;
    }

    public void setLogins(ArrayList<Login> logins) {
        this.logins = logins;
    }
}
