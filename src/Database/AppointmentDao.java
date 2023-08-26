package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AppointmentDao {
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

    public static void deleteAppointment (Appointment appointment) {
        DBConnection.openConnection();
        String sqlStatement = "DELETE FROM appointments WHERE Appointment_ID = %s;".formatted(appointment.getID());
        Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }


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

    public static boolean checkCustomerAppointments (Customer customer) throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "SELECT 1 FROM appointments WHERE Customer_ID = '%s';".formatted(customer.getId());
        Query.makeQuery(sqlStatement);

        return Query.getResult().next();
    }
}
