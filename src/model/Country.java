package model;

import java.util.ArrayList;

/** This class records a countries name and its subdivisions. */
public class Country {
    /** The name of the country. */
    private final String name;
    /** The states/provinces of the country. */
    private final ArrayList<String> divisions = new ArrayList<String>();

    /** Constructor, assigns the name of the country.
     * @param name The name to assign.
     */
    public Country(String name) {
        this.name = name;
    }

    /** Gets the name of the country.
     * @return The name to return.
     */
    public String getName() {
        return name;
    }

    /** Adds a state or province to the country.
     * @param division The state or province to add.
     */
    public void addDivision(String division) {
        divisions.add(division);
    }

    /** Gets the list of states/provinces.
     * @return The list of states/provinces to return.
     */
    public ArrayList<String> getDivisions() {
        return divisions;
    }

    /** Returns the name of the country.
     * @return The name to return.
     */
    public String toString() {
        return name;
    }
}
