package com.mswartz;

import java.util.ArrayList;

public class List {
    public User listOwner;
    private ArrayList<Login> logins = new ArrayList<Login>();


    public List(){}

    public User getListOwner() {
        return listOwner;
    }

    public void setListOwner(User listOwner) {
        this.listOwner = listOwner;
    }

    public void importListFromFile(){
        this.logins.add(new Login("google","mswartz","password"));
        this.logins.add(new Login("yahoo","mswartz","password"));
    }

    public ArrayList<Login> getLogins() {
        return logins;
    }

    public void setLogins(ArrayList<Login> logins) {
        this.logins = logins;
    }
}
