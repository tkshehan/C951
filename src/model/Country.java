package model;

import java.util.ArrayList;

public class Country {
    private String name;
    private ArrayList<String> divisions;

    public Country(String name) {
        this.name = name;
        this.divisions = divisions;
    }

    public String getName() {
        return name;
    }

    public void addDivision(String division) {
        divisions.add(division);
    }

    public ArrayList<String> getDivisions() {
        return divisions;
    }
}
