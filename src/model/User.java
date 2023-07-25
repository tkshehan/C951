package model;

import java.util.ArrayList;

public class User {

    private final int ID;
    private String name;
    private String password;

    public User(int id, String name, String password) {
        ID = id;
        this.name = name;
        this.password = password;
    }


    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public String getPassword() {return password;}
}
