package controller;

import Database.CountryDao;
import Database.CustomerDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Country;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public abstract class CustomerCtrl implements Initializable {

    public Label windowTitle;
    public TextField idField;
    public TextField nameField;
    public TextField addressField;
    public TextField postalField;
    public TextField phoneField;
    public Text errorText;
    public ComboBox<Country> countryCBox;
    public ComboBox<String> divisionCBox;
    public Button cancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Country> countries = FXCollections.observableArrayList();
        try {
            countries.addAll(
                    CountryDao.getAllCountries()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        countryCBox.setItems(countries);
    }

    protected boolean validateCustomer() {
        String errorMessage = "";

        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            errorMessage = errorMessage.concat(
                    "Title must not be empty\n"
            );
        }

        String address = addressField.getText().trim();
        if (address.isEmpty()) {
            errorMessage = errorMessage.concat(
                    "Address must not be empty\n"
            );
        }

        String phone = phoneField.getText().trim();
        if (phone.isEmpty()) {
            errorMessage = errorMessage.concat(
                    "Phone # must not be empty\n"
            );
        }

        String postalCode = postalField.getText().trim();
        if (postalCode.isEmpty()) {
            errorMessage = errorMessage.concat(
                    "Postal Code must not be empty\n"
            );
        }

        if(countryCBox.getValue() == null) {
            errorMessage = errorMessage.concat(
                    "Select a Country\n"
            );
        } else if (divisionCBox.getValue() == null) {
            errorMessage = errorMessage.concat(
                    "Select a Division\n"
            );
        }


        if( errorMessage.length() > 0){
            errorText.setText(errorMessage);
            return false;
        } else {
            return true;
        }
    }

    @FXML
    public void onCountrySelect(ActionEvent actionEvent) {
        divisionCBox.getItems().clear();

        Country selected = countryCBox.getValue();
        ObservableList<String> divisions = FXCollections.observableArrayList();
        divisions.addAll(selected.getDivisions());

        divisionCBox.setItems(divisions);
        divisionCBox.setDisable(false);
    }

    @FXML
    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public abstract void submitCustomer(ActionEvent actionEvent);


}

