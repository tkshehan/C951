package controller;

import Database.CustomerDao;
import javafx.event.ActionEvent;
import model.Customer;

import java.net.URL;
import java.util.ResourceBundle;

/** This method controls the Customer View, adding a new Customer.
 */
public class NewCustomer extends CustomerCtrl {

    /** This method runs when the View finishes loading.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        windowTitle.setText("New Appointment");
        idField.setText("Auto-Generated");
    }

    /** This method creates a new customer if the fields are valid.
     * @param actionEvent An action from the user.
     */
    @Override
    public void submitCustomer(ActionEvent actionEvent) {
        if(!validateCustomer()) return;

        Customer newCustomer = new Customer(
                Integer.MIN_VALUE,
                postalField.getText().trim().replace("'", ""),
                nameField.getText().trim().replace("'", ""),
                addressField.getText().trim().replace("'", ""),
                phoneField.getText().trim().replace("'", ""),
                divisionCBox.getValue().trim().replace("'", ""),
                countryCBox.getValue().getName().trim().replace("'", "")
        );

        try {
            CustomerDao.createCustomer(newCustomer);
            cancel.fire();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
