package model.reports;

/** This class is a datatype specifying how many appointments share the same month and type.
 * @param month The month of the appointments.
 * @param type The type of appointments.
 * @param count The number of appointments that share the month and type.
 */
public record AppointmentsByMonthAndType(String month, String type, int count) {

    /** This method returns the month.
     * @return The month to return.
     */
    public String getMonth() {
        return month;
    }

    /** This method returns the type.
     * @return The type to return.
     */
    public String getType() {
        return type;
    }

    /** This method returns the amount of appointments sharing the same month and type.
     * @return The count to return.
     */
    public int getCount() {
        return count;
    }
}
