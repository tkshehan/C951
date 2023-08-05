package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import model.Contact;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ContactDao {
    private static ObservableList<Contact> contacts = null;
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        if (contacts != null) {
            return contacts;
        } else {
            contacts = FXCollections.observableArrayList();
        }

        DBConnection.openConnection();
        String sqlStatement = "SELECT first_level_divisions.division, countries.country " +
                "FROM first_level_divisions " +
                "INNER JOIN countries ON first_level_divisions.Country_ID=countries.Country_ID +" +
                "ORDER BY country";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();

        while (result.next()) {
            int id = result.getInt("id");
            String name = result.getString("name");
            String email = result.getString("email");

            contacts.add(new Contact(id, name, email));
        }
        DBConnection.closeConnection();
        return contacts;
    }
}
