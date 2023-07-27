package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.ResultSet;
import java.sql.SQLException;

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
            String start = result.getString("Start");
            String end = result.getString("End");
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
}
