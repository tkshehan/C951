package model;

import java.util.ArrayList;

public class User {

    private final int ID;
    private String name;
    private ArrayList<Appointment> appointments;

    public User(int id, String name) {
        ID = id;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }
}
