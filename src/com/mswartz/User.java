package com.mswartz;

public class User {
    private String firstName;
    private String lastName;

    //default Constructor
    public User() {}

    public User(String first, String last) {
        firstName = first;
        lastName = last;
    }

    User(User u) {
        firstName = u.firstName;
        lastName = u.lastName;
    }
}