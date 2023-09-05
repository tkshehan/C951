package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * This class accesses the database for the Appointment Class.
 */
public class AppointmentDao {
    /** This method accesses and returns all appointments from the database.
     * @return The list of appointments to return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sqlStatement = "SELECT * FROM appointments";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()){
            int id = result.getInt("Appointment_ID");
            String title = result.getString("Title");
            String location = result.getString("Location");
            String type = result.getString("Type");
            Timestamp start = result.getTimestamp("Start");
            Timestamp end = result.getTimestamp("End");
            int customerID = result.getInt("Customer_ID");
            int userID = result.getInt("User_ID");
            int contactID = result.getInt("Contact_ID");
            String description = result.getString("Description");
            Appointment appointmentResult = new Appointment(
                    id, title, location, contactID, type,
                    start, end, customerID, userID, description
            );
            allAppointments.add(appointmentResult);
        }
        DBConnection.closeConnection();
        return allAppointments;
    }

    /** This method updates an appointment in the database by the appointment's ID.
     * @param appointment The appointment to update
     */
    public static void updateAppointment (Appointment appointment) {
        DBConnection.openConnection();
        String sqlStatement = "UPDATE appointments \n" +
                "SET " +
                "Title = '%s', ".formatted(appointment.getTitle()) +
                "Description = '%s' ,".formatted(appointment.getDescription()) +
                "Location = '%s' ,".formatted(appointment.getLocation()) +
                "Type = '%s', ".formatted(appointment.getType()) +
                "Customer_ID = %s, ".formatted(appointment.getCustomerID()) +
                "User_ID = %s, ".formatted(appointment.getUserID()) +
                "Contact_ID = %s, ".formatted(appointment.getContactID()) +
                "Start = '%s', ".formatted(appointment.getStart()) +
                "End = '%s' \n".formatted(appointment.getEnd()) +
                "WHERE Appointment_ID = %s;".formatted(appointment.getID());
        Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }

    /** This method deletes an appointment from the database by its ID.
     * @param appointment The appointment to delete.
     */
    public static void deleteAppointment (Appointment appointment) {
        DBConnection.openConnection();
        String sqlStatement = "DELETE FROM appointments WHERE Appointment_ID = %s;".formatted(appointment.getID());
        Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }


    /** This method adds an appointment to the database.
     * @param appointment The appointment to create.
     */
    public static void createAppointment (Appointment appointment) {
        DBConnection.openConnection();
        String sqlStatement = "INSERT INTO appointments(title, description, location, type, start, end, " +
                "customer_id, user_id, contact_id) " +
                "VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');"
                .formatted(
                  appointment.getTitle(),
                  appointment.getDescription(),
                  appointment.getLocation(),
                  appointment.getType(),
                  appointment.getStart(),
                  appointment.getEnd(),
                  appointment.getCustomerID(),
                  appointment.getUserID(),
                  appointment.getContactID()
                );
        Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }

    /** This method checks to see if a customer has any scheduled appointments.
     * @param customer The customer to check for appointments.
     * @return True if the customer has an appointment, false otherwise.
     * @throws SQLException
     */
    public static boolean checkCustomerAppointments (Customer customer) throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "SELECT 1 FROM appointments WHERE Customer_ID = '%s';".formatted(customer.getId());
        Query.makeQuery(sqlStatement);

        return Query.getResult().next();
    }
}
