package controller;

import javafx.event.ActionEvent;
import model.Country;
import model.Customer;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class EditCustomer extends CustomerCtrl {
    private final Customer selectedCustomer;

    EditCustomer(Customer customer) {
        super();
        selectedCustomer = customer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        setCustomerFields();
    }

    @Override
    public void submitCustomer(ActionEvent actionEvent) {

    }

    private void setCustomerFields() {
        idField.setText(String.valueOf(selectedCustomer.getId()));
        nameField.setText(selectedCustomer.getName());
        addressField.setText(selectedCustomer.getAddress());
        postalField.setText(selectedCustomer.getPostalCode());
        phoneField.setText(selectedCustomer.getPhoneNumber());

        for (Country country:countryCBox.getItems()) {
            if(Objects.equals(country.getName(), selectedCustomer.getCountry())) {
                System.out.println("Country Found");
                countryCBox.setValue(country);
                onCountrySelect(new ActionEvent());
                divisionCBox.setValue(selectedCustomer.getDivision());
                divisionCBox.setDisable(false);
                break;
            }
        }
    }

}
