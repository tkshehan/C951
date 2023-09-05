package model;
import java.sql.Timestamp;

/** This class models the appointments for users and contacts.
 */
public class Appointment {

    /** The appointment ID. */
    private final int ID;
    /** The title of the appointment. */
    private String title;
    /** The location of the appointment. */
    private String location;
    /** The typ of appointment. */
    private String type;
    /** The start time of the appointment in UTC. */
    private Timestamp start; // Stored in UTC
    /** The end time of the appointment in UTC. */
    private Timestamp end; // Stored in UTC
    /** The appointment customer's ID */
    private int customerID;
    /** The appointment user's ID */
    private int userID;
    /** The appointment contact's ID */
    private int contactID;
    /** The description of the appointment.  */
    private String description;


    /** Constructor: Assigns appointment members.
     * @param id The ID to assign.
     * @param title The title to assign.
     * @param location The location to assign.
     * @param contactID The contact ID to assign.
     * @param type The type to assign.
     * @param start The start time to assign.
     * @param end The end time to assign.
     * @param custID The customer ID to assign.
     * @param userID The user ID to assign.
     * @param description The description to assign.
     */
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

    /** Returns the appointment ID.
     * @return The ID to return.
     */
    public int getID() {
        return ID;
    }

    /** Returns the appointment title.
     * @return The title to return.
     */
    public String getTitle() {
        return title;
    }

    /** Sets the appointment title.
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Returns the appointment location.
     * @return The location to return.
     */
    public String getLocation() {
        return location;
    }

    /** Sets the appointment location.
     * @param location The location to set.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /** Returns the contact ID.
     * @return The contact ID to return.
     */
    public int getContactID() {
        return contactID;
    }

    /** Sets the contact ID.
     * @param contactID The contact ID to set.
     */
    public void setContact(int contactID) {
        this.contactID = contactID;
    }

    /** Returns the appointment type.
     * @return The type to return.
     */
    public String getType() {
        return type;
    }

    /** Sets the appointment type.
     * @param type The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /** Returns the appointment start time in UTC.
     * @return The start time to return.
     */
    public Timestamp getStart() {
        return start;
    }

    /** Sets the appointment start time, only submit UTC timestamps.
     * @param start the start time to set.
     */
    public void setStart(Timestamp start) {
        this.start = start;
    }

    /** Returns the appointment end time in UTC
     * @return the time to return.
     */
    public Timestamp getEnd() {
        return end;
    }

    /** Sets the appointment end time, only submit UTC timestamps.
     * @param end the time to set.
     */
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    /** Returns the appointment customer's ID.
     * @return The ID to return.
     */
    public int getCustomerID() {
        return customerID;
    }

    /** Sets the appointment customer's ID.
     * @param customerID The ID to set.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /** Returns the appointment user's ID.
     * @return The ID to return.
     */
    public int getUserID() {
        return userID;
    }

    /** Sets the appointment user's ID.
     * @param userID The ID to set.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /** Returns the appointment description.
     * @return The description to return.
     */
    public String getDescription() {
        return description;
    }

    /** Sets the appointment description.
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
