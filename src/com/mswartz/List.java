package com.mswartz;

import java.util.ArrayList;

public class List {
    public User listOwner;
    public ArrayList<Login> keyChain;

    public List(){}

    public User getListOwner() {
        return listOwner;
    }

    public void setListOwner(User listOwner) {
        this.listOwner = listOwner;
    }

    public void importListFromFile(){

    }
}
