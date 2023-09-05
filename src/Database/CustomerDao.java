package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

/** This class accesses the database for the Customer Class.
 */
public class CustomerDao {

    /** This method retrieves a list of all customers from the database.
     * @return The list of customers to return.
     * @throws SQLException
     */
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

    /** This method updates a customer in the database.
     * @param customer The customer to update.
     */
    public static void updateCustomer(Customer customer) {
        DBConnection.openConnection();
        Query.makeQuery(
                "SELECT DIVISION_ID FROM first_level_divisions \n" +
                        "WHERE Division = '%s'".formatted(customer.getDivision())
        );
        int divisionID;
        try {
            Query.getResult().next();
            divisionID = Query.getResult().getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String sqlStatement = "UPDATE customers \n" +
                "SET " +
                "Customer_Name = '%s', ".formatted(customer.getName()) +
                "Address = '%s', ".formatted(customer.getAddress()) +
                "Postal_Code = '%s', ".formatted(customer.getPostalCode()) +
                "Phone = '%s', ".formatted(customer.getPhoneNumber()) +
                "Division_ID = '%s' \n".formatted(divisionID) +
                "WHERE Customer_ID = '%s';".formatted(customer.getId());
        Query.makeQuery(sqlStatement);

        DBConnection.closeConnection();
    }

    /** This method inserts a new customer into the database.
     * @param customer The customer to insert.
     */
    public static void createCustomer(Customer customer) {
        DBConnection.openConnection();
        Query.makeQuery(
                "SELECT DIVISION_ID FROM first_level_divisions \n" +
                        "WHERE Division = '%s'".formatted(customer.getDivision())
        );
        int divisionID;
        try {
            Query.getResult().next();
            divisionID = Query.getResult().getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String sqlStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID) " +
                "VALUES('%s', '%s', '%s', '%s', '%s');".formatted(
                        customer.getName(),
                        customer.getAddress(),
                        customer.getPostalCode(),
                        customer.getPhoneNumber(),
                        divisionID
                );
        Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }

    /** This method deletes a customer from the database.
     * @param customer The customer to delete.
     */
    public static void deleteCustomer(Customer customer) {
        DBConnection.openConnection();
        String sqlStatement = "DELETE FROM customers WHERE Customer_ID = %s;".formatted(customer.getId());
        Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }
}
