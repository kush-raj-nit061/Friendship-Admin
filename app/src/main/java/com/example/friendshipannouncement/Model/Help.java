package com.example.friendshipannouncement.Model;

public class Help {
    Help(){}

    String name;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String email;
    String key;

    public Help(String name, String email,String key) {
        this.name = name;
        this.email = email;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
