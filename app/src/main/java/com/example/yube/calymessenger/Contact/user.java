package com.example.yube.calymessenger.Contact;

public class user {

    private String email;
    private String name;

    public user(String name, String email) {

        this.email = email;
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
