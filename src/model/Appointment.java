package model;

public class Appointment {
    private final int ID;
    private String title;
    private String location;
    private String type;
    private String start;
    private String end;
    private int custID;
    private int userID;
    private int contactID;
    private String description;


    public Appointment(int id, String title, String location, int contactID, String type, String start, String end, int custID, int userID, String description) {
        ID = id;
        this.title = title;
        this.location = location;
        this.contactID = contactID;
        this.type = type;
        this.start = start;
        this.end = end;
        this.custID = custID;
        this.userID = userID;
        this.description = description;
    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContact(int contact) {
        this.contactID = contactID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getCustID() {
        return custID;
    }

    public void setCustID(int custID) {
        this.custID = custID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}