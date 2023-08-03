package Database;

import model.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ContactDao {
    private static ArrayList<Contact> contacts = null;
    public static ArrayList<Contact> getAllContacts() throws SQLException {
        if (contacts != null) return contacts;

        contacts = new ArrayList<Contact>();
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
