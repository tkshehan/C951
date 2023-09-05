package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;

/** This class accesses the database for the Contact Class
 */
public class ContactDao {
    /** A persistent list of contacts, which are not expected to change regularly.
     */
    private static ObservableList<Contact> contacts = null;

    /** This method retrieves a list of all contacts from the database.
     * @return The list of all contacts to return.
     * @throws SQLException
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        if (contacts != null) {
            return contacts;
        } else {
            contacts = FXCollections.observableArrayList();
        }

        DBConnection.openConnection();
        String sqlStatement = "SELECT * from contacts";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();

        while (result.next()) {
            int id = result.getInt("contact_id");
            String name = result.getString("contact_name");
            String email = result.getString("email");

            contacts.add(new Contact(id, name, email));
        }
        DBConnection.closeConnection();
        return contacts;
    }


}
