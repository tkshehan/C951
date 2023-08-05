package controller;

import Database.ContactDao;
import Database.CountryDao;
import Database.CustomerDao;
import Database.UserDao;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Contact;
import model.Customer;
import model.User;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditAppointment implements Initializable {

    public Label windowTitle;
    public TextField idField;
    public TextField titleField;
    public TextField locationField;
    public TextField typeField;
    public ComboBox<User> userCBox;
    public ComboBox<Customer> customerCBox;
    public ComboBox<Contact> contactCBox;
    public DatePicker startDate;
    public ComboBox<Time> startTime;
    public ComboBox<String> duration;
    public TextField descriptionField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setContacts();
        setCustomers();
        setUsers();
    }

    private void setCustomers() {
        try {
            ObservableList<Customer> customers = CustomerDao.getAllCustomers();
            customerCBox.setItems(customers);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    private void setContacts() {
        try {
            ObservableList<Contact> contacts = ContactDao.getAllContacts();
            contactCBox.setItems(contacts);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void setUsers() {
        try {
            ObservableList<User> users = UserDao.getAllUsers();
            userCBox.setItems(users);
        } catch (Exception e) {
          System.out.println(e);
        }
    }

}
