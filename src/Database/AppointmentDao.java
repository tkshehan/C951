package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

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
            int custID = result.getInt("Customer_ID");
            int userID = result.getInt("User_ID");
            int contactID = result.getInt("Contact_ID");
            String description = result.getString("Description");
            Appointment appointmentResult = new Appointment(
                    id, title, location, contactID, type,
                    start, end, custID, userID, description
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
        System.out.println(Query.getResult());
    }

    public static void deleteAppointment (Appointment appointment) {

    }

    public static void createAppointment (Appointment appointment) {


    }
}
