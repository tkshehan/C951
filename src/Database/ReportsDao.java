package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.reports.AppointmentsByMonthAndType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportsDao {

    public static ObservableList<AppointmentsByMonthAndType> getMonthAndTypeReport() {
        ObservableList<AppointmentsByMonthAndType> report = FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sqlStatement = """
                SELECT MONTHNAME(Start) as Month,
                  Type,
                  COUNT(*)  as Count
                FROM appointments
                GROUP BY Month, Type;
                """;
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();

        try {
            while (result.next()) {
                String month = result.getString("Month");
                String type = result.getString("Type");
                int count = result.getInt("Count");
                report.add(new AppointmentsByMonthAndType(month, type, count));
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException();
        }

        DBConnection.closeConnection();
        return report;
    }

    public static ObservableList<Contact> getContactReport() throws SQLException {
        ObservableList<Contact> contactReports = FXCollections.observableArrayList();

        DBConnection.openConnection();
        String sqlStatement = """
                SELECT contacts.Contact_ID, Contact_Name, Email, COUNT(appointments.Appointment_ID) as NumAppointments FROM contacts
                  LEFT JOIN appointments ON contacts.contact_id = appointments.contact_id
                  GROUP BY contact_id;
                """;
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();

        while (result.next()) {
            int id = result.getInt("contact_id");
            String name = result.getString("contact_name");
            String email = result.getString("email");
            int count = result.getInt("NumAppointments");

            contactReports.add(new Contact(id, name, email, count));
        }

        DBConnection.closeConnection();
        return contactReports;
    }
}
