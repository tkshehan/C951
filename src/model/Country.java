package model;

import java.util.ArrayList;

public class Country {
    private final String name;
    private final ArrayList<String> divisions = new ArrayList<String>();

    public Country(String name) {
        this.name = name;
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

    public String toString() {
        return name;
    }
}
