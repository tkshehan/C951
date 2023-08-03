package model;
import java.sql.Timestamp;

public class Appointment {
    private final int ID;
    private String title;
    private String location;
    private String type;
    private Timestamp start; // Stored in UTC
    private Timestamp end; // Stored in UTC
    private int customerID;
    private int userID;
    private int contactID;
    private String description;


    public Appointment(int id, String title, String location, int contactID, String type, Timestamp start, Timestamp end, int custID, int userID, String description) {
        ID = id;
        this.title = title;
        this.location = location;
        this.contactID = contactID;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = custID;
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

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
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
