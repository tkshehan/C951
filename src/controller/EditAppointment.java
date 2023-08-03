package controller;

import Database.ContactDao;
import Database.CountryDao;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditAppointment implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            CountryDao.getAllCountries();
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            ContactDao.getAllContacts();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
