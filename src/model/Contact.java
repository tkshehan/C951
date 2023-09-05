package model;

import java.util.Objects;

/** This class models Contact information for appointments.
 */
public final class Contact {

    /** The contact's ID */
    private final int id;
    /** The contact's name*/
    private final String name;
    /** The contact's email address */
    private final String email;
    /** The number of appointment's for the contact */
    private int appointmentCount;

    /** Constructor: Assigns the Contact members.
     * @param id The id to assign.
     * @param name The name to assign.
     * @param email The email to assign.
     */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /** Constructor: Assigns the contact members, including appointment count.
     * @param id The id to assign.
     * @param name The name to assign.
     * @param email The email to assign.
     * @param count The count of appointments to assign.
     */
    public Contact(int id, String name, String email, int count) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.appointmentCount = count;
    }

    /** Returns the Contact's Name
     * @return The string to return.
     */
    @Override
    public String toString() {
        return name;
    }

    /** Gets the contact's ID
     * @return The ID to return.
     */
    public int getId() {
        return id;
    }

    /** Gets the contact's name.
     * @return The name to return.
     */
    public String getName() {
        return name;
    }

    /** Gets the contacts email address.
     * @return The email address to return.
     */
    public String getEmail() {
        return email;
    }

    /** Gets the Contact's number of appointments.
     * @return The count to return.
     */
    public int getAppointmentCount() {
        return appointmentCount;
    }

    /** Compares this contact with another object to see if they are equal.
     * @param obj The object to compare to.
     * @return Returns true if objects are equal, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Contact) obj;
        return this.id == that.id &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.email, that.email);
    }

}
