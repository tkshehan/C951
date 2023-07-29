package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDao {
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        DBConnection.openConnection();
        String sqlStatement = "SELECT customers.*, first_level_divisions.division, countries.country " +
                "FROM customers " +
                "INNER JOIN first_level_divisions ON customers.Division_ID=first_level_divisions.Division_ID " +
                "INNER JOIN countries ON first_level_divisions.Country_ID=countries.Country_ID";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()){
            int id = result.getInt("Customer_ID");
            String name = result.getString("Customer_Name");
            String address = result.getString("Address");
            String postal = result.getString("Postal_Code");
            String phone = result.getString("Phone");
            String division = result.getString("Division");
            String country = result.getString("Country");

            Customer customerResult = new Customer(id, postal, name, address, phone, division, country);
            customers.add(customerResult);
        }
        DBConnection.closeConnection();
        return customers;
    }
}
